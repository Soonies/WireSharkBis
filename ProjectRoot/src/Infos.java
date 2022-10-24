import java.util.Map;

public interface Infos {
  public void hashEthernet();
  public void hashIpv4();
  public void hashTcp();
  public void hashHttp();
  
  

  public void setInfos(String field, String data);

  public void parseHeaderIpv4(String header);
  public void parseHeaderEthernet(String header);
  public void parseHeaderTcp(String header);
  public void parseHeaderHttp(String header);
  
  public Map<String,String> getInfos();

  public String getField(String field);
  public String getType();
  
}
