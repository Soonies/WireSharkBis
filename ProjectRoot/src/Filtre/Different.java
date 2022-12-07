package Filtre;

public class Different implements Expression {
  private Equals e;

 public Different(Field f, String val) {
   this.e = new Equals(f, val);
  }

  public Field getField() {
    return e.getField();
  }

  public String getValue() {
    return e.getValue();
  }

  public Equals getEqual(){
    return e;
  }

  @Override
  public <T> T accept(Visitor<T> v) {
      return v.visit(this);
  }
  
}