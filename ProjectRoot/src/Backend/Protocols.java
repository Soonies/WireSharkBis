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

  public static boolean hasEthernetInfo(int id) {
    return ethernet.containsKey(id);
  }public static boolean hasIpv4Info(int id) {
    return ipv4.containsKey(id);
  }public static boolean hasTcpInfo(int id) {
    return tcp.containsKey(id);
  }public static boolean hasHttpInfo(int id) {
    return http.containsKey(id);
  }
  public static Infos getEthernetInfos(int id) {
    if (!hasEthernetInfo(id)) {
      throw new IllegalAccessError("Pas d'id correspondant " + id);
    }
    return ethernet.get(id);
  }

  public static Infos getHttpInfos(int id) {
    if (!hasHttpInfo(id)) {
      throw new IllegalAccessError("Pas d'id correspondant " + id);
    }
    return http.get(id);
  }

  public static Infos getTcpInfos(int id) {
    if (!hasTcpInfo(id)) {
      throw new IllegalAccessError("Pas d'id correspondant " + id);
    }
    return tcp.get(id);
  }

  public static Infos getIpv4Infos(int id) {
    if (!hasIpv4Info(id)) {
      throw new IllegalAccessError("Pas d'id correspondant " + id);
    }
    return ipv4.get(id);
  }

  public static String getEthernetInfosField(int id, String field) {
    return getEthernetInfos(id).getField(field);
  }

  public static String getHttpInfosField(int id, String field) {
    return getHttpInfos(id).getField(field);
  }

  public static String getTcpInfosField(int id, String field) {
    return getTcpInfos(id).getField(field);
  }

  public static String getIpv4InfosField(int id, String field) {
    return getIpv4Infos(id).getField(field);
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
