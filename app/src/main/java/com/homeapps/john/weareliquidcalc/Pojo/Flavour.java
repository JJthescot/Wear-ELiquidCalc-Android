package com.homeapps.john.weareliquidcalc.Pojo;

import java.io.Serializable;

public class Flavour implements Serializable{
    private String name;
    private Integer percentage;

    public Flavour(){
    }

    public Flavour(String Name, Integer Percentage){
        this.name = Name;
        this.percentage = Percentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}
