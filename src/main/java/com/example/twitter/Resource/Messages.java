/**
 * Messages.java: Class implementing the Messages function.
 * This class implements basic get/set functionality.
 * @author Zihao(Ares) Chen
 */


package com.example.twitter.Resource;

public class Messages {
    private int user_id; // ID of the user who sent the message
    private String message; // Content of the message

    /**
     * Default constructor function.
     */
    public Messages() {

    }

    /**
     * Constructor function.
     * @param user_id ID of the user who sent the message
     * @param message Content of the message
     */
    public Messages(int user_id, String message) {
        this.user_id = user_id;
        this.message = message;
    }

    /**
     * Function that returns the ID of the user.
     * @return User ID
     */
    public int getUserId() {
        return user_id;
    }

    /**
     * Function that sets the ID of the user.
     * @param user_id User ID
     */
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Function that returns the content of the message.
     * @return Message content
     */
    public String getMessage() {
        return message;
    }

    /**
     * Function that sets the content of the message.
     * @param message Message content
     */
    public void setMessage(String message) {
        this.message = message;
    }
}