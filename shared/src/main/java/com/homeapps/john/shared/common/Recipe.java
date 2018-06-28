package com.homeapps.john.shared.common;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable{
    public String Name;
    public Integer Ratio;
    public Integer NicotineBase;
    public Integer Nicotine;
    public ArrayList<Flavour> Flavours;

    public Recipe(){
        Flavours = new ArrayList<>();
    }
    public Recipe(String Name, Integer VGPGPercent, Integer NicBase,Integer Nicotine, ArrayList<Flavour> Flavours){
        this.Name = Name;
        this.Ratio = VGPGPercent;
        this.NicotineBase = NicBase;
        this.Nicotine = Nicotine;
        this.Flavours = Flavours;
    }

}
