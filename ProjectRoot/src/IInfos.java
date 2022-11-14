import java.util.Map;

public interface IInfos {
  public void hashEthernet();
  public void hashIpv4();
  public void hashTcp();
  public void hashHttp();
  
  

  public void setInfos(String field, String data);

  
  public Map<String,String> getInfos();

  public String getField(String field);
  public String getType();
  
  public String toString();
}
