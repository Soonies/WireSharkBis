package Filtre ;

import Backend.Protocols;
import Backend.Trame;

public class VisitorEval implements Visitor<Boolean>{
  private Trame t;

  public VisitorEval(Trame t) {
    this.t = t;
  }


  public Boolean visit(Or x) {
    return x.getLeft().accept(this) || x.getRight().accept(this);
    
  }

  public Boolean visit(And x) {
    return x.getLeft().accept(this) || x.getRight().accept(this);
  }

  public Boolean visit(Not x) {
    return !x.getOperand().accept(this);
  }

  public Boolean visit(Protocol p) {
    return t.hasProtocol(p.getContent());
  }

  
  public Boolean visit(Equals x) {
    Field f = x.getField();
    String
      protocol = f.getProtocol(),
      field = f.getField();

   String val = x.getValue();
    switch (protocol) {
      case "http":
        return evalHttp(field, val);
      case "ipv4":
       return evalIpv4( field, val);
      case "tcp":
      return evalTcp( field, val);
      case "ethernet":
      return evalEthernet(field, val);
      default:
        throw new IllegalArgumentException("Protocole non reconnu: " + protocol);
        break;
    }
  }

  private Boolean evalHttp(String field, String val) {
    return Protocols.getEthernetInfosField(t.getId(), field).equals(val);
  }

  private Boolean evalIpv4(String field, String val) {
    
    String formatIpv4_fields = "version lengthHeader ToS lengthTotal id flag offset ttl protocol checksum ipSrc ipDst";
    return Protocols.getEthernetInfosField(t.getId(), field).equals(val);
  }

}