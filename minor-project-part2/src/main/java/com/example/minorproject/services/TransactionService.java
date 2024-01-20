package com.example.minorproject.services;

import com.example.minorproject.dto.TransactionCreateRequest;
import com.example.minorproject.models.*;
import com.example.minorproject.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    @Value("${books.max-allowed}")
    Integer MAX_ALLOWED_BOOKS;

    @Value("${books.issuance.due-date}")
    Integer ISSUED_ALLOWED_DAYS;

//    private static final Integer MAX_ALLOWED_BOOK = 3;

    public String transact(Integer studentId, TransactionCreateRequest transactionCreateRequest) throws Exception { // return a UUID as an external txn id

        Integer bookId = transactionCreateRequest.getBookId();

        TransactionType transactionType = transactionCreateRequest.getTransactionType();

        if(transactionType == TransactionType.ISSUE){
            return issueBook(studentId, bookId);
        }else{
            return returnBook(studentId, bookId);
        }
    }

    private String issueBook(Integer studentId, Integer bookId) throws Exception {

        /**
         * 1. create an entry in the txn table with the status as pending : acknowledging that we have received the request to issue
         * 2. whether book is available or not ?
         * 3. whether student has already issued the max no of books allowed
         * 4. update book table set student id = ? i.e making the book unavailable
         * 5. update txn status to success or if anything fails just make the txn status as FAILED
         */

        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .transactionStatus(TransactionStatus.PENDING)
                .book(Book.builder().id(bookId).build())
                .student(Student.builder().id(studentId).build())
                .build();

        this.transactionRepository.save(transaction);

        try {

            Book book = this.bookService.find(bookId);
            Student student = this.studentService.get(studentId);


            // Book is available or not
            if (book.getStudent() != null) {
                throw new Exception("Book is already issued to another student");
            }

            if (student.getBookList() != null && student.getBookList().size() >= MAX_ALLOWED_BOOKS) {
                throw new Exception("Student has already issued maximum number of books");
            }

            this.bookService.updateBookAvailability(bookId, student);

            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            this.transactionRepository.save(transaction);

        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            this.transactionRepository.save(transaction);
            throw e;
        }

        return transaction.getExternalTxnId();
    }

    private String returnBook(Integer studentId, Integer bookId) throws Exception {

        /**
         * /**
         *          * 1. create an entry in the txn table with the status as pending : acknowledging that we have received the request to issue
         *          * 2. whether book is available or issue to someone else ? in that case throw an exception
         *          * 3. Check for fine ? when the book was issued and the due date
         *          * 4. update book table set student id = null i.e making the book available
         *          * 5. update txn status to success or if anything fails just make the txn status as FAILED
         *
         */
        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .transactionStatus(TransactionStatus.PENDING)
                .book(Book.builder().id(bookId).build())
                .student(Student.builder().id(studentId).build())
                .build();

        this.transactionRepository.save(transaction);

        try{
            Book book = this.bookService.find(bookId);
            Student student = this.studentService.get(studentId);
            if(book.getStudent() == null || book.getStudent().getId() != studentId){
                throw new Exception("Book is not issued to this student");
            }

            Transaction issuedTxn = this.transactionRepository.findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByIdDesc(
                    student, book, TransactionType.ISSUE, TransactionStatus.SUCCESS
            );

            if(issuedTxn == null){
                throw new Exception("Corresponding issue transaction not found");
            }


            Date issuanceDate = issuedTxn.getCreatedOn();
            Date returnDate = transaction.getCreatedOn();

            Double fine = this.getFine(issuanceDate, returnDate);


            this.bookService.updateBookAvailability(bookId, null);

            transaction.setFine(fine);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            this.transactionRepository.save(transaction);

        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            this.transactionRepository.save(transaction);
        }

        return transaction.getExternalTxnId();
    }

    private Double getFine(Date issuanceDate, Date returnDate){

        long timeDiffInMillis = (returnDate.getTime() - issuanceDate.getTime());

        long daysPassed = TimeUnit.DAYS.convert(timeDiffInMillis, TimeUnit.MILLISECONDS);

        Double fine = 0.0;

        if(daysPassed > ISSUED_ALLOWED_DAYS){
            fine = (daysPassed - ISSUED_ALLOWED_DAYS) * 1.0;
        }

        return fine;
    }

    public Transaction getTxnById(String externalTxnId){
        return this.transactionRepository.findByExternalTxnId(externalTxnId);
    }
}