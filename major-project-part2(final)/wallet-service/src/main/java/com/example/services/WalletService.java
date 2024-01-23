package com.example.services;

import com.example.models.Wallet;
import com.example.repositories.WalletRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    JSONParser jsonParser;

    private static final String USER_CREATED_TOPIC = "user_created";
    private static final String TRANSACTION_INITIATED_TOPIC = "transaction_initiated";

    private static final String WALLET_UPDATED_TOPIC = "wallet_updated";

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${wallet.promotional.balance}")
    private Long promotionalBalance;

    @KafkaListener(topics = {USER_CREATED_TOPIC}, groupId = "jbdl60_grp1")  // => creating a consumer
    public void createWallet(String msg) throws ParseException {

        JSONObject data = (JSONObject) jsonParser.parse(msg);

        String contact = (String) data.get("contact");


        Wallet wallet = this.walletRepository.findByContact(contact);
        if(wallet != null){
            return;
        }

        wallet = Wallet.builder()
                .contact(contact)
                .balance(promotionalBalance)
                .build();

        walletRepository.save(wallet);
    }

    @KafkaListener(topics = {TRANSACTION_INITIATED_TOPIC}, groupId = "jbdl60_grp1")  // => creating a consumer
    public void updateWallets(String msg) throws ParseException, JsonProcessingException {

        //TODO: Add wallet audit for idempotency
        JSONObject data = (JSONObject) jsonParser.parse(msg);

        String sender = (String) data.get("sender");
        String receiver = (String) data.get("receiver");
        Long amount = (Long) data.get("amount");
        String externalTxnId = (String) data.get("externalTransactionId");

        Wallet senderWallet = this.walletRepository.findByContact(sender);
        Wallet receiverWallet = this.walletRepository.findByContact(receiver);

        JSONObject dataToBeSent = new JSONObject();
        dataToBeSent.put("sender", sender);
        dataToBeSent.put("receiver", receiver);
        dataToBeSent.put("amount", amount);
        dataToBeSent.put("externalTransactionId", externalTxnId);

        if(senderWallet == null || receiverWallet == null || senderWallet.getBalance() < amount){
            dataToBeSent.put("walletUpdateStatus", "FAILED");
            kafkaTemplate.send(WALLET_UPDATED_TOPIC, this.objectMapper.writeValueAsString(dataToBeSent));
            return;
        }

//        this.walletRepository.decrementWalletByContact(sender, amount);
//        this.walletRepository.incrementWalletByContact(receiver, amount);

        try {
            this.walletRepository.updateWalletByContact(sender, -amount);
            this.walletRepository.updateWalletByContact(receiver, amount);

            dataToBeSent.put("walletUpdateStatus", "SUCCESS");
            kafkaTemplate.send(WALLET_UPDATED_TOPIC, this.objectMapper.writeValueAsString(dataToBeSent));

        }catch (Exception e){
            dataToBeSent.put("walletUpdateStatus", "FAILED");
            kafkaTemplate.send(WALLET_UPDATED_TOPIC, this.objectMapper.writeValueAsString(dataToBeSent));
        }
    }
}
