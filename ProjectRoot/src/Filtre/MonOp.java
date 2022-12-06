package Filtre;

public abstract class MonOp {
  private Expression o ; 

  public MonOp(Expression o){
    this.o =   o ;
  }

  public Expression getOperand(){
    return o ; 
  }

}