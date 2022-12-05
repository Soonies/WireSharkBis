package Affichage;

import java.util.ArrayList;
import java.util.List;

import Backend.Protocols;
import Backend.Trame;
import Backend.U;

public class Fleche {
  private Queue q;
  private Tete t;
  private String commentaire;
  private String type;
  private int id;

  public Fleche(Trame t1) {
    if (!(t1.isRejected() || t1.getLastProtocol() == "http" || t1.getLastProtocol() == "tcp")) {
      throw new IllegalArgumentException(
          "Trame corrompue : ne verifie pas  (t.isRejected() || t.getLastProtocol()== 'http' || t.getLastProtocol() == 'tcp')");
    }

    int idTrame = t1.getId();
    id = idTrame;

    String srcPort = tcpIF(idTrame, "srcPort"),
        dstPort = tcpIF(idTrame, "dstPort"),
        ipSrc = ipIF(idTrame, "ipSrc"),
        ipDst = ipIF(idTrame, "ipDst");

    q = new Queue(srcPort, ipSrc);
    this.t = new Tete(dstPort, ipDst);

    type = t1.getLastProtocol();
    commentaire = makeComment(t1);

  }

  private String makeComment(Trame t2) {
    String comment = "";
    switch (t2.getLastProtocol()) {
      case "http":
        comment = makeCommentHTTP(t2);
        break;
      case "tcp":
        comment = makeCommentTCP(t2);
        break;
    }
    return comment;
  }

  private static String ipIF(int id, String field) {
    return ipIF(id, field);
  }

  private static String tcpIF(int id, String field) {
    return tcpIF(id, field);
  }

  private static String ipBTD(String s) {
    return U.ipBinToIpDec(s);
  }

  private static String BTD(String s) {
    return U.binToDec(s);
  }

  /**
   * 
   * @param t trame
   * @return la liste des flgs up pour la trame t
   */
  private static List<String> flagsUpTcp(Trame t) {

    if (t.getLastProtocol() != "tcp") {
      throw new IllegalArgumentException("c pas du tcp bouffon");
    }

    String[] lsFlags = "NS CWR ECE URG ACK PSH RST SYN FIN".split(" ");
    List<String> ls = new ArrayList<String>();

    for (String flag : lsFlags) {
      if (tcpIF(t.getId(), "flag").equals("1")) {
        ls.add(flag);
      }
    }
    return ls;

  }

  private String makeCommentTCP(Trame trame) {
    id = trame.getId();

    String portSrc = ipBTD(tcpIF(id, "portSrc")),
        portDst = ipBTD(tcpIF(id, "portDst")),
        sequenceNumber = ipBTD(tcpIF(id, "sequenceNumber")),
        ackNumber = ipBTD(tcpIF(id, "ackNumber")),
        window = BTD(tcpIF(id, "window"));

    List<String> flags = flagsUpTcp(trame);
    StringBuilder sb = new StringBuilder();

    sb.append(portSrc + "->" + portDst + " [");

    for (String s : flags) {
      sb.append(s + ",");
    }
    sb.deleteCharAt(sb.length() - 1);
    sb.append("] ");

    sb.append("Seq=" + sequenceNumber + " ");

    sb.append("Ack=" + ackNumber + " ");
    sb.append("Win=" + window + " ");

    return sb + "";
  }

  private String makeCommentHTTP(Trame t2) {
    if (!(t2.getLastProtocol() == "http")) {
      throw new IllegalArgumentException(
          "A essayer de faire un comment HTTP sur une trame non HTTP");
    }
    return Protocols.getHttpInfosField(id, "messageEnClair");
  }

  public Queue getQueue() {
    return q;
  }

  public Tete getTete() {
    return t;
  }

  public String getCommentaire() {
    return this.commentaire;
  }

  public String getId() {
    return U.formatString(15, this.id + "");
  }

  public String getType() {
    return this.type;
  }
}
