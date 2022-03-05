package com.example.twitter.DAOs;
/**
 * UserDAO.java: Interface implementing the Data Access Object that contains
 * functionality related to users. Individual function documentation is
 * in UserDAOclass.java.
 * @author Zihao(Ares) Chen
 */

import java.util.List;
import com.example.twitter.Resource.Followers;
import com.example.twitter.Resource.User;

public interface UserDAO {
    int follow(int follower_id);
    int unfollow(int follower_id);
    List<User> listAllUsers();
    List<Followers> listAllFollowers();
    List<Followers> listAllFollowing();
    List<Followers> listUsersWithMostPopularFollower();
    int getId();
    boolean isFollower(int follower_id);
}
