package com.twitter.service;

import com.twitter.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.twitter.repo.CustomerRepository;
import com.twitter.repo.FollowerRepository;
import com.twitter.model.Follower;
import com.twitter.model.Customer;

@Service
public class FollowService {

    @Autowired
    private CustomerRepository R_User;
    @Autowired
    FollowerRepository R_Follower;

    public void followUser(Long curr_id, Long user_id) throws Exception
    {
        Customer LoggedInUser = R_User.findById(curr_id).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        Customer FolloweeUser = R_User.findById(user_id).orElse(null);
        if(FolloweeUser == null)
        {
            throw new Exception("User not found!");
        }
        Follower f = new Follower();
        f.setFollower(LoggedInUser);
        f.setFollowee(FolloweeUser);
        R_Follower.save(f);
    }

    public void unfollowUser(Long curr_id, Long user_id) throws Exception
    {
        Customer LoggedInUser = R_User.findById(curr_id).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        Customer FolloweeUser = R_User.findById(user_id).orElse(null);
        if(FolloweeUser == null)
        {
            throw new Exception("User not found!");
        }

        Follower f = R_Follower.findByFolloweeAndFollower(FolloweeUser,LoggedInUser).orElse(null);
        if(f == null)
        {
            throw new Exception("User not following " + FolloweeUser.getFirstName());
        }
        R_Follower.delete(f);

    }
}
