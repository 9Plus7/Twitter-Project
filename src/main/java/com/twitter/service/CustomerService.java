package com.twitter.service;

import com.twitter.model.Customer;
import com.twitter.model.CustomerUI;
import com.twitter.repo.CustomerRepository;

import org.apache.commons.*;

import com.twitter.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;


import com.twitter.redis.RedisService;
import com.twitter.redis.Result;

import org.springframework.util.SerializationUtils;


import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private RedisService service;

    public static final String HASH_KEY = "User";

    public boolean isPopular(long user_id) throws ResourceNotFoundException {
        Customer user = repo.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        int count = repo.countFollower(user_id);

        if (count < 50000) user.setPopular(false);
        if (count > 50000) user.setPopular(true);

        return user.getPopular();


    }


    public CustomerUI getUserById(long id) throws ResourceNotFoundException {

        String cache = service.hget(HASH_KEY, id);

        CustomerUI ui = new CustomerUI("", "");

        if (cache == null) {

            Customer customer = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));

            service.hset(HASH_KEY, String.valueOf(id), customer.toString());
            ui.setFirstName(customer.getFirstName());
            ui.setLastName(customer.getLastName());

            return ui;

        }

        return ui;

    }

    public String deleteUserById(long id) throws ResourceNotFoundException {

        String cache = service.hget(HASH_KEY, id);

        if (cache == null) {
            return "user does not exist";
        } else {
            service.del(HASH_KEY, String.valueOf(id));
        }

        Customer customer = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        repo.deleteById(id);

        return "delete success";

    }

    public String saveUser (Customer user, long id) throws ResourceNotFoundException {

        String cache = service.hget(HASH_KEY, id);
        Customer u = repo.getById(id);

        if (cache == null) {

                byte[] userUI = SerializationUtils.serialize(user);
                byte[] key = SerializationUtils.serialize(HASH_KEY);

                service.set(key, userUI, 0);

            if (u == null){

                repo.save(user);

                return "save success in database and cache";

            }

                return "save success in cache";

        } else {
            return "save failed, user already exist";
        }



    }


    public String deleteUser(long id) throws ResourceNotFoundException {

        service.del(0, HASH_KEY, String.valueOf(id));

        Customer customer = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        repo.deleteById(id);

        return "delete successful";
    }




}
