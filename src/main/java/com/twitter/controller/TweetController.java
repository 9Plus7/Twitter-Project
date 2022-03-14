package com.twitter.controller;

import java.util.*;

import com.twitter.exception.ResourceNotFoundException;


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

    @PostMapping("/tweet/create")
    public String create(@RequestBody Tweet tweet){
        // save a single tweet
        tweetRepository.save(new Tweet (tweet.getContent()));

        return "Tweet is created";
    }

    @DeleteMapping("/tweet/delete/{id}")
    public Map<String, Boolean> delete (@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("tweet not found."));

        this.tweetRepository.delete(tweet);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

    @GetMapping("/tweet/get/{id}")
    public ResponseEntity<Tweet> getTweetById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tweet not found."));

        return ResponseEntity.ok().body(tweet);
    }


    @PutMapping("/tweet/update/{id}")
    public String updateTweet(@PathVariable(value = "id") Long id, @RequestBody Tweet t) throws ResourceNotFoundException {
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
