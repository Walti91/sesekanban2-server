package sese.responses;

import sese.entities.Customer;
import sese.entities.Gender;

import java.util.Date;

public class CustomerResponse {

    private Long id;
    private String name;
    private Date birthday;
    private Gender gender;
    private String billingAddress;
    private String companyName;
    private String note;
    private int discount;
    private String telephoneNumber;
    private String email;
    private String web;
    private String fax;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.birthday = customer.getBirthday();
        this.gender = customer.getGender();
        this.billingAddress = customer.getBillingAddress();
        this.companyName = customer.getCompanyName();
        this.note = customer.getNote();
        this.discount = customer.getDiscount();
        this.telephoneNumber = customer.getTelephoneNumber();
        this.email = customer.getEmail();
        this.web = customer.getWeb();
        this.fax = customer.getFax();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
