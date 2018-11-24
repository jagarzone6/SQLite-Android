package com.example.jage.sqliteapp.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private String companyName;
    private URL webPage;
    private String phone;
    private String email;
    private CompanyType type;
    private List<ProductOrService> productsAndServices;

    Company(String companyName, URL webPage, String phone, String email, CompanyType type){
        this.companyName = companyName;
        this.webPage = webPage;
        this.phone = phone;
        this.email =email;
        this.type =type;
        this.productsAndServices = new ArrayList<ProductOrService>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public URL getWebPage() {
        return webPage;
    }

    public void setWebPage(URL webPage) {
        this.webPage = webPage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ProductOrService> getProductsAndServices() {
        return productsAndServices;
    }

    public void addProductOrService(ProductOrService product) {
        this.productsAndServices.add(product);
    }

    public enum CompanyType {
        CONSULTORIA,
        DESAROLLO_A_LA_MEDIDA,
        FABRICA_DE_SOFTWARE
    }
}
