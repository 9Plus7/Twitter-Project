package com.twitter.model;

public class TweetUI  {


    private String content;

    protected TweetUI() {
    }

    public TweetUI(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return String.format("Tweet[content='%s']", content);
    }
}