package com.example.minorproject.services;

import com.example.minorproject.models.Author;
import com.example.minorproject.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Author createOrGet(Author author){
//        this.authorRepository.
        Author authorFromDB = this.authorRepository.getByEmail(author.getEmail());
        if(authorFromDB == null){
            authorFromDB = this.authorRepository.save(author); //author - will not have id
        }

        return authorFromDB; // id will be there, it will not be null
    }
}
