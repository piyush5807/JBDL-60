package com.example.services;

import com.example.dtos.TransactionCreateRequest;
import com.example.models.Transaction;
import com.example.models.TransactionStatus;
import com.example.repositories.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JSONParser jsonParser;


    private static String TRANSACTION_INITIATED_TOPIC = "transaction_initiated";
    private static final String WALLET_UPDATE_TOPIC = "wallet_updated";

    public String transact(TransactionCreateRequest transactionCreateRequest) {

        Transaction transaction = transactionCreateRequest.to();
        transaction.setExternalTransactionId(UUID.randomUUID().toString());
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        this.transactionRepository.save(transaction);

        JSONObject data = new JSONObject();
        data.put("sender", transaction.getSender());
        data.put("receiver", transaction.getReceiver());
        data.put("amount", transaction.getAmount());
        data.put("externalTransactionId", transaction.getExternalTransactionId());

        // An extra attribute
        data.put("transactionStatus", transaction.getTransactionStatus());

        try {
            this.kafkaTemplate.send(TRANSACTION_INITIATED_TOPIC, this.objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return transaction.getExternalTransactionId();
    }

    @KafkaListener(topics = {WALLET_UPDATE_TOPIC}, groupId = "jbdl60_grp1")
    public void updateTxn(String msg) throws ParseException {

        JSONObject data = (JSONObject) jsonParser.parse(msg);

        String externalTxnId = (String) data.get("externalTransactionId");
        String walletUpdateStatus = (String) data.get("walletUpdateStatus");

        Transaction transaction = this.transactionRepository.findByExternalTransactionId(externalTxnId);

        if(walletUpdateStatus.equals("FAILED")){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }else{
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        }

        this.transactionRepository.updateTransactionStatusForExtTxnId(externalTxnId, transaction.getTransactionStatus());

        //TODO: Publish a txn complete event to which notification service can listen and send an email whether
        // This transaction has been failed or has been successfully processed
    }
}
