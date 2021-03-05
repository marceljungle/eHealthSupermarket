package com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections;

import java.util.List;

public class Intolerance {

    private String intolerance_id;
    private String intolerance_name;
    private List<String> ingredients_involved;

    public Intolerance() {

    }

    public String getIntolerance_id() {
        return intolerance_id;
    }

    public void setIntolerance_id(String intolerance_id) {
        this.intolerance_id = intolerance_id;
    }

    public String getIntolerance_name() {
        return intolerance_name;
    }

    public void setIntolerance_name(String intolerance_name) {
        this.intolerance_name = intolerance_name;
    }

    public List<String> getIngredients_involved() {
        return ingredients_involved;
    }

    public void setIngredients_involved(List<String> ingredients_involved) {
        this.ingredients_involved = ingredients_involved;
    }

    public Intolerance(String intolerance_id, String intolerance_name, List<String> ingredients_involved) {
        this.intolerance_id = intolerance_id;
        this.intolerance_name = intolerance_name;
        this.ingredients_involved = ingredients_involved;
    }
}
