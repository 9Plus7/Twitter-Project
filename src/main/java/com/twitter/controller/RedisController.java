package com.twitter.controller;

import com.twitter.exception.ResourceNotFoundException;
import com.twitter.redis.RedisService;
import com.twitter.redis.Result;

import com.twitter.model.Customer;
import com.twitter.repo.CustomerRepository;
import com.twitter.repo.TweetRepository;
import com.twitter.model.Tweet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    CustomerRepository customerRepository;



    @GetMapping("/get")
    @ResponseBody
    public Result get(){
        String str=redisService.get("girlfriend",String.class);
        return Result.success().add("girlfriend",str);
    }
}

