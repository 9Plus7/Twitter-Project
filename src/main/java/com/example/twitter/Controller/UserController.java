package com.example.twitter.Controller;

/**
 * UserController.java: User Controller class.
 * This is one of two controllers used by the web application. The functions
 * implemented are related to the users. Methods are either HTTP GET or POST
 * and the output is in JSON format.
 * @author Zihao(Ares) Chen
 */

import com.example.twitter.DAOs.UserDAO;
import com.example.twitter.Resource.User;
import com.example.twitter.Resource.Followers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@RestController
public class UserController {

    private UserDAO myUserDAO;

    /**
     * Constructor function to initialize the beans
     * @param myUserDAO DAO
     */
    @Autowired
    public UserController(UserDAO myUserDAO) {
        this.myUserDAO = myUserDAO;
    }

    /**
     * Function that allows user to follow another user
     * @param follower_id id the user to be followed. (Path Variable)
     * @return JSON object
     */
    @RequestMapping(value="/user/follow/{follower_id}", method=RequestMethod.POST,
                    produces="application/json")
    @ResponseBody
    public ResponseEntity followUser(@PathVariable int follower_id) {
        int follow = myUserDAO.follow(follower_id);
        if (follow == 1) {
            return ResponseEntity.ok("{\"message\":\"Success!\"}");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Error!\"}");
        }
    }

    /**
     * Function that allows the current user to unfollow another user.
     * @param follower_id id of the user to be unfollowed. (Path)
     * @return JSON object
     */

    @RequestMapping(value="/user/unfollow/{follower_id}", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public ResponseEntity unfollowUser(@PathVariable int follower_id) {
        int unfollow = myUserDAO.unfollow(follower_id);

        if (unfollow == 1)
            return ResponseEntity.ok("{\"message\":\"Success!\"}");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Error!\"}");

    }

    /**
     * Function that list all the available users.
     * @return List of users.
     */
    @RequestMapping(value="/user", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<User> listUsers() {
        return myUserDAO.listAllUsers();
    }

    /**
     * Function that lists all the followers of the current user.
     * @return List of all followers.
     */
    @RequestMapping(value="/followers", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<Followers> listFollowers() {
        return myUserDAO.listAllFollowers();
    }

    /**
     * Function that lists all the following of the current user.
     * @return List of all following of current user.
     */
    @RequestMapping(value="/following", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<Followers> listFollowing() {
        return myUserDAO.listAllFollowing();
    }

    /**
     * Function that list all users along with their most popular follower.
     * The most popular follower is who has the most followers in user's
     * following list. In case two or more people have the highest number of followers,
     * an arbitrary selection is made.
     * @return List of users along with their most popular follower
     */
    @RequestMapping(value="/option2", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<Followers> listUsersWithTheirMostPopularFollower() {
        return myUserDAO.listUsersWithMostPopularFollower();
    }



}
