package com.ansi.busticketsalesautomation;

public class TicketReservation {

    private int id;
    private int expeditionId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String departureDate;
    private String gender;
    private int seatNumber;
    private int price;

    public TicketReservation(){

    }

    public TicketReservation(int id, int expeditionId, String firstName, String lastName, String phoneNumber, String email, String departureDate, String gender, int seatNumber, int price){
        setId(id);
        setExpeditionId(expeditionId);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setDepartureDate(departureDate);
        setGender(gender);
        setSeatNumber(seatNumber);
        setPrice(price);
    }

    public TicketReservation(int expeditionId, String firstName, String lastName, String phoneNumber, String email, String departureDate, String gender, int seatNumber, int price){
        setExpeditionId(expeditionId);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
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

    public int getExpeditionId() {
        return expeditionId;
    }

    public void setExpeditionId(int expeditionId) {
        this.expeditionId = expeditionId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
