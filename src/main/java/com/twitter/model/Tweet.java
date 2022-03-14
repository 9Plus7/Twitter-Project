package com.twitter.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tweet")
public class Tweet implements Serializable {

    private static final long serialVersionUID = -2343243243242432341L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long tweet_id;

    @Column(name = "id")
    private long userId;

    @Column(name = "content")
    private String content;

    protected Tweet() {
    }

    public Tweet(String content) {

        this.content = content;
    }



    @Override
    public String toString() {
        return String.format("tweet_id[id=%d, content='%s']", tweet_id, content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String s) {this.content = s;}
}
