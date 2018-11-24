package com.example.jage.sqliteapp.model;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private long companyID;
    private String companyName;
    private String webPage;
    private String phone;
    private String email;
    private CompanyType type;
    private List<ProductOrService> productsAndServices;

    public Company(long companyID,String companyName, String webPage, String phone, String email, CompanyType type){
        this.companyID = companyID;
        this.companyName = companyName;
        this.webPage = webPage;
        this.phone = phone;
        this.email =email;
        this.type =type;
        this.productsAndServices = new ArrayList<ProductOrService>();
    }

    public Company() {

    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
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

    public long getCompanyID() {
        return companyID;
    }

    public void setCompanyID(long companyID) {
        this.companyID = companyID;
    }

    public CompanyType getType() {
        return type;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }

    public enum CompanyType {
        CONSULTORIA("CONSULTORIA"),
        DESAROLLO_A_LA_MEDIDA("DESAROLLO_A_LA_MEDIDA"),
        FABRICA_DE_SOFTWARE("FABRICA_DE_SOFTWARE");
        private final String text;

        /**
         * @param text
         */
        CompanyType(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }
    }
}
