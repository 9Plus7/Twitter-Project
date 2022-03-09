package com.example.twitter.DAOs;

import com.example.twitter.Resource.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CacheDAO {

    public static final String HASH_KEY = "Cache";
    @Autowired
    private RedisTemplate template;


    public List<Cache> findAll () {
        return template.opsForHash().values(HASH_KEY);
    }

    public Cache findCacheById(int id) {

        return (Cache) template.opsForHash().get(HASH_KEY, id);
    }

    public Cache saveCache (Cache c) {
        template.opsForHash().put(HASH_KEY, c.getId(), c);
        return c;
    }

    public String deleteCache(int id) {
        template.opsForHash().delete(HASH_KEY, id);

        return "cache removed.";
    }



}
