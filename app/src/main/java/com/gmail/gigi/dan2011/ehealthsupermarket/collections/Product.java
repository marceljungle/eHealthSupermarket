package com.gmail.gigi.dan2011.ehealthsupermarket.collections;

import java.util.List;
import java.util.Objects;

/** Function example. */
public class Product {

  private String productId;
  private String productName;
  private String genericName;
  private String quantity;
  private String packaging;
  private List<String> brands;
  private String informationPhone;
  private String informationMail;
  private String factoryAddress;
  private String informationText;
  private List<Additive> additives;
  private List<Intolerance> intolerances;
  private String image;
  private String code;

  public Product() {}

  /** Function example. */
  public Product(
      String productId,
      String productName,
      String genericName,
      String quantity,
      String packaging,
      List<String> brands,
      String informationPhone,
      String informationMail,
      String factoryAddress,
      String informationText,
      List<Additive> additives,
      List<Intolerance> intolerances,
      String image,
      String code) {
    this.productId = productId;
    this.productName = productName;
    this.genericName = genericName;
    this.quantity = quantity;
    this.packaging = packaging;
    this.brands = brands;
    this.informationPhone = informationPhone;
    this.informationMail = informationMail;
    this.factoryAddress = factoryAddress;
    this.informationText = informationText;
    this.additives = additives;
    this.intolerances = intolerances;
    this.image = image;
    this.code = code;
  }

  public Product(String productId, String genericName, String quantity, String packaging,
      String image) {
    this.productId = productId;
    this.genericName = genericName;
    this.quantity = quantity;
    this.packaging = packaging;
    this.image = image;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getGenericName() {
    return genericName;
  }

  public void setGenericName(String genericName) {
    this.genericName = genericName;
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

  public String getInformationPhone() {
    return informationPhone;
  }

  public void setInformationPhone(String informationPhone) {
    this.informationPhone = informationPhone;
  }

  public String getInformationMail() {
    return informationMail;
  }

  public void setInformationMail(String informationMail) {
    this.informationMail = informationMail;
  }

  public String getFactoryAddress() {
    return factoryAddress;
  }

  public void setFactoryAddress(String factoryAddress) {
    this.factoryAddress = factoryAddress;
  }

  public String getInformationText() {
    return informationText;
  }

  public void setInformationText(String informationText) {
    this.informationText = informationText;
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

  public int getId() {
    return productId.hashCode();
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

  @Override
  public String toString() {
    return "Product{"
        + "productId='"
        + productId
        + '\''
        + ", productName='"
        + productName
        + '\''
        + ", genericName='"
        + genericName
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
        + informationPhone
        + '\''
        + ", informationMail='"
        + informationMail
        + '\''
        + ", factoryAddress='"
        + factoryAddress
        + '\''
        + ", informationText='"
        + informationText
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
    return Objects.equals(productId, product.productId)
        && Objects.equals(productName, product.productName)
        && Objects.equals(genericName, product.genericName)
        && Objects.equals(quantity, product.quantity)
        && Objects.equals(packaging, product.packaging)
        && Objects.equals(brands, product.brands)
        && Objects.equals(informationPhone, product.informationPhone)
        && Objects.equals(informationMail, product.informationMail)
        && Objects.equals(factoryAddress, product.factoryAddress)
        && Objects.equals(informationText, product.informationText)
        && Objects.equals(additives, product.additives)
        && Objects.equals(intolerances, product.intolerances)
        && Objects.equals(image, product.image)
        && Objects.equals(code, product.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        productId,
        productName,
        genericName,
        quantity,
        packaging,
        brands,
        informationPhone,
        informationMail,
        factoryAddress,
        informationText,
        additives,
        intolerances,
        image,
        code);
  }
}
