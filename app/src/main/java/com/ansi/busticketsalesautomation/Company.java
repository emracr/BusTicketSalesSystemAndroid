package com.ansi.busticketsalesautomation;

public class Company {

    private int id;
    private String companyName;
    private String username;
    private String password;
    private String logoPath;

    public Company(){

    }

    public Company(String companyName, String username, String password, String logoPath){
        setCompanyName(companyName);
        setUsername(username);
        setPassword(password);
        setLogoPath(logoPath);
    }

    public Company(int id, String companyName, String username, String password, String logoPath){
        setId(id);
        setCompanyName(companyName);
        setUsername(username);
        setPassword(password);
        setLogoPath(logoPath);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}
