package com.twitter.repo;

import com.twitter.model.Follower;
import com.twitter.model.Customer;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {

    @Query("select f from Follower f where f.follower = ?1")
    List<Follower> findTweetsThatUserFollows(Customer user);

    @Query("select f from Follower f where f.followee = ?1 and f.follower=?2")
    Optional<Follower> findByFolloweeAndFollower(Customer followee, Customer follower);
}
