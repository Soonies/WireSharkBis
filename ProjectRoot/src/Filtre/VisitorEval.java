package Filtre ;

import Backend.Protocols;
import Backend.Trame;
import Backend.U;

public class VisitorEval implements Visitor<Boolean>{
  private Trame t;

  public VisitorEval(Trame t) {
    this.t = t;
  }


  public Boolean visit(Or x) {
    return x.getLeft().accept(this) || x.getRight().accept(this);
    
  }

  public Boolean visit(And x) {
    return x.getLeft().accept(this) && x.getRight().accept(this);
  }

  public Boolean visit(Different x) {
    return !x.getEqual().accept(this);
  }

  public Boolean visit(Protocol p) {
    return t.hasProtocol(p.getContent());
  }
  
  public Boolean visit(Equals x) {
    Field f = x.getField();
    String protocol = f.getProtocol(),
        field = f.getField();

    String val = x.getValue();
    switch (protocol) {
      case "http":
        return evalHttp(field, val);
      case "ipv4":
        return evalIpv4(field, val);
      case "tcp":
        return evalTcp(field, val);
      case "ethernet":
        return evalEthernet(field, val);
      default:
        throw new IllegalArgumentException("Protocole non reconnu: " + protocol);
    }
  }

  private Boolean evalHttp(String field, String val) {
    return Protocols.getEthernetInfosField(t.getId(), field).equals(val);
  }

  private Boolean evalTcp(String field, String val) {
     String fieldTrame =  Protocols.getTcpInfosField(t.getId(), field);

     return U.binToDec(fieldTrame).equals(val);
  
  }

  private String binToMac(String bin) {
    String res = "";
    String octet;
    int decimal;
    String hex;

    for (int i = 0; i < bin.length(); i += 8) {
      octet = bin.substring(i, i + 8);
      decimal = Integer.parseInt(octet, 2);
      hex = Integer.toString(decimal, 16);

      if (hex.length() == 1) {
        hex = "0" + hex;
      }
      if (hex.length() == 0) {
        hex = "00";
      }

      res += hex;

      if (i + 8 != bin.length())
        res += ":";
    }
    return res;
  }


  private Boolean evalEthernet(String field, String val1) {
    String val = val1.toLowerCase();
    String fieldTrame =  Protocols.getEthernetInfosField(t.getId(), field);
    String aComparer;
    
    switch (field){
      case "next":
        aComparer = U.binToDec(fieldTrame);
        break;
      default:
        aComparer = binToMac(fieldTrame);
        break;
    }

    return aComparer.equals(val);
  }


/**
   * 
   * @param ipBin
   * @return ip binaire convertie en decimal SANS FORMATAGE donc different de U.binToDec
   */
  public static String ipBinToIpDec(String ipBin) {
    String ipDec = "";
    for (int i = 0; i < 4; i++) {
      String petitMorceau =  U.binToDec(ipBin.substring(8 * i, 8 * (i + 1)));
      ipDec += petitMorceau + (i < 3 ? "." : "");
    }

    return ipDec;
  }
  private Boolean evalIpv4(String field, String val) {
    
    String fieldTrame =  Protocols.getIpv4InfosField(t.getId(), field);
  
    String aComparer ; 
    switch (field) {

      case "ipSrc":
        aComparer = ipBinToIpDec(fieldTrame);
        break;
      case "ipDst":
        aComparer = ipBinToIpDec(fieldTrame);
        break;

      default:
        aComparer = U.binToDec(fieldTrame);
        break;
    }
    return aComparer.equals(val);
  }

}