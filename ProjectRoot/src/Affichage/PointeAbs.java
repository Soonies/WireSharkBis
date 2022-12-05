package Affichage;

import Backend.U;

public abstract class PointeAbs implements Pointe {
  private String port;
  private String addresseIp;

  public PointeAbs(String portBin, String addresseIpBin) {// tous les deux en binaire

    String port = U.formatString(5, U.binToDec(portBin)),
        addresseIp = U.ipBinToIpDec(addresseIpBin);
    if (port.length() != 5) {
      throw new IllegalArgumentException("le port ne respecte pas le bon format de longueur 5: " + port);
    }
    if (addresseIp.length() != 15) {
      throw new IllegalArgumentException("l'ip' ne respecte pas le bon format de longueur 15: " + addresseIp);
    }
    this.port = port;
    this.addresseIp = addresseIp;
  }

  public String getPort() {
    return port;
  }

  public String getAddress() {
    return addresseIp;
  }
}
