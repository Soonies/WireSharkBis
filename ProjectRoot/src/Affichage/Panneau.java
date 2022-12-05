package Affichage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Backend.Trame;

public class Panneau {
  private List<List<String>> panneau = new ArrayList<>();
  private int lins, cols;
  List<String> lsIps;
  List<Fleche> lsFleches;

  private final String L_ARROW = "<--------------", R_ARROW = "-------------->", LINE = "---------------",
      UNDERSCORES = "_______________";

  private Map<String, Integer> indexIps = new HashMap<>();

  /**
   * input: list des trames a afficher
   * initialise lsIps et lsFleches
   * 
   * @param ls
   */
  public Panneau(List<Trame> ls) {
    List<String> lsIps = new ArrayList<>();
    List<Fleche> lsFleches = new ArrayList<>();

    int k = 0; // k = compteur du de l'index du prochain IP a etre ajoute a lsIps
    for (Trame t : ls) {
      Fleche f = new Fleche(t);
      lsFleches.add(f);
      Queue q = f.getQueue();
      Tete tete = f.getTete();

      if (!lsIps.contains(q.getAddress())) {
        lsIps.add(q.getAddress());
        indexIps.put(q.getAddress(), k); // ajout a la HashTable Ip --> indexIp
        k++;
      }
      if (!lsIps.contains(tete.getAddress())) {
        lsIps.add(tete.getAddress());
        indexIps.put(tete.getAddress(), k);// ajout a la HashTable Ip --> indexIp
        k++;
      }
    }

    this.lsFleches = lsFleches;
    this.lsIps = lsIps;
    initPanneau();
    rempliPanneau();
  }

  private void initPanneau() {
    int lins = 2 + 3 * lsFleches.size(), cols = 2 * lsIps.size() + 1 + 1 + 1; // 1 for id, 1 for right blank of the last
                                                                              // ip, 1 for comments
    this.lins = lins;
    this.cols = cols;
    for (int i = 0; i < lins; i++) {
      List<String> tbl = new ArrayList<>();
      for (int j = 0; j < cols; j++) {
        tbl.add("               ");
      }
      panneau.add(tbl);
    }
  }

  private void setij(int i, int j, String x) {
    panneau.get(i).set(j, x);
  }

  private void rempliIps() {
    int n = lsIps.size();
    for (int i = 0; i < n; i++) {
      int k = (2 * i + 1) + 1; // dernier +1 = offset pr les ids de trame
      setij(0, k, lsIps.get(i));
    }
    for (int j = 0; j < cols; j++) {
      setij(1, j, UNDERSCORES);
    }
  }

  /**
   * index = index de f dans lsFleches
   */
  private void writeFleche(Fleche f, int index) {
    Queue q = f.getQueue();
    Tete t = f.getTete();

    String ipSrc = q.getAddress(),
        srcPort = q.getPort(),
        ipDst = t.getAddress(),
        dstPort = t.getPort();
    int i_ipSrc = indexIp(ipSrc),
        i_ipDst = indexIp(ipDst);

    writeId(f.getId(), index);

    if (i_ipSrc < i_ipDst) {
      writeRightPort(srcPort, index, i_ipSrc);
      writeLine(index, i_ipSrc, i_ipDst);
      writeRightArrow(index, i_ipDst);
      writeLeftPort(dstPort, index, i_ipDst);
    } else {
      writeRightPort(dstPort, index, i_ipDst);
      writeLeftArrow(index, i_ipDst);
      writeLine(index, i_ipDst + 1, i_ipSrc + 1);
      writeLeftPort(srcPort, index, i_ipSrc);

    }
    writeCommentaire(f.getCommentaire(), index);
  }

  /**
   * 
   * @param ip
   * @return index de ip dans la list lsIps
   */
  private int indexIp(String ip) {
    if (!indexIps.containsKey(ip)) {
      throw new IllegalAccessError("Pas d'IP correspondant dans la Map Ip--> indexIp: " + ip);
    }
    return indexIps.get(ip);
  }

  /**
   * @return renvoie la coordonnee en x de l'ip d'indice i_ip dans le panneau
   */
  private int getIndexIpDecale(int i_ip) {
    return 2 * i_ip + 1 + 1; // le deuxieme +1 pour l'offset de l'id
  }

  /**
   * @return renvoie la coordonnee en y de la fleche d'indice : `index` avec un
   *         decalage de `decalage`
   */
  private int getIndexFlecheDecale(int index, int decalage) { // avec decalage = 0, 1 ou 2
    return (3 * index + decalage) + 2; // +2 pour l'offset
  }

  private void writeId(String id, int index) {
    int i = getIndexFlecheDecale(index, 1);
    setij(i, 0, id);
  }

  private void writeCommentaire(String commentaire, int index) {
    int i = getIndexFlecheDecale(index, 1);
    setij(i, cols - 1, commentaire);
  }

  private void writeArrowGeneral(int index, int i_ip, char side) {
    int i = getIndexFlecheDecale(index, 1);
    int j = getIndexIpDecale(i_ip);
    String content = null;

    switch (side) {
      case 'l':
        content = L_ARROW;
        break;
      case 'r':
        content = R_ARROW;
        break;

      default:
        throw new IllegalArgumentException("Cote de fleche incorrect");
    }

    setij(i, j, content);
  }

  private void writeLeftArrow(int index, int i_ip) {
    writeArrowGeneral(index, i_ip, 'l');
  }

  private void writeRightArrow(int index, int i_ip) {
    writeArrowGeneral(index, i_ip, 'l');
  }

  private void writePortGeneral(String port, int index, int i_ip, char side) {
    int i = getIndexFlecheDecale(index, 0); // +2 pour l'offset
    int j = getIndexIpDecale(i_ip);
    String content = null;

    switch (side) {
      case 'l':
        j -= 1;
        content = port + "          ";
        break;
      case 'r':
        j += 1;
        content = "          " + port;
        break;

      default:
        throw new IllegalArgumentException("Cote du port a remplir incorrect");
    }

    setij(i, j, content);
  }

  private void writeLeftPort(String port, int index, int i_ip) {
    writePortGeneral(port, index, i_ip, 'l');
  }

  private void writeRightPort(String port, int index, int i_ip) {
    writePortGeneral(port, index, i_ip, 'r');
  }

  /**
   * rempli avec des ---------- de i_ipSrc a i_ipDst-1
   * 
   * @param index
   * @param i_ipSrc
   * @param i_ipDst
   */
  private void writeLine(int index, int i_ipSrc, int i_ipDst) {
    int i = getIndexFlecheDecale(index, 1);
    for (int j = i_ipSrc; j < i_ipDst; j++) {
      setij(i, j, LINE);
    }
  }

  private void rempliPanneau() {
    rempliIps();
    int n = lsFleches.size();
    for (int i = 0; i < n; i++) {
      Fleche f = lsFleches.get(i);
      writeFleche(f, i);
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (List<String> ls : this.panneau) {
      for (String s : ls) {
        sb.append(s);
      }
      sb.append("\n");
    }
    return sb + "";
  }
}
