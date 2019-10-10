package com.ecobank.rapidtransfer.obj;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustParameter {
//    @JsonProperty("prd")
    String product;
    String customerid;
    String firstname;
    String lastname;
    String p_phone_num;
    String  pdob;

    public String getP_phone_num() {
        return p_phone_num;
    }

    public void setP_phone_num(String p_phone_num) {
        this.p_phone_num = p_phone_num;
    }



    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPdob() {
        return pdob;
    }

    public void setPdob(String pdob) {
        this.pdob = pdob;
    }
}
