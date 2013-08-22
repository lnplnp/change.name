package fr.manuelpayet.jsonobject;

public class User {
  public enum Gender {
    female, male
  };

  private String email;
  private Gender gender;
  private Name name;
  private byte[] picture;
  private String seed;

  public String getEmail() {
    return email;
  }

  public Gender getGender() {
    return gender;
  }

  public Name getName() {
    return name;
  }

  public byte[] getPicture() {
    return picture;
  }

  public String getSeed() {
    return seed;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setGender(Gender g) {
    gender = g;
  }

  public void setName(Name n) {
    name = n;
  }

  public void setPicture(byte[] picture) {
    this.picture = picture;
  }

  public void setSeed(String seed) {
    this.seed = seed;
  }
}
