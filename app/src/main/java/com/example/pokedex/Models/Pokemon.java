package com.example.pokedex.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Pokemon implements Serializable {

    private static final long id = 1L;

    private String name;
    private String indexNum;
    private ArrayList<String> abilities;
    private String height;
    private String weight;
    private String image;
    private ArrayList<String> type;
    private String flavorText;
    private String evolvesFrom;
    private String genus;
    private String captureRate;
    private String baseHappiness;

    // Constructor
    public Pokemon() {
    }

    // Getters and Setters

    // from pokemon object
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(String indexNum) {
        this.indexNum = indexNum;
    }

    public ArrayList<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<String> abilities) {
        this.abilities = abilities;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    // from pokemon-species object
    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public String getEvolvesFrom() {
        return evolvesFrom;
    }

    public void setEvolvesFrom(String evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getCaptureRate() {
        return captureRate;
    }

    public void setCaptureRate(String captureRate) {
        this.captureRate = captureRate;
    }

    public String getBaseHappiness() {
        return baseHappiness;
    }

    public void setBaseHappiness(String baseHappiness) {
        this.baseHappiness = baseHappiness;
    }
}

