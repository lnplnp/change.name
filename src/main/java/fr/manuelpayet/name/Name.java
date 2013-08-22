package fr.manuelpayet.name;

public abstract class Name {

  protected Long id;

  protected String label;

  public Name() {
  }

  public Name(String label) {
    setLabel(label);
  }

  @SuppressWarnings("unused")
  private void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return this.id;
  }

  public String getLabel() {
    return this.label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
