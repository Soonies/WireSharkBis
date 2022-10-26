import java.util.HashMap;
import java.util.Map;

public class Protocols {

  private static Map<Integer, Infos> ethernet = new HashMap<>();
  private static Map<Integer, Infos> ipv4 = new HashMap<>();
  private static Map<Integer, Infos> tcp = new HashMap<>();
  private static Map<Integer, Infos> http = new HashMap<>();

  public Protocols() {  }

  public Infos getEthernetInfos (int id) {
    return ethernet.get(id);
  }

  public Infos getHttpInfos (int id) {
    return http.get(id);
  }

  public Infos getTcpInfos (int id) {
    return tcp.get(id);
  }

  public Infos getIpv4Infos (int id) {
    return ipv4.get(id);
  }

  public void addEthernetInfos (int id, Infos infos) {
    ethernet.put(id, infos);
  }

  public void addHttpInfos (int id, Infos infos) {
    http.put(id, infos);
  }

  public void addTcpInfos (int id, Infos infos) {
    tcp.put(id, infos);
  }

  public void addIpv4Infos (int id, Infos infos) {
    ipv4.put(id, infos);
  }

}
