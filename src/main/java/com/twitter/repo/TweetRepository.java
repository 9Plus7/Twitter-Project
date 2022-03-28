package com.twitter.repo;

import com.twitter.model.CustomerUI;
import com.twitter.model.Tweet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TweetRepository extends CrudRepository<Tweet, Long>{
    List<Tweet> findAll();
    List<Tweet> findTweetByUserId(long userId);


    @Query(value = "SELECT a FROM Tweet a WHERE a.content like %:str%")
    List<Tweet> fetchTweetsWithContent(@Param("str") String str);

    @Query("select t from Tweet t where tweeter_userid =?1")
    List<Tweet> findLatestTweetByUser(Long userid);


    @Query(value = "select * from Tweet where tweeter_userid in (select followee from Follwer where follower =?1)", nativeQuery = true)
    List<Tweet> findTweetsThatUserFollows(CustomerUI user);
}
