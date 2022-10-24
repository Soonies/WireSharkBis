import java.util.Hashtable;
import java.util.Map;

public class ImplInfos implements Infos {
  private final String type;
  private Map<String, String> hashInfos = new Hashtable<>();

  public ImplInfos(String type){
    this.type = type;
  }

  @Override
  public void hashEthernet() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void hashIpv4() {
    // TODO Auto-generated method stub

  }

  @Override
  public void hashTcp() {
    // TODO Auto-generated method stub

  }

  @Override
  public void hashHttp() {
    // TODO Auto-generated method stub

  }

  @Override
  public void setInfos(String field, String data) {
    this.hashInfos.replace(field, data);
  }

  @Override
  public void parseHeaderIpv4(String header) {
    // TODO Auto-generated method stub

  }

  @Override
  public void parseHeaderEthernet(String header) {
    // TODO Auto-generated method stub

  }

  @Override
  public void parseHeaderTcp(String header) {
    // TODO Auto-generated method stub

  }

  @Override
  public void parseHeaderHttp(String header) {
    // TODO Auto-generated method stub

  }

  @Override
  public Map<String, String> getInfos() {
    return this.hashInfos;
  }
  
  @Override
  public String getField(String field) {
    return this.hashInfos.get(field);
  }

  @Override
  public String getType() {
    return this.type;
  }

}
