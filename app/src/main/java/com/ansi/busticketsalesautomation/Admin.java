package com.ansi.busticketsalesautomation;

public class Admin {

    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    public Admin(int id, String firstName, String lastName, String userName, String password) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setUserName(userName);
        setPassword(password);
    }


    public Admin(String firstName, String lastName, String userName, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setUserName(userName);
        setPassword(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
