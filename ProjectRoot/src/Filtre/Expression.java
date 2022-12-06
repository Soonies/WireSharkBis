package Filtre;


public interface Expression {
  public <T> T accept (Visitor<T> v);
  
}

