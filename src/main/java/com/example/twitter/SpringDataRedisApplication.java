package com.example.twitter;

import com.example.twitter.Resource.Cache;
import com.example.twitter.DAOs.CacheDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/product")
public class SpringDataRedisApplication {
    @Autowired
    private CacheDAO dao;

    @GetMapping
    public List<Cache> getAllCache() {
        return dao.findAll();
    }

    @GetMapping("/{id}")
    public Cache findCache(@PathVariable int id) {
        return dao.findCacheById(id);
    }

    @PostMapping
    public Cache save(@RequestBody Cache cache) {
        return dao.saveCache(cache);
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id) {
        return dao.deleteCache(id);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDataRedisApplication.class, args);
    }
}
