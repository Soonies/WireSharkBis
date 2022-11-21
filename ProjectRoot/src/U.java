public class U{
  public static String sTbl(String[] t){
    StringBuilder sb = new StringBuilder();
    int accu  =  0 ; 
    for (String s : t) {
      sb.append(accu + ":" + s +" ");
      accu++; 
    }
    return sb+"";
  }

  public static void pTbl(String[] t){
    System.err.println(sTbl(t));
  }
}