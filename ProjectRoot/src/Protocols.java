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
    return ethernet.get(id);
  }

  public static Infos getHttpInfos(int id) {
    return http.get(id);
  }

  public static Infos getTcpInfos(int id) {
    return tcp.get(id);
  }

  public static Infos getIpv4Infos(int id) {
    return ipv4.get(id);
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
