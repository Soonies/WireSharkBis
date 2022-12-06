package Filtre;

public abstract class BinOp {
  private Expression l, r;

  public BinOp(Expression l, Expression r) {
    this.l = l;
    this.r = r;
    
  }
  public Expression getLeft() {
    return this.l;
  }
  
  public Expression getRight() {
    return this.r;
  }



}