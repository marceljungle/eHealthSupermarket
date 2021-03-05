package com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections;

public class Additive {

    private String additive_id;
    private String additive_name;
    private String additive_code;
    private Integer additive_danger_level;
    private String clasification;

    public Additive() {

    }

    public String getAdditive_id() {
        return additive_id;
    }

    public void setAdditive_id(String additive_id) {
        this.additive_id = additive_id;
    }

    public String getAdditive_name() {
        return additive_name;
    }

    public void setAdditive_name(String additive_name) {
        this.additive_name = additive_name;
    }

    public String getAdditive_code() {
        return additive_code;
    }

    public void setAdditive_code(String additive_code) {
        this.additive_code = additive_code;
    }

    public Integer getAdditive_danger_level() {
        return additive_danger_level;
    }

    public void setAdditive_danger_level(Integer additive_danger_level) {
        this.additive_danger_level = additive_danger_level;
    }

    public String getClasification() {
        return clasification;
    }

    public void setClasification(String clasification) {
        this.clasification = clasification;
    }


    public Additive(String additive_id, String additive_name, String additive_code, Integer additive_danger_level, String clasification) {
        this.additive_id = additive_id;
        this.additive_name = additive_name;
        this.additive_code = additive_code;
        this.additive_danger_level = additive_danger_level;
        this.clasification = clasification;
    }
}
