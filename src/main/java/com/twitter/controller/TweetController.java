package com.twitter.controller;

import java.util.*;

import com.twitter.exception.ResourceNotFoundException;

import com.twitter.redis.RedisService;
import com.twitter.redis.Result;
import com.twitter.repo.CustomerRepository;
import com.twitter.repo.TweetRepository;
import com.twitter.service.CustomerService;
import com.twitter.model.Tweet;
import com.twitter.model.TweetUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.twitter.service.TweetService;

@RestController
@RequestMapping("/api")
public class TweetController {


    @Autowired
    CustomerService userService;

    @Autowired
    RedisService service;

    @Autowired
    TweetService tweetService;

    @GetMapping("/tweet/get/")
    public Tweet getTweetById(long user_id, long tweet_id) throws Exception {

        boolean popular = userService.isPopular(user_id);

        if (popular) {
            Tweet tweet = tweetService.getTweet(user_id, tweet_id);
            return tweet;

        } else {
            tweetService.getFeed(user_id);
        }

    }


    @PostMapping("/tweet/create")
    public Result create(@RequestBody Tweet tweet, Long id){
        // save a single tweet
        if (service.exists(tweet.toString())) {
            return Result.success();
        }

        service.save(id.toString(), tweet.getContent());
        tweetRepository.save(new Tweet (tweet.getContent()));

        return Result.success();

    }

    @DeleteMapping("/tweet/delete/{id}")
    public Map<String, Boolean> delete (@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        if (service.exists(id.toString())) {

            service.delete(id.toString());
        }

        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("tweet not found."));

        this.tweetRepository.delete(tweet);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }




    @PutMapping("/tweet/update/{id}")
    public String updateTweet(@PathVariable(value = "id") Long id, @RequestBody Tweet t) throws ResourceNotFoundException {
        if (service.exists(id.toString())) {
            service.set(id.toString(), t.toString());
        }
        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tweet not found."));


       tweet.setContent(t.getContent());


        return this.tweetRepository.save(tweet).toString();
    }

    @GetMapping("/tweet/findall")
    public List<Tweet> findAllTweets(){

        List<Tweet> tweets = tweetRepository.findAll();
        return tweets;
    }

    @RequestMapping("/tweet/search/{id}")
    public TweetUI searchTweet(@PathVariable long id){

        Tweet tweet = (Tweet) tweetRepository.findById(id);
        return new TweetUI(tweet.getContent());
    }

    @RequestMapping("/tweet/searchcontent/{str}")
    public List<TweetUI> fetchTweetByConstraining(@PathVariable String str){

        List<Tweet> tweets = tweetRepository.fetchTweetsWithContent(str);
        List<TweetUI> tweetsUI = new ArrayList<>();

        for (Tweet tweet : tweets) {
            tweetsUI.add(new TweetUI(tweet.getContent()));
        }

        return tweetsUI;
    }



}
