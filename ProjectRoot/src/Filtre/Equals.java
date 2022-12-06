package Filtre;

public class Equals implements Expression {
  private Field field;
  private String val;
 public Equals(Field f, String val) {
   this.field = f;
   this.val = val;
  }

  public Field getField() {
    return this.field;
  }

  public String getValue() {
    return this.val;
}

@Override
public <T> T accept(Visitor<T> v) {
    return v.visit(this);
}
  
}