package Backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Parser {
  private File f;

  public Parser(File f) {
    this.f = f;
    init();
  }

  private void init() {
    try {
      splitInput();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String hexToBin(String hex) {
    hex = hex.replaceAll("0", "0000");
    hex = hex.replaceAll("1", "0001");
    hex = hex.replaceAll("2", "0010");
    hex = hex.replaceAll("3", "0011");
    hex = hex.replaceAll("4", "0100");
    hex = hex.replaceAll("5", "0101");
    hex = hex.replaceAll("6", "0110");
    hex = hex.replaceAll("7", "0111");
    hex = hex.replaceAll("8", "1000");
    hex = hex.replaceAll("9", "1001");
    hex = hex.replaceAll("a", "1010");
    hex = hex.replaceAll("b", "1011");
    hex = hex.replaceAll("c", "1100");
    hex = hex.replaceAll("d", "1101");
    hex = hex.replaceAll("e", "1110");
    hex = hex.replaceAll("f", "1111");
    hex = hex.replaceAll("A", "1010");
    hex = hex.replaceAll("B", "1011");
    hex = hex.replaceAll("C", "1100");
    hex = hex.replaceAll("D", "1101");
    hex = hex.replaceAll("E", "1110");
    hex = hex.replaceAll("F", "1111");
    return hex;
  }

  private static String[] formateTrame(String t) {
    String lignes[] = t.split("\n");
    String s = "";
    for (String l : lignes) {

      s += l.substring(6, 54);

    }
    String[] tbl = hexToBin(s).split(" ");
    return tbl;
  }

  /**
   * instancie toutes les trames du fichier
   * 
   * @throws IOException
   */
  private void splitInput() throws IOException {
    StringBuilder sb = new StringBuilder();

    BufferedReader br = new BufferedReader(new FileReader(this.f));

    for (String line = br.readLine(); line != null; line = br.readLine()) {
      sb.append(line + "\n");
    }
    String text = "" + sb;
    String[] lsTrames = text.split("\n\n");

    for (String t : lsTrames) {
      String[] trame = formateTrame(t);
      new Trame(trame);
    }

    br.close();
  }


// tcp " "
  private static boolean isHttp(int indexDebutPayload, int length, int idTrame) {
    Trame t = Trame.getTrame(idTrame);
    String[] tcontent = t.getContent();
    String keywords  =  "GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH HTTP";
    List<String> lsKeywords = Arrays.asList(keywords.split(" "));

    String s = "";
    for (int i = indexDebutPayload; i < indexDebutPayload + length; i++) {
      Character c  = U.binToAscii(tcontent[i]);
      if (c.equals(' ')) {
        return lsKeywords.contains(s);
      } 
      if(s.equals("HTTP")) {
        return true;
      }
      s+= c;
    }
    return false;
  }
  /**
   * @returns Le Tableau {next_protocol ; longueur du header en octet(format
   *          String)]
   */
  private static String[] observateurTcp(int indexDebutEntete, int idTrame){
    Trame t = Trame.getTrame(idTrame);
    String[] tcontent = t.getContent();

    // condition  =  
    String dataOffset = tcontent[indexDebutEntete + 12].substring(0, 4);
    int length = Integer.parseInt(dataOffset, 2) * 4; // car exprime en mots de 32 bits = 4 octets

    
    String nextProt = "rejected";
  
  
    int longueurDataApresTcp = tcontent.length - (indexDebutEntete + length);

    if (longueurDataApresTcp > 0){ // le port est 80, verifions si un HTTP est encapsule
      nextProt = isHttp(indexDebutEntete+length, longueurDataApresTcp, idTrame) ? "http" : null;
    }
    else{
      nextProt = null;
    }


    return new String[] { nextProt, length + "" };

  }

  /**
   * @returns le Tableau {next_protocol ; longueur du header en octet(format
   *          String)]
   */
  private static String[] observateurIpv4(int indexDebutEntete, int idTrame) {
    Trame t = Trame.getTrame(idTrame);
    String[] tcontent = t.getContent();
    String nextProt = tcontent[indexDebutEntete + 9].equals("00000110") ? "tcp" : "rejected";
    String lengthHeader = tcontent[indexDebutEntete].substring(4, 8);
    String totalLength = tcontent[indexDebutEntete + 2]+tcontent[indexDebutEntete+3];
    return new String[] { nextProt, Integer.parseInt(lengthHeader, 2) * 4 + "", totalLength};
  }

  /**
   * @returns Le Tableau {next_protocol ; longueur du header en octet(format
   *          String)]
   */
  private static String[] observateurEthernet(int idTrame) {
    Trame t = Trame.getTrame(idTrame);
    String[] tContent = t.getContent();

    String type = tContent[12] + tContent[13];
    String nextProt = type.equals("0000100000000000") ? "ipv4" : "rejected";
    String[] out = { nextProt, 14 + "" };

    return out;
  }

  /**
   * Slicing de la trame entiere pour isoler une entete
   * 
   * @param idTrame
   * @param indexDebutEntete
   * @param lengthHeader
   * @return renvoie le sous tableau trameEntiere[indexDebutEntete,
   *         indexDebutEntete+ lengthHeader -1]
   */
  private static String[] copyHeader(int idTrame, int indexDebutEntete, int lengthHeader) {
    Trame t = Trame.getTrame(idTrame);
    String[] content = t.getContent();

    int indexFinEntete = indexDebutEntete + lengthHeader - 1;
    String[] copyHeader = new String[lengthHeader];

    for (int i = indexDebutEntete; i <= indexFinEntete; i++) {
      copyHeader[i - indexDebutEntete] = content[i];
    }
    return copyHeader;
  }

  /**
   * Effectue les actions suivante:
   * - cree les Infos infos correspondant a l'entete en cours de lecture
   * - les ajoute a Protocols
   * 
   * @param idTrame
   * @param indexDebutEntete
   * @param lengthHeader
   * @param protocol
   */
  private static void parserGeneral(int idTrame, int indexDebutEntete, int lengthHeader, String protocol) {
    Trame t = Trame.getTrame(idTrame);
    t.setLastProtocol(protocol);
    String[] header = copyHeader(idTrame, indexDebutEntete, lengthHeader);

    Infos infos = new Infos(protocol, header);
    Protocols.addInfos(idTrame, infos, protocol);
  }

  /**
   * Traite la trame d'id idTrame en initialisant toutes ses `Infos`
   * 
   * @param idTrame
   */
  public static void traitementTrame(int idTrame) {
    Trame t = Trame.getTrame(idTrame);

    String[] observateur = observateurEthernet(idTrame);
    String nextProt = observateur[0];
    int i = 0;
    int n = Integer.parseInt(observateur[1]);

    if (nextProt.equals("rejected")) {
      t.setRejected();
    } else { // --> on a un protocol IPV4
      parserGeneral(idTrame, i, n, "ethernet"); // on parse l'entete ethernet
      i += n;
      observateur = observateurIpv4(i, idTrame); // on regarde la suite
      nextProt = observateur[0];

      n = Integer.parseInt(observateur[1]);

      if (nextProt.equals("rejected")) {
        t.setRejected();
      } else { // --> on a un protocol tcp
        parserGeneral(idTrame, i, n, "ipv4"); // on parse l'entete ipv4
        i += n;
        observateur = observateurTcp(i, idTrame); //observateur = observateurTcp(i, idTrame, totalLength);
        nextProt = observateur[0];

        n = Integer.parseInt(observateur[1]);

        if (nextProt == null) { // --> la trame s'arrete a tcp, on effectue seulement le parsing de l'entete tcp
          parserGeneral(idTrame, i, n, "tcp");
        } else if (nextProt.equals("rejected")) {
          t.setRejected();
        } else { // --> on a un protocole http

          parserGeneral(idTrame, i, n, "tcp"); // on parse l'entete tcp
          i += n;

          parserGeneral(idTrame, i, t.getSize() - i, "http"); // on parse l'entete http

        }
      }
    }
  }
}