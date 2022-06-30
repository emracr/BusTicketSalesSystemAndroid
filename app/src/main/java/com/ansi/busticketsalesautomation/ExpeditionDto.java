package com.ansi.busticketsalesautomation;

public class ExpeditionDto {

    private Expedition expedition;
    private Company company;

    public ExpeditionDto(){

    }

    public ExpeditionDto(Expedition expedition, Company company){
        setExpedition(expedition);
        setCompany(company);
    }

    public Expedition getExpedition() {
        return expedition;
    }

    public void setExpedition(Expedition expedition) {
        this.expedition = expedition;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
