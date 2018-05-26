package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 22-Mar-18.
 */
public class EventSubscriberBean {

    private String name;

    private String contactNumber;

    private String email;

    private Integer userType;

    private Integer board;

    public EventSubscriberBean() {

    }

    public EventSubscriberBean(String name, String contactNumber, String email, Integer userType, Integer board) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.userType = userType;
        this.board = board;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getBoard() {
        return board;
    }

    public void setBoard(Integer board) {
        this.board = board;
    }
}
