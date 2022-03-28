package com.twitter.controller;



import com.twitter.exception.ResourceNotFoundException;
import com.twitter.model.Customer;
import com.twitter.model.CustomerUI;
import com.twitter.redis.RedisService;
import com.twitter.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.twitter.service.CustomerService;

import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerService service;


    @GetMapping("/customer/{id}")
    public CustomerUI getUserById(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
            CustomerUI c = service.getUserById(id);

            return c;

    }

    @PostMapping("/customer")
    public String createUser(String name) {

       return service.createUser(name);

    }


        @DeleteMapping("/customer/{id}")
        public String deleteUser (@PathVariable(value = "id") long id) throws ResourceNotFoundException {

            return service.deleteUserById(id);

        }

        @PutMapping("customer/{id}")
        public CustomerUI updateUser(@PathVariable(value= "id")long id, String name) throws Exception {
                return service.updateUser(id, name);
        }


    }
