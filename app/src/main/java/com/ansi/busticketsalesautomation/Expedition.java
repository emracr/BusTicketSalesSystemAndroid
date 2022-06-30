package com.ansi.busticketsalesautomation;

public class Expedition {

    private int id;
    private int companyId;
    private String cityFrom;
    private String cityTo;
    private String departureTime;
    private int price;

    public Expedition(){

    }

    public Expedition(int id, int companyId, String cityFrom, String cityTo, String departureTime, int price){
        setId(id);
        setCompanyId(companyId);
        setCityFrom(cityFrom);
        setCityTo(cityTo);
        setDepartureTime(departureTime);
        setPrice(price);
    }

    public Expedition(int companyId, String cityFrom, String cityTo, String departureTime, int price){
        setCompanyId(companyId);
        setCityFrom(cityFrom);
        setCityTo(cityTo);
        setDepartureTime(departureTime);
        setPrice(price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
