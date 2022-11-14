import java.util.HashMap;
import java.util.Map;

public class Protocols {

  private static Map<Integer, IInfos> ethernet = new HashMap<>();
  private static Map<Integer, IInfos> ipv4 = new HashMap<>();
  private static Map<Integer, IInfos> tcp = new HashMap<>();
  private static Map<Integer, IInfos> http = new HashMap<>();

  public Protocols() {  }

  public IInfos getEthernetInfos (int id) {
    return ethernet.get(id);
  }

  public IInfos getHttpInfos (int id) {
    return http.get(id);
  }

  public IInfos getTcpInfos (int id) {
    return tcp.get(id);
  }

  public IInfos getIpv4Infos (int id) {
    return ipv4.get(id);
  }

  public void addEthernetInfos (int id, IInfos infos) {
    ethernet.put(id, infos);
  }

  public void addHttpInfos (int id, IInfos infos) {
    http.put(id, infos);
  }

  public void addTcpInfos (int id, IInfos infos) {
    tcp.put(id, infos);
  }

  public void addIpv4Infos (int id, IInfos infos) {
    ipv4.put(id, infos);
  }

}
