package com.gmail.gigi.dan2011.ehealthsupermarket.collections;

import java.util.List;
import java.util.Objects;

/**
 * Function example.
 */
public class User {

  private String name;
  private String last_name;
  private String image;
  private String phone_number;
  private String nickname;
  private List<Product> liked_products;
  private List<Product> disliked_products;
  private List<Intolerance> intolerances;
  private List<Additive> unsupported_additives;
  private String email;
  private String idUser;

  public User() {
  }

  /**
   * Function example.
   */
  public User(
      String name,
      String last_name,
      String image,
      String phone_number,
      String nickname,
      List<Product> liked_products,
      List<Product> disliked_products,
      List<Intolerance> intolerances,
      List<Additive> unsupported_additives,
      String email,
      String idUser) {
    this.name = name;
    this.last_name = last_name;
    this.image = image;
    this.phone_number = phone_number;
    this.nickname = nickname;
    this.liked_products = liked_products;
    this.disliked_products = disliked_products;
    this.intolerances = intolerances;
    this.unsupported_additives = unsupported_additives;
    this.email = email;
    this.idUser = idUser;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getPhone_number() {
    return phone_number;
  }

  public void setPhone_number(String phone_number) {
    this.phone_number = phone_number;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public List<Product> getLiked_products() {
    return liked_products;
  }

  public void setLiked_products(List<Product> liked_products) {
    this.liked_products = liked_products;
  }

  public List<Product> getDisliked_products() {
    return disliked_products;
  }

  public void setDisliked_products(List<Product> disliked_products) {
    this.disliked_products = disliked_products;
  }

  public List<Intolerance> getIntolerances() {
    return intolerances;
  }

  public void setIntolerances(List<Intolerance> intolerances) {
    this.intolerances = intolerances;
  }

  public List<Additive> getUnsupported_additives() {
    return unsupported_additives;
  }

  public void setUnsupported_additives(List<Additive> unsupported_additives) {
    this.unsupported_additives = unsupported_additives;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getIdUser() {
    return idUser;
  }

  public void setIdUser(String idUser) {
    this.idUser = idUser;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(name, user.name)
        && Objects.equals(last_name, user.last_name)
        && Objects.equals(image, user.image)
        && Objects.equals(phone_number, user.phone_number)
        && Objects.equals(nickname, user.nickname)
        && Objects.equals(liked_products, user.liked_products)
        && Objects.equals(disliked_products, user.disliked_products)
        && Objects.equals(intolerances, user.intolerances)
        && Objects.equals(unsupported_additives, user.unsupported_additives)
        && Objects.equals(email, user.email)
        && Objects.equals(idUser, user.idUser);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        last_name,
        image,
        phone_number,
        nickname,
        liked_products,
        disliked_products,
        intolerances,
        unsupported_additives,
        email,
        idUser);
  }
}
