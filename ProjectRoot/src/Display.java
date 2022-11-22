import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.security.InvalidParameterException;

public class Display {
    
    public static void main(String[] args) {
      File f = new File("../data/1.txt");
      new Parser(f);
      afficheurFinal();
    }


    public static void afficheurFinal() {

      Trame t = Trame.getTrame(0);
      Iterator<Trame> ite = t.iterator();

      while (ite.hasNext()) {
        Trame next = ite.next();
        if (!next.isRejected() && passedFilterTrame(t)) {
          switch (next.getLastProtocol()) {
            case "http":
              afficheurHTTP(next);
              break;
            case "tcp":
              afficheurTCP(next);
              break;
          }
        }
      }
    }

//TODO
private static boolean passedFilterTrame(Trame t) {
  return true;
}

    private static String binToDec(String s){
      return Integer.parseInt(s, 2)+"";
    }
    

    private static String ipBinToIpDec(String ipBin) {
      String ipDec = "";
      for (int i = 0; i < 4; i++) {
        ipDec += binToDec(ipBin.substring(8 * i, 8 * (i + 1))) + (i < 3 ? "." : "");
      }
    
    return ipDec;
    
    }

    private static List<String> flagsUpTcp(Trame t) {
      Infos infoTcp = Protocols.getTcpInfos(t.getId());
      
      if (t.getLastProtocol() != "tcp") {
        throw  new InvalidParameterException("c pas du tcp bouffon");
      }
      String[] lsFlags = "NS CWR ECE URG ACK PSH RST SYN FIN".split(" ");
      List<String> ls = new ArrayList<String>();
      for (String flag : lsFlags) {
        if (infoTcp.getField(flag).equals("1")) {
          ls.add(flag);
        }
      }
      return ls;
      
    }


    public static void afficheurTCP(Trame trame){
        Infos infoTcp = Protocols.getTcpInfos(trame.getId());
        Infos infoIpv4 = Protocols.getIpv4Infos(trame.getId());
        // Infos infoEthernet = Protocols.getIpv4Infos(trame.getId());

        String 
        ipSrc = ipBinToIpDec(infoIpv4.getField("ipSrc")),
        ipDst = ipBinToIpDec(infoIpv4.getField("ipDst")),
        portSrc = binToDec(infoTcp.getField("portSrc")),
        portDst = binToDec(infoTcp.getField("portDst")),
        sequenceNumber = binToDec(infoTcp.getField("sequenceNumber")),
        ackNumber = binToDec(infoTcp.getField("ackNumber"));


        System.out.println(ipSrc+"                                       " + ipDst);

        //sequenceNumber ackNumber offset reserved NS CWR ECE URG ACK PSH RST SYN FIN
        System.out.print(portSrc+"->"+portDst+" [");
        
        List<String> flags = flagsUpTcp(trame);
        StringBuilder sb = new StringBuilder();
        for(String s : flags){
            sb.append(s+",");
        }
        sb.deleteCharAt(sb.length()-1);
        System.out.print(sb+"] ");

        if(flags.contains("SYN"))
            System.out.print("Seq=0 ");
        else
            System.out.print("Seq="+sequenceNumber+" ");

        if(flags.contains("ACK"))
            System.out.print("Ack="+ackNumber+" ");
        
        System.out.println("Len=0 Win=... TSval=... TSecr=...");
        
        
        
        System.out.println(portSrc+" --------------------------------------------------> " + portDst);
    }
    
    public static void afficheurHTTP(Trame trame){
         
      Infos infoHttp = Protocols.getHttpInfos(trame.getId());
      Infos infoTcp = Protocols.getTcpInfos(trame.getId());
      Infos infoIpv4 = Protocols.getIpv4Infos(trame.getId());
      Infos infoEthernet = Protocols.getEthernetInfos(trame.getId());
    }
}
