package com.example.minorproject.services;

import com.example.minorproject.dto.TransactionCreateRequest;
import com.example.minorproject.models.*;
import com.example.minorproject.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

//    private static final Integer MAX_ALLOWED_BOOK = 3;

    public String transact(TransactionCreateRequest transactionCreateRequest) throws Exception { // return a UUID as an external txn id

        Integer studentId = transactionCreateRequest.getStudentId();
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

    private String returnBook(Integer studentId, Integer bookId){

        /**
         * /**
         *          * 1. create an entry in the txn table with the status as pending : acknowledging that we have received the request to issue
         *          * 2. whether book is available or issue to someone else ? in that case throw an exception
         *          * 3. Check for fine ? when the book was issued and the due date
         *          * 4. update book table set student id = null i.e making the book available
         *          * 5. update txn status to success or if anything fails just make the txn status as FAILED
         *
         */

        /*
            Postman
            Dbeaver / Mysql workbench
         */
        return null;
    }
}