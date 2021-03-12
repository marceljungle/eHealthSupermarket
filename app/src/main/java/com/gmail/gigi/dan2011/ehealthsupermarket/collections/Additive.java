package com.gmail.gigi.dan2011.ehealthsupermarket.collections;

import java.util.Objects;

/** Function example. */
public class Additive {

  private String additiveId;
  private String additiveName;
  private String additiveCode;
  private Integer additiveDangerLevel;
  private String clasification;

  public Additive() {}

  public String getAdditiveId() {
    return additiveId;
  }

  public void setAdditiveId(String additiveId) {
    this.additiveId = additiveId;
  }

  public String getAdditiveName() {
    return additiveName;
  }

  public void setAdditiveName(String additiveName) {
    this.additiveName = additiveName;
  }

  public String getAdditiveCode() {
    return additiveCode;
  }

  public void setAdditiveCode(String additiveCode) {
    this.additiveCode = additiveCode;
  }

  public Integer getAdditiveDangerLevel() {
    return additiveDangerLevel;
  }

  public void setAdditiveDangerLevel(Integer additiveDangerLevel) {
    this.additiveDangerLevel = additiveDangerLevel;
  }

  public String getClasification() {
    return clasification;
  }

  public void setClasification(String clasification) {
    this.clasification = clasification;
  }

  /** Function example. */
  public Additive(
      String additiveId,
      String additiveName,
      String additiveCode,
      Integer additiveDangerLevel,
      String clasification) {
    this.additiveId = additiveId;
    this.additiveName = additiveName;
    this.additiveCode = additiveCode;
    this.additiveDangerLevel = additiveDangerLevel;
    this.clasification = clasification;
  }

  @Override
  public String toString() {
    return "Additive{"
        + "additiveId='"
        + additiveId
        + '\''
        + ", additiveName='"
        + additiveName
        + '\''
        + ", additiveCode='"
        + additiveCode
        + '\''
        + ", additiveDangerLevel="
        + additiveDangerLevel
        + ", clasification='"
        + clasification
        + '\''
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
    Additive additive = (Additive) o;
    return Objects.equals(additiveId, additive.additiveId)
        && Objects.equals(additiveName, additive.additiveName)
        && Objects.equals(additiveCode, additive.additiveCode)
        && Objects.equals(additiveDangerLevel, additive.additiveDangerLevel)
        && Objects.equals(clasification, additive.clasification);
  }

  @Override
  public int hashCode() {
    return Objects.hash(additiveId, additiveName, additiveCode, additiveDangerLevel, clasification);
  }
}
