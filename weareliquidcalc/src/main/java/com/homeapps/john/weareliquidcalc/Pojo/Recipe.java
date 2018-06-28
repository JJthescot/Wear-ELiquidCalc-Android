package com.homeapps.john.weareliquidcalc.Pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable{
    private String name;
    private Integer ratio;
    private Integer nicotinebase;
    private Integer nicotine;
    private ArrayList<Flavour> flavours;

    public Recipe(){
        flavours = new ArrayList<>();
    }
    public Recipe(String Name, Integer VGPGPercent, Integer NicBase,Integer Nicotine, ArrayList<Flavour> Flavours){
        this.name = Name;
        this.ratio = VGPGPercent;
        this.nicotinebase = NicBase;
        this.nicotine = Nicotine;
        this.flavours = Flavours;
    }

    public ArrayList<Flavour> getFlavours() {
        return flavours;
    }

    public void setFlavours(ArrayList<Flavour> flavours) {
        this.flavours = flavours;
    }

    public Integer getNicotine() {
        return nicotine;
    }

    public void setNicotine(Integer nicotine) {
        this.nicotine = nicotine;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNicotinebase() {
        return nicotinebase;
    }

    public void setNicotinebase(Integer nicotinebase) {
        this.nicotinebase = nicotinebase;
    }
}
