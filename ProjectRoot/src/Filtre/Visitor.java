package Filtre ; 

public interface Visitor<T>{
  public T visit(Or x);
  public T visit(And x);

public T visit(Equals b);
public T visit(Different b);

public T visit(Protocol p);

}