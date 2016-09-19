package com.donars.srp.bloodbank;

/**
 * Created by Rahul Satish on 26-08-2016.
 */
public class AdapterList {
    String address;
    String name;
    String hop_name;
    String blood_group;
    int quantity;

    public AdapterList(String address, String name, String hop_name,int quantity,String blood_group) {
        this.address = address;
        this.name = name;
        this.hop_name = hop_name;
        this.blood_group=blood_group;
        this.quantity=quantity;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getQuantity() {
        return  quantity+"";
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHop_name() {
        return hop_name;
    }

    public void setHop_name(String hop_name) {
        this.hop_name = hop_name;
    }
}
