package com.twitter.service;

import com.twitter.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.twitter.model.Follower;

import com.twitter.repo.CustomerRepository;
import com.twitter.repo.FollowerRepository;
import com.twitter.model.Customer;
import com.twitter.redis.RedisService;

import java.util.HashMap;
import java.util.Map;

@Service
public class FollowService {

    @Autowired
    private CustomerRepository userRepository;
    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private CustomerService service;

    @Autowired
    private RedisService redis;

    public void followUser(long user_id, long follower_id) throws Exception {

        Customer user =userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        Customer follower = userRepository.findById(follower_id).orElseThrow(() -> new ResourceNotFoundException("User not found."));


        Follower f = new Follower();
        f.setFollower(follower);
        f.setFollowee(user);
        followerRepository.save(f);

    }



    public void unfollowUser(Long user_id, Long follower_id) throws Exception
    {
        Customer user =userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        Customer follower = userRepository.findById(follower_id).orElseThrow(() -> new ResourceNotFoundException("User not found."));


        Follower f = followerRepository.findByFolloweeAndFollower(user,follower).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        followerRepository.delete(f);

    }


}
