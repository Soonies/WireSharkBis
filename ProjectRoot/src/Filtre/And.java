package Filtre;

public class And extends BinOp implements Expression{
  public And(Expression l, Expression r) {
    super(l, r);
  }

@Override
public <T> T accept(Visitor<T> v) {
    return v.visit(this);
}


}