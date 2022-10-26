import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Parser {
  private File f;

  public Parser(File f) {
    this.f = f;    
  }
  
  /**
   * 
   * @throws IOException
   */
  public void splitInput() throws IOException {
    StringBuilder trame = new StringBuilder();

    BufferedReader br = new BufferedReader(new FileReader(f));

    for(String line = br.readLine() ; line != null ; line = br.readLine() ) {

      if(!(line == "\n")){
        for(int i=6; i<line.length(); i++){
          if(line.charAt(i) != ' ' || line.charAt(i+1) != ' '){
            trame.append(line.charAt(i));
          }
          else{
            trame.append(" ");
            break;
          }
        }
      }

      else{
        new Trame(trame.toString());
        trame = new StringBuilder();
      }

    }
    
    br.close();
  }



  
  /**
   * @returns Tableau [next_protocol ; longueur du header (format String)]
   */
  public String[] observateurHttp( int indexDebutEntete, int idTrame) {
     Trame t  =  Trame.getTrame(idTrame);
    List<String> tContent  = t.getContent() ; 
    
     next_protocol = 
  }
  
  public String[] observateurTcp(int indexDebutEntete, int idTrame) {
    Trame t = Trame.getTrame(idTrame);
    List<String> tcontent = t.getContent();

    
  }

  public String[] observateurIpv4(int indexDebutEntete, int idTrame) {

  }

  public String[] observateurEthernet( int idTrame) {
    Trame t  =  Trame.getTrame(idTrame);
    String tContent  = t.getContent() ; 
    
    String next = tContent.substring(36, 42 /*36+ */);
    String [] out =  {(next == "08 00")?  "ipv4" : "rejected" , 42+""   };

    return out;
   }

  public void parserHttp() {
    
  }
  
  public void parserTcp() {
    
  }

  public void parserIpv4() {

  }

  public void parserEthernet() {
    
  }

    
}