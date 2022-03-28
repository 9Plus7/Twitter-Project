package com.twitter.service;

import com.twitter.model.Customer;
import com.twitter.model.CustomerUI;
import com.twitter.repo.CustomerRepository;
import com.twitter.model.Tweet;
import com.twitter.model.TweetUI;
import com.twitter.repo.TweetRepository;
import com.twitter.model.Follower;
import com.twitter.repo.FollowerRepository;

import com.twitter.exception.ResourceNotFoundException;


import org.springframework.beans.factory.annotation.Autowired;

import com.twitter.service.CustomerService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;


import com.twitter.redis.RedisService;
import com.twitter.redis.Result;

import org.springframework.util.SerializationUtils;


import java.util.*;

@Service
public class TweetService {


    @Autowired
    private RedisService redis;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired CustomerService userService;


    public static final String HASH_KEY = "Tweet";


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

        public Tweet getTweet(long tweet_id) throws Exception {


            if (redis.exists(String.valueOf(tweet_id))) {
                Tweet t = (Tweet) redis.hget(SerializationUtils.serialize(HASH_KEY), SerializationUtils.serialize(tweet_id));
                return t;
            }
            Tweet tweet = tweetRepository.findById(tweet_id).orElseThrow(() -> new ResourceNotFoundException("Tweet not found"));
            return tweet;
        }

        public Tweet deleteTweet(long tweet_id) throws Exception {

            Tweet tweet = tweetRepository.findById(tweet_id).orElseThrow(() -> new ResourceNotFoundException("Tweet not found"));
            tweetRepository.deleteById(tweet_id);
            redis.del(0, SerializationUtils.serialize(tweet_id));

            return tweet;
        }

        public Tweet updateTweet(long tweet_id, String content)throws Exception{
                Tweet tweet = tweetRepository.findById(tweet_id).orElse(null);
                if (tweet == null) {
                    throw new ResourceNotFoundException("tweet does not exist");
                }

                tweet.setContent(content);
                redis.hset(SerializationUtils.serialize(HASH_KEY), SerializationUtils.serialize(tweet_id), SerializationUtils.serialize(content));

                return tweet;
        }

        public Tweet createTweet(long tweet_id, String content)throws Exception {
                Tweet tweet = tweetRepository.findById(tweet_id).orElse(null);
                if (tweet == null) {
                    tweet = new Tweet(content);
                    tweetRepository.save(tweet);
                    return tweet;
                }
            redis.hset(SerializationUtils.serialize(HASH_KEY), SerializationUtils.serialize(tweet_id), SerializationUtils.serialize(content));
                return tweet;
        }

        public List<Tweet> findAll() {
            return tweetRepository.findAll();
        }

}
