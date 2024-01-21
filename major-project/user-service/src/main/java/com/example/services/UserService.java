package com.example.services;

import com.example.dtos.UserCreateRequest;
import com.example.models.User;
import com.example.repositories.UserCacheRepository;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCacheRepository userCacheRepository;

    public User create(UserCreateRequest userCreateRequest){

        User user = userCreateRequest.to();
        return this.userRepository.save(user); // user's account is getting created

        // Making an API call to wallet service to create a wallet - 2-3 seconds

        // TODO: Publish an event that the user is created

        // TODO: Wallet service's consumer will be listening to this event and will
        //  create wallet for the user with some given balance

        // TODO: FOR YOU: Notification service consumer will be listening to this and send email for account verification or initiate kyc
    }

    public User get(Integer userId) {
//        User user = userCacheRepository.get(userId);

//        if(user == null){
            User user = this.userRepository.findById(userId).orElse(null);
//            userCacheRepository.save(user);
//        }

        return user;
    }
}
