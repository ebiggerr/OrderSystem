package com.Wendys;

public class Customer {

    String cust_Name;
    int id;

    public Customer(int id,String name){
        this.cust_Name=name;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCust_Name() {
        return cust_Name;
    }

    public void setCust_Name(String cust_Name) {
        this.cust_Name = cust_Name;
    }


}
