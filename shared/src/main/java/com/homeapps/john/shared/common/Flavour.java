package com.homeapps.john.shared.common;

import java.io.Serializable;

public class Flavour implements Serializable{
    public String Name;
    public Integer Percentage;

    public Flavour(){
    }

    public Flavour(String Name, Integer Percentage){
        this.Name = Name;
        this.Percentage  = Percentage;
    }

}
