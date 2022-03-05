package com.example.twitter.DAOs;

/**
 * MessageDAO.java: Interface implementing the Data Access Object that contains
 * functionality related to user messages. Individual function documentation is
 * in MessageDAOclass.java.
 * @author Zihao(Ares) Chen
 */

import java.util.List;
import com.example.twitter.Resource.Messages;
import com.example.twitter.Resource.User;

public interface MessageDAO {

    public int addMessage(String content);
    public List<Messages> getUserMessages();
    public List<Messages> searchUserMessages(String keyword);
    public int getId();
}
