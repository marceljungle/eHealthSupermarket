package com.gmail.gigi.dan2011.ehealthsupermarket.collections;

import java.util.List;
import java.util.Objects;

/** Function example. */
public class Intolerance {

  private String intoleranceId;
  private String intoleranceName;
  private String about;
  private List<String> ingredientsInvolved;

  public Intolerance() {}

  /** Function example. */
  public Intolerance(
      String intoleranceId,
      String intoleranceName,
      String about,
      List<String> ingredientsInvolved) {
    this.intoleranceId = intoleranceId;
    this.intoleranceName = intoleranceName;
    this.about = about;
    this.ingredientsInvolved = ingredientsInvolved;
  }

  public String getIntoleranceId() {
    return intoleranceId;
  }

  public void setIntoleranceId(String intoleranceId) {
    this.intoleranceId = intoleranceId;
  }

  public String getIntoleranceName() {
    return intoleranceName;
  }

  public void setIntoleranceName(String intoleranceName) {
    this.intoleranceName = intoleranceName;
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
        + intoleranceId
        + '\''
        + ", intoleranceName='"
        + intoleranceName
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
    return Objects.equals(intoleranceId, that.intoleranceId)
        && Objects.equals(intoleranceName, that.intoleranceName)
        && Objects.equals(about, that.about)
        && Objects.equals(ingredientsInvolved, that.ingredientsInvolved);
  }

  @Override
  public int hashCode() {
    return Objects.hash(intoleranceId, intoleranceName, about, ingredientsInvolved);
  }
}
