package com.twitter.controller;

import java.util.*;

import com.twitter.exception.ResourceNotFoundException;

import com.twitter.redis.RedisService;
import com.twitter.redis.Result;
import com.twitter.repo.CustomerRepository;
import com.twitter.repo.TweetRepository;
import com.twitter.model.Tweet;
import com.twitter.model.TweetUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TweetController {

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RedisService service;

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

    @GetMapping("/tweet/get/{id}")
    public String getTweetById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {


        String s = service.get(id.toString(), String.class);
        if (s == null) {
            Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tweet not found."));

            if (tweet == null) {

                service.set(id.toString(), tweet.toString());
                return null;
            }
            return tweet.toString();
        }


        return s;
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
