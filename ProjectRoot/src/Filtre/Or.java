package Filtre;

public class Or extends BinOp implements Expression{
  public Or(Expression l, Expression r) {
    super(l, r);
  }

@Override
public <T> T accept(Visitor<T> v) {
    return v.visit(this);
}


}