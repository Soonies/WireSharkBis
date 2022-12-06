package Filtre;

public class Not extends MonOp implements Expression{
  public Not(Expression e) {
    super(e);
  }

@Override
public <T> T accept(Visitor<T> v) {
    return v.visit(this);
}


}