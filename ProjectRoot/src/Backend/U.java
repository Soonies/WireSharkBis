package Backend;

import java.util.List;

public class U {
  public static String sTbl(String[] t) {
    StringBuilder sb = new StringBuilder();
    int accu = 0;
    for (String s : t) {
      sb.append(accu + ":" + s + " ");
      accu++;
    }
    return sb + "";
  }

  public static void pTbl(String[] t) {
    System.out.println(sTbl(t));
  }

  public static<T> String  sLst(List<T> t) {
    StringBuilder sb = new StringBuilder();
    int accu = 0;
    for (T s : t) {
      sb.append(accu + ":" + s + " ");
      accu++;
    }
    return sb + "";
  }

  public static void pLst(List<String> t) {
    System.out.println(sLst(t));
  }

  public static String binToDec(String s) {
    return Long.parseLong(s, 2) + "";
  }
  

  /**
   * Pour une string s = abcdef et un entier n
   *
   * @returns si len(s) < n, retourne s' = (n-len(s))*' ' + s , cad s completee
   *          avec n-len(s) espaces pour avoir une taille n au final
   */
  public static String formatString(int n, String s) {
    if (s.length() > n) {
      throw new IllegalArgumentException("Input string est plus longue que n");
    }
    String padding = "";
    for (int i = 0; i < n - s.length(); i++) {
      padding += " ";
    }

    return padding + s;

  }

  /**
   * 
   * @param ipBin
   * @return ip binaire convertie en decimal, avec longueur = 15
   */
  public static String ipBinToIpDec(String ipBin) {
    System.out.println("Mon ip bin:" + ipBin); // 1110 1101 0110 1011
    String ipDec = "";
    for (int i = 0; i < 4; i++) {
      String petitMorceau = formatString(3, binToDec(ipBin.substring(8 * i, 8 * (i + 1))));
      ipDec += petitMorceau + (i < 3 ? "." : "");
    }

    return ipDec;
  }

}