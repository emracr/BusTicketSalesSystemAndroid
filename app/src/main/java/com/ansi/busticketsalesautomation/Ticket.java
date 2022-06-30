package com.ansi.busticketsalesautomation;

public class Ticket {

    private int id;
    private int userId;
    private int expeditionId;
    private String departureDate;
    private String gender;
    private int seatNumber;
    private int price;

    public Ticket(){

    }

    public Ticket(int id, int userId, int expeditionId, String departureDate, String gender, int seatNumber, int price){
        setId(id);
        setUserId(userId);
        setExpeditionId(expeditionId);
        setDepartureDate(departureDate);
        setGender(gender);
        setSeatNumber(seatNumber);
        setPrice(price);
    }

    public Ticket(int userId, int expeditionId, String departureDate, String gender, int seatNumber, int price){
        setUserId(userId);
        setExpeditionId(expeditionId);
        setDepartureDate(departureDate);
        setGender(gender);
        setSeatNumber(seatNumber);
        setPrice(price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExpeditionId() {
        return expeditionId;
    }

    public void setExpeditionId(int expeditionId) {
        this.expeditionId = expeditionId;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
