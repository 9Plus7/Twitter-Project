package com.twitter.controller;

import java.util.*;

import com.twitter.exception.ResourceNotFoundException;


import com.twitter.service.CustomerService;
import com.twitter.model.Tweet;
import com.twitter.model.TweetUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.web.bind.annotation.*;
import com.twitter.service.TweetService;

@EnableAutoConfiguration(exclude={org.activiti.spring.boot.SecurityAutoConfiguration.class})
@RestController
@RequestMapping("/api")
public class TweetController {


    @Autowired
    CustomerService userService;

    @Autowired
    TweetService tweetService;


    @GetMapping("/tweet")
    public List<Tweet> getTimeline(long user_id) throws ResourceNotFoundException {

        boolean popular = userService.isPopular(user_id);
        List<Tweet> timeline = new ArrayList<>();

        if (popular) {
            timeline = tweetService.getFeed(user_id);
            return timeline;
        } else {
            timeline = tweetService.findAll();
        }

        return timeline;
    }

    @GetMapping("/tweet/{id}")
    public TweetUI getTweetById(long tweet_id) throws Exception {

        TweetUI tweet = new TweetUI(tweetService.getTweet(tweet_id).getContent());
        return tweet;

    }


    @PostMapping("/tweet")
    public TweetUI createTweetById(@RequestBody String content, long id) throws Exception {

        TweetUI t = new TweetUI(tweetService.createTweet(id, content).getContent());
        return t;

    }

    @DeleteMapping("/tweet/{id}")
    public TweetUI deleteTweetById (@PathVariable(value = "id") long id) throws Exception {

            TweetUI t = new TweetUI(tweetService.deleteTweet(id).getContent());
            return t;

    }



    @PutMapping("/tweet/{id}")
    public TweetUI updateTweet(@PathVariable(value = "id") long id, @RequestBody Tweet t) throws Exception {
       String content = t.getContent();
        TweetUI ui = new TweetUI(tweetService.updateTweet(id, content).getContent());
        return ui;
    }

    @GetMapping("/tweet/findall")
    public List<Tweet> findAllTweets(){

        List<Tweet> tweets = tweetService.findAll();
        return tweets;
    }



}
