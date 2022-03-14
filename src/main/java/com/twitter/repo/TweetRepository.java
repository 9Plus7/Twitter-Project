package com.twitter.repo;

import com.twitter.model.Tweet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TweetRepository extends CrudRepository<Tweet, Long>{
    List<Tweet> findAll();
    List<Tweet> findTweetByUserId(long userId);
    List<Tweet> findById(long tweetID);

    @Query(value = "SELECT a FROM Tweet a WHERE a.content like %:str%")
    List<Tweet> fetchTweetsWithContent(@Param("str") String str);

}
