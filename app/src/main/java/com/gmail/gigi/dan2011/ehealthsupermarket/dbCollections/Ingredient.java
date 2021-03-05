package com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections;

public class Ingredient {

    private String id_ingredient;
    private String ingredient_name;

    public Ingredient() {

    }

    public String getId_ingredient() {
        return id_ingredient;
    }

    public void setId_ingredient(String id_ingredient) {
        this.id_ingredient = id_ingredient;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public Ingredient(String id_ingredient, String ingredient_name) {
        this.id_ingredient = id_ingredient;
        this.ingredient_name = ingredient_name;
    }
}
