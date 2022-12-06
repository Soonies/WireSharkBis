package Filtre;

import java.util.Arrays;
import java.util.List;

public class Protocol implements Expression {
  private String protocol;

  public Protocol(String protocol) {
    List<String> lsProto = Arrays.asList(new String[] { "http", "ipv4", "tcp", "ethernet" });
    if( !lsProto.contains(protocol)){
      throw new IllegalArgumentException("Protocole non reconnu: " + protocol);
    }
    this.protocol = protocol;
  }

  public String getContent() {
    return this.protocol;
  }

@Override
public <T> T accept(Visitor<T> v) {
    return v.visit(this);
}


}