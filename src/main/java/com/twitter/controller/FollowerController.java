package com.twitter.controller;

import com.twitter.model.Follower;
import com.twitter.model.Customer;
import com.twitter.repo.FollowerRepository;
import com.twitter.repo.CustomerRepository;
import com.twitter.service.FollowService;
import com.twitter.service.ApiResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FollowerController {
    @Autowired
    private ApiResponse apiResponse;

    @Autowired
    FollowService followService;


    @PostMapping(path = "follow/{user_id}", produces = "application/json")
    public ResponseEntity<Object> addFollowee(
            Long curr_id, @PathVariable("user_id") long user_id
    ) throws Exception
    {
        followService.followUser(curr_id, user_id);
        apiResponse.setMessage("Followee Added!");
        apiResponse.setData(user_id);

        return new ResponseEntity<>(apiResponse.getBodyResponse(),HttpStatus.CREATED);
    }

    @DeleteMapping(path = "unfollow/user/{user_id}",  produces = "application/json")
    public ResponseEntity<Object> deleteFollowee(
            Long curr_id, @PathVariable("user_id") long user_id
    ) throws Exception
    {
        followService.unfollowUser(curr_id, user_id);
        apiResponse.setMessage("Followee Added!");
        apiResponse.setData(user_id);

        return new ResponseEntity<>(apiResponse.getBodyResponse(),HttpStatus.CREATED);
    }

}
