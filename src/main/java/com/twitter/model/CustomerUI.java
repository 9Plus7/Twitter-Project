package com.twitter.model;

public class CustomerUI {
    private String firstName;
    private String lastName;
    private boolean popular;

    protected CustomerUI() {
    }

    public CustomerUI(String firstName, String lastName, boolean popular) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.popular = false;
    }
    public CustomerUI(String name){
        this.firstName = name;
        this.lastName = "";
        this.popular = false;
    }


    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Boolean getPopular() {return popular; }
    public void setPopular(boolean p) {this.popular = p; }

    public String toString() {
        return String.format("Customer[firstName='%s', lastName='%s']", firstName, lastName);
    }
}

