package com.example.jage.sqliteapp.model;

public class ProductOrService {
    private String name;
    private Boolean isService;

    public ProductOrService(String name, Boolean isService){
        this.name = name;
        this.isService = isService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getService() {
        return isService;
    }

    public void setService(Boolean service) {
        isService = service;
    }
}
