package com.gmail.gigi.dan2011.ehealthsupermarket.collections;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Featured implements Serializable {

  private String featured_id;

  private String featured_image;

  private String featured_title;

  private String featured_subtitle;

  private List<Product> featured_products;

  public Featured(String featured_id, String featured_image, String featured_title,
      String featured_subtitle, List<Product> featured_products) {
    this.featured_id = featured_id;
    this.featured_image = featured_image;
    this.featured_title = featured_title;
    this.featured_subtitle = featured_subtitle;
    this.featured_products = featured_products;
  }

  public Featured() {

  }

  public String getFeatured_id() {
    return featured_id;
  }

  public void setFeatured_id(String featured_id) {
    this.featured_id = featured_id;
  }

  public String getFeatured_image() {
    return featured_image;
  }

  public void setFeatured_image(String featured_image) {
    this.featured_image = featured_image;
  }

  public List<Product> getFeatured_products() {
    return featured_products;
  }

  public void setFeatured_products(
      List<Product> featured_products) {
    this.featured_products = featured_products;
  }

  public String getFeatured_title() {
    return featured_title;
  }

  public void setFeatured_title(String featured_title) {
    this.featured_title = featured_title;
  }

  public String getFeatured_subtitle() {
    return featured_subtitle;
  }

  public void setFeatured_subtitle(String featured_subtitle) {
    this.featured_subtitle = featured_subtitle;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Featured featured = (Featured) o;
    return Objects.equals(featured_id, featured.featured_id) &&
        Objects.equals(featured_image, featured.featured_image) &&
        Objects.equals(featured_title, featured.featured_title) &&
        Objects.equals(featured_subtitle, featured.featured_subtitle) &&
        Objects.equals(featured_products, featured.featured_products);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(featured_id, featured_image, featured_title, featured_subtitle, featured_products);
  }

  @Override
  public String toString() {
    return "Featured{" +
        "featured_id='" + featured_id + '\'' +
        ", featured_image='" + featured_image + '\'' +
        ", featured_title='" + featured_title + '\'' +
        ", featured_subtitle='" + featured_subtitle + '\'' +
        ", featured_products=" + featured_products +
        '}';
  }
}
