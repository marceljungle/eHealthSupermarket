package com.gmail.gigi.dan2011.ehealthsupermarket.collections;

import java.util.List;
import java.util.Objects;

/**
 * Function example.
 */
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

  /**
   * Function example.
   */
  public Product(
      String product_id,
      String product_name,
      String generic_name,
      String quantity,
      String packaging,
      List<String> brands,
      String information_phone,
      String information_mail,
      String factory_address,
      String information_text,
      List<Additive> additives,
      List<Intolerance> intolerances,
      String image,
      String code) {
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

  /**
   * Javadoc comment.
   */
  public Product(String product_id, String generic_name, String quantity, String packaging,
      String image) {
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

  public int idGet() {
    return product_id.hashCode();
  }

  public static Product[] ITEMS = {
      new Product("1", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick",
          "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
      new Product("2", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick",
          "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
      new Product("3", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick",
          "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
      new Product("4", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick",
          "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
      new Product("5", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick",
          "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
      new Product("6", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick",
          "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
      new Product("1", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick",
          "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
      new Product("1", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick",
          "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
      new Product("1", "Leche semidesnatada sin lactosa Hacendado", "1 L", "Brick",
          "https://prod-mercadona.imgix.net/images/6c49659a46a8540a915d5925646b889e.jpg"),
  };

  /**
   * Javadoc comment.
   */
  public static Product getItem(int id) {
    for (Product item : ITEMS) {
      if (item.idGet() == id) {
        return item;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return "Product{"
        + "productId='"
        + product_id
        + '\''
        + ", productName='"
        + product_name
        + '\''
        + ", genericName='"
        + generic_name
        + '\''
        + ", quantity='"
        + quantity
        + '\''
        + ", packaging='"
        + packaging
        + '\''
        + ", brands="
        + brands
        + ", informationPhone='"
        + information_phone
        + '\''
        + ", informationMail='"
        + information_mail
        + '\''
        + ", factoryAddress='"
        + factory_address
        + '\''
        + ", informationText='"
        + information_text
        + '\''
        + ", additives="
        + additives
        + ", intolerances="
        + intolerances
        + ", image='"
        + image
        + '\''
        + ", code='"
        + code
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
    Product product = (Product) o;
    return Objects.equals(product_id, product.product_id)
        && Objects.equals(product_name, product.product_name)
        && Objects.equals(generic_name, product.generic_name)
        && Objects.equals(quantity, product.quantity)
        && Objects.equals(packaging, product.packaging)
        && Objects.equals(brands, product.brands)
        && Objects.equals(information_phone, product.information_phone)
        && Objects.equals(information_mail, product.information_mail)
        && Objects.equals(factory_address, product.factory_address)
        && Objects.equals(information_text, product.information_text)
        && Objects.equals(additives, product.additives)
        && Objects.equals(intolerances, product.intolerances)
        && Objects.equals(image, product.image)
        && Objects.equals(code, product.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        product_id,
        product_name,
        generic_name,
        quantity,
        packaging,
        brands,
        information_phone,
        information_mail,
        factory_address,
        information_text,
        additives,
        intolerances,
        image,
        code);
  }
}
