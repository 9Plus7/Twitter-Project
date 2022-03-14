package com.twitter.model;

import javax.persistence.*;

@Entity
@Table(name = "follower")
public class Follower {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "followee")
    private Customer followee;

    @ManyToOne
    @JoinColumn(name = "follower")
    private Customer follower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getFollowee() {
        return followee;
    }

    public void setFollowee(Customer followee) {
        this.followee = followee;
    }

    public Customer getFollower() {
        return follower;
    }

    public void setFollower(Customer follower) {
        this.follower = follower;
    }


}
