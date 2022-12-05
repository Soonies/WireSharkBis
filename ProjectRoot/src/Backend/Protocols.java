package Backend;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class Protocols {

  private static Map<Integer, Infos> ethernet = new HashMap<>();
  private static Map<Integer, Infos> ipv4 = new HashMap<>();
  private static Map<Integer, Infos> tcp = new HashMap<>();
  private static Map<Integer, Infos> http = new HashMap<>();

  public Protocols() {
  }

  public static Infos getEthernetInfos(int id) {
    if (!ethernet.containsKey(id)) {
      throw new IllegalAccessError("Pas d'id correspondant " + id);
    }
    return ethernet.get(id);
  }

  public static Infos getHttpInfos(int id) {
    if (!http.containsKey(id)) {
      throw new IllegalAccessError("Pas d'id correspondant " + id);
    }
    return http.get(id);
  }

  public static Infos getTcpInfos(int id) {
    if (!tcp.containsKey(id)) {
      throw new IllegalAccessError("Pas d'id correspondant " + id);
    }
    return tcp.get(id);
  }

  public static Infos getIpv4Infos(int id) {
    if (!ipv4.containsKey(id)) {
      throw new IllegalAccessError("Pas d'id correspondant " + id);
    }
    return ipv4.get(id);
  }

  public static String getEthernetInfosField(int id, String field) {
    return ethernet.get(id).getField(field);
  }

  public static String getHttpInfosField(int id, String field) {
    return http.get(id).getField(field);
  }

  public static String getTcpInfosField(int id, String field) {
    return tcp.get(id).getField(field);
  }

  public static String getIpv4InfosField(int id, String field) {
    return ipv4.get(id).getField(field);
  }

  public static void addInfos(int idTrame, Infos infos, String protocol) {
    switch (protocol) {
      case "ethernet":
        ethernet.put(idTrame, infos);
        break;
      case "ipv4":
        ipv4.put(idTrame, infos);
        break;
      case "tcp":
        tcp.put(idTrame, infos);
        break;
      case "http":
        http.put(idTrame, infos);
        break;
      default:
        throw new InvalidParameterException("Protocol invalide: " + protocol);
    }
  }

}
