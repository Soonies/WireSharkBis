package Filtre;


public class Field {
  private final String name;
  private String protocol;
  private String field ; 

  public Field(String field) {

    if (!field.contains(".")) {
      throw new IllegalArgumentException("Mauvais format de champ, se referer a la documentation: "+field);
    }
    this.name = field;
    String[] split = field.split("\\.");  // "\\." expression regulier pour reconnaitre le charactere '.' ( . match a nimporte quel char)
    this.protocol = split[0];
    this.field = split[1];
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