package com.twitter.service;

import com.twitter.model.Customer;
import com.twitter.model.CustomerUI;
import com.twitter.repo.CustomerRepository;
import com.twitter.model.Tweet;
import com.twitter.model.TweetUI;
import com.twitter.repo.TweetRepository;
import com.twitter.model.Follower;
import com.twitter.repo.FollowerRepository;

import org.apache.commons.*;
import com.twitter.exception.ResourceNotFoundException;


import org.springframework.beans.factory.annotation.Autowired;

import com.twitter.service.CustomerService;
import org.springframework.stereotype.Service;


import com.twitter.redis.RedisService;
import com.twitter.redis.Result;

import org.springframework.util.SerializationUtils;


import java.util.*;

@Service
public class TweetService {


    @Autowired
    private RedisService service;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired CustomerService userService;



    public List<Tweet> getFeed(long user_id) throws ResourceNotFoundException {
        CustomerUI user = userService.getUserById(user_id);

        Tweet t = null;

            List<Tweet> userTweets = tweetRepository.findLatestTweetByUser(user_id);

            if (userTweets.size() > 0) t = userTweets.get(0);

            List<Tweet> followTweets = tweetRepository.findTweetsThatUserFollows(user);

            if (t != null ) {
                followTweets.add(0, t);

        }
            return followTweets;

    }

        public Tweet getTweet(long user_id, long tweet_id) throws Exception {

            Tweet tweet = tweetRepository.findById(tweet_id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            return tweet;
        }

}
