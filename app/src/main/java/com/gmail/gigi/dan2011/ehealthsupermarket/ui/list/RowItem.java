package com.gmail.gigi.dan2011.ehealthsupermarket.ui.list;

import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import java.io.Serializable;
import java.util.List;

public class RowItem implements Serializable {

  private String id;
  private String listName;
  private String subHeading;
  private int smallImageName;
  private int bigImageName;
  private List<Product> productsInTheList;


  public RowItem(String id, String listName, String subHeading, int smallImageName,
      int bigImageName,
      List<Product> productsInTheList) {
    this.id = id;
    this.listName = listName;
    this.subHeading = subHeading;
    this.smallImageName = smallImageName;
    this.bigImageName = bigImageName;
    this.productsInTheList = productsInTheList;
  }

  public RowItem() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Product> getProductsInTheList() {
    return productsInTheList;
  }

  public void setProductsInTheList(
      List<Product> productsInTheList) {
    this.productsInTheList = productsInTheList;
  }

  public String getListName() {
    return listName;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public String getSubHeading() {
    return subHeading;
  }

  public void setSubHeading(String subHeading) {
    this.subHeading = subHeading;
  }

  public int getSmallImageName() {
    return smallImageName;
  }

  public void setSmallImageName(int smallImageName) {
    this.smallImageName = smallImageName;
  }

  public int getBigImageName() {
    return bigImageName;
  }

  public void setBigImageName(int bigImageName) {
    this.bigImageName = bigImageName;
  }
}
