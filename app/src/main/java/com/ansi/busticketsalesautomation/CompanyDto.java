package com.ansi.busticketsalesautomation;

import java.util.List;

public class CompanyDto {

    private Company company;
    private List<Expedition> expeditions;

    public CompanyDto(){

    }

    public CompanyDto(Company company, List<Expedition> expeditions){
        setCompany(company);
        setExpeditions(expeditions);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Expedition> getExpeditions() {
        return expeditions;
    }

    public void setExpeditions(List<Expedition> expeditions) {
        this.expeditions = expeditions;
    }
}
