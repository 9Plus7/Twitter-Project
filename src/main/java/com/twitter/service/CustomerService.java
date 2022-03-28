package com.twitter.service;

import com.twitter.model.Customer;
import com.twitter.model.CustomerUI;
import com.twitter.repo.CustomerRepository;


import com.twitter.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;


import com.twitter.redis.RedisService;

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


        CustomerUI cache = (CustomerUI)service.hget(SerializationUtils.serialize(HASH_KEY), SerializationUtils.serialize(id));

        CustomerUI ui = new CustomerUI("", "", false);

        if (cache == null) {

            Customer customer = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));

            service.hset(SerializationUtils.serialize(HASH_KEY), SerializationUtils.serialize(id), SerializationUtils.serialize(customer));
            ui = (CustomerUI)service.hget(SerializationUtils.serialize(HASH_KEY), SerializationUtils.serialize(id));

            return ui;

        } else {
            ui = cache;
            return ui;
        }


    }

    public String deleteUserById(long id) throws ResourceNotFoundException {

        CustomerUI cache = (CustomerUI)service.hget(SerializationUtils.serialize(HASH_KEY), SerializationUtils.serialize(id));

        if (cache == null) {
            Customer customer = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
            repo.deleteById(customer.getId());
        } else {
            service.del(HASH_KEY, String.valueOf(id));
            Customer customer = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
            repo.deleteById(customer.getId());
        }

        return "delete success";

    }

    public CustomerUI updateUser(long id, String name) throws Exception {

        CustomerUI cache = (CustomerUI) service.hget(SerializationUtils.serialize(HASH_KEY), SerializationUtils.serialize(id));
        Customer c = new Customer(name);
        if (cache == null) {
            service.hset(SerializationUtils.serialize(HASH_KEY), SerializationUtils.serialize(id), SerializationUtils.serialize(c));
            System.out.println("save in cache");
            Customer customer = repo.findById(id).orElse(null);

            if (customer.equals(null)) {
                repo.save(customer);
                CustomerUI ui = new CustomerUI(customer.getFirstName(), customer.getLastName(), customer.getPopular());
                return ui;
            }

        }
        return cache;
    }

    public String createUser(String name) {
        Customer c = repo.findByName(name);

        if (c == null) {
            repo.save(new Customer(name));
            return "user saved in db";
        }
        service.hset(SerializationUtils.serialize(HASH_KEY), SerializationUtils.serialize(0), SerializationUtils.serialize(c));

        return "saved in cache";
    }


    public List<Customer> findAll() {return repo.findAll();}
}
