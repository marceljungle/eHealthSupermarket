package com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections;

import java.util.List;

public class Product {

    private String product_id;
    private String product_name;
    private String generic_name;
    private String quantity;
    private String packaging;
    private List<String> brands;
    private String information_phone;
    private String information_mail;
    private String factory_address;
    private String information_text;
    private List<Additive> additives;
    private List<Intolerance> intolerances;
    private String image;
    private String code;


    public Product() {

    }


    public Product(String product_id, String product_name, String generic_name, String quantity, String packaging, List<String> brands, String information_phone, String information_mail, String factory_address, String information_text, List<Additive> additives, List<Intolerance> intolerances, String image, String code) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.generic_name = generic_name;
        this.quantity = quantity;
        this.packaging = packaging;
        this.brands = brands;
        this.information_phone = information_phone;
        this.information_mail = information_mail;
        this.factory_address = factory_address;
        this.information_text = information_text;
        this.additives = additives;
        this.intolerances = intolerances;
        this.image = image;
        this.code = code;
    }

    public Product(String product_id, String generic_name, String quantity, String packaging, String image) {
        this.product_id = product_id;
        this.generic_name = generic_name;
        this.quantity = quantity;
        this.packaging = packaging;
        this.image = image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getGeneric_name() {
        return generic_name;
    }

    public void setGeneric_name(String generic_name) {
        this.generic_name = generic_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public String getInformation_phone() {
        return information_phone;
    }

    public void setInformation_phone(String information_phone) {
        this.information_phone = information_phone;
    }

    public String getInformation_mail() {
        return information_mail;
    }

    public void setInformation_mail(String information_mail) {
        this.information_mail = information_mail;
    }

    public String getFactory_address() {
        return factory_address;
    }

    public void setFactory_address(String factory_address) {
        this.factory_address = factory_address;
    }

    public String getInformation_text() {
        return information_text;
    }

    public void setInformation_text(String information_text) {
        this.information_text = information_text;
    }

    public List<Additive> getAdditives() {
        return additives;
    }

    public void setAdditives(List<Additive> additives) {
        this.additives = additives;
    }

    public List<Intolerance> getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(List<Intolerance> intolerances) {
        this.intolerances = intolerances;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    ///////////////////////////////////////////////////////////////
    public int getId() {
        return product_id.hashCode();
    }

    public static Product[] ITEMS = {
            new Product("1", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick", "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
            new Product("2", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick", "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
            new Product("3", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick", "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
            new Product("4", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick", "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
            new Product("5", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick", "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
            new Product("6", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick", "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
            new Product("1", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick", "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
            new Product("1", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick", "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
            new Product("1", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick", "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
    };

    /////////////////////////////////////////////////
    public static Product getItem(int id) {
        for (Product item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
