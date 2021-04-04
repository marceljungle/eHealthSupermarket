package com.gmail.gigi.dan2011.ehealthsupermarket.collections;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Function example.
 */
public class Intolerance implements Serializable {

  private String intolerance_id;
  private String intolerance_name;
  private String about;
  private List<String> ingredientsInvolved;
  private List<String> ingredients_involved;

  public Intolerance() {
  }

  /**
   * Function example.
   */
  public Intolerance(
      String intolerance_id,
      String intolerance_name,
      String about,
      List<String> ingredientsInvolved) {
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

  public List<String> getIngredients_involved() {
    return ingredientsInvolved;
  }

  public void setIngredients_involved(List<String> ingredients_involved) {
    this.ingredientsInvolved = ingredients_involved;
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

  @Override
  public String toString() {
    return "Intolerance{"
        + "intoleranceId='"
        + intolerance_id
        + '\''
        + ", intoleranceName='"
        + intolerance_name
        + '\''
        + ", about='"
        + about
        + '\''
        + ", ingredientsInvolved="
        + ingredientsInvolved
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Intolerance that = (Intolerance) o;
    return Objects.equals(intolerance_id, that.intolerance_id)
        && Objects.equals(intolerance_name, that.intolerance_name)
        && Objects.equals(about, that.about)
        && Objects.equals(ingredientsInvolved, that.ingredientsInvolved);
  }

  @Override
  public int hashCode() {
    return Objects.hash(intolerance_id, intolerance_name, about, ingredientsInvolved);
  }
}
