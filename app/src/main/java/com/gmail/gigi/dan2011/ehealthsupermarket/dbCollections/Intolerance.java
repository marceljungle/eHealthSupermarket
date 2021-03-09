package com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections;

import java.util.List;

public class Intolerance {

    private String intolerance_id;
    private String intolerance_name;
    private String about;
    private List<String> ingredientsInvolved;

    public Intolerance() {

    }

    public Intolerance(String intolerance_id, String intolerance_name, String about, List<String> ingredientsInvolved) {
        this.intolerance_id = intolerance_id;
        this.intolerance_name = intolerance_name;
        this.about = about;
        this.ingredientsInvolved = ingredientsInvolved;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<String> getIngredientsInvolved() {
        return ingredientsInvolved;
    }

    public void setIngredientsInvolved(List<String> ingredientsInvolved) {
        this.ingredientsInvolved = ingredientsInvolved;
    }
}
