/**
 * User.java: Class implementing the user basic resource .
 * This class implements basic get/set functionality.
 * @author Zihao(Ares) Chen
 */

package com.example.twitter.Resource;

public class User {
    private int user_id; // ID of the user
    private String handle; // Username
    private String name; // Name of the user.

    /**
     * Default constructor function.
     */
    public User() {
    }

    /**
     * Constructor function.
     * @param user_id ID of the user
     * @param handle Username
     * @param name Name of the user
     */
    public User(int user_id, String handle, String name) {
        this.user_id = user_id;
        this.handle = handle;
        this.name = name;
    }

    /**
     * Function that returns the ID of the user.
     * @return User ID
     */
    public int getId() {
        return user_id;
    }

    /**
     * Function that sets the ID of the user.
     * @param id User ID
     */
    public void setId(int id) {
        this.user_id = id;
    }

    /**
     * Function that returns the username.
     * @return Username
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Function that sets the username.
     * @param handle Username
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Function that returns the name of the user.
     * @return Name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Function that sets the name of the user.
     * @param name Name of the user
     */
    public void setName(String name) {
        this.name = name;
    }
}
