package Filtre;

public class Field {
  private final String name;
  private String protocol;
  private String field ; 




  public Field(String field) {
    //TODO verif si c un field legal( ici?)
    this.name = field;
    String[] split = field.split(".");
    protocol = split[0];
    field = split[1];
  }

  public String getName() {
    return this.name;
  }

  public String getProtocol(){
    return this.protocol;
  }
  public String getField() {
    return this.field;
  }


  
}