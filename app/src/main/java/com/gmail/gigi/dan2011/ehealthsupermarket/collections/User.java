package com.gmail.gigi.dan2011.ehealthsupermarket.collections;

import java.util.List;
import java.util.Objects;

/**
 * Function example.
 */
public class User {

  private String name;
  private String lastName;
  private String image;
  private String phoneNumber;
  private String nickname;
  private List<Product> likedProducts;
  private List<Product> dislikedProducts;
  private List<Intolerance> intolerances;
  private List<Additive> unsupportedAdditives;
  private String email;
  private String idUser;

  public User() {
  }

  /**
   * Function example.
   */
  public User(
      String name,
      String lastName,
      String image,
      String phoneNumber,
      String nickname,
      List<Product> likedProducts,
      List<Product> dislikedProducts,
      List<Intolerance> intolerances,
      List<Additive> unsupportedAdditives,
      String email,
      String idUser) {
    this.name = name;
    this.lastName = lastName;
    this.image = image;
    this.phoneNumber = phoneNumber;
    this.nickname = nickname;
    this.likedProducts = likedProducts;
    this.dislikedProducts = dislikedProducts;
    this.intolerances = intolerances;
    this.unsupportedAdditives = unsupportedAdditives;
    this.email = email;
    this.idUser = idUser;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public List<Product> getLikedProducts() {
    return likedProducts;
  }

  public void setLikedProducts(List<Product> likedProducts) {
    this.likedProducts = likedProducts;
  }

  public List<Product> getDislikedProducts() {
    return dislikedProducts;
  }

  public void setDislikedProducts(List<Product> dislikedProducts) {
    this.dislikedProducts = dislikedProducts;
  }

  public List<Intolerance> getIntolerances() {
    return intolerances;
  }

  public void setIntolerances(List<Intolerance> intolerances) {
    this.intolerances = intolerances;
  }

  public List<Additive> getUnsupportedAdditives() {
    return unsupportedAdditives;
  }

  public void setUnsupportedAdditives(List<Additive> unsupportedAdditives) {
    this.unsupportedAdditives = unsupportedAdditives;
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
        && Objects.equals(lastName, user.lastName)
        && Objects.equals(image, user.image)
        && Objects.equals(phoneNumber, user.phoneNumber)
        && Objects.equals(nickname, user.nickname)
        && Objects.equals(likedProducts, user.likedProducts)
        && Objects.equals(dislikedProducts, user.dislikedProducts)
        && Objects.equals(intolerances, user.intolerances)
        && Objects.equals(unsupportedAdditives, user.unsupportedAdditives)
        && Objects.equals(email, user.email)
        && Objects.equals(idUser, user.idUser);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        lastName,
        image,
        phoneNumber,
        nickname,
        likedProducts,
        dislikedProducts,
        intolerances,
        unsupportedAdditives,
        email,
        idUser);
  }
}
