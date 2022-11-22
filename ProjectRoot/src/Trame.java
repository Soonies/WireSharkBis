import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Trame implements Iterable<Trame>{
  private static List<Trame> lsTrames = new ArrayList<>();

  private static int counter = 0;
  private final int id;
  private final String[] content;
  private String lastProtocol;
  private boolean rejected = false;

  public Trame(String[] trame) {
    id = counter;
    content = trame;
    lsTrames.add(this);
    counter++;

    Parser.traitementTrame(id);
  }

  public int getId() {
    return id;
  }

  public int getSize() {
    return this.content.length;
  }

  public String[] getContent() {
    return content;
  }

  public String getLastProtocol() {
    return this.lastProtocol;
  }

  public void setLastProtocol(String prot) {
    this.lastProtocol = prot;
  }

  public void setRejected() {
    this.rejected = true;
  }
  
  public boolean isRejected() {
    return this.rejected;
  }

  public static Trame getTrame(int idTrame) {
    return lsTrames.get(idTrame);
  }

  @Override
  public String toString() {
    String sb = U.sTbl(content);
    return "{" +
      " id='" + getId() + "'" +
      ", content='" + sb + "' \n " +
      ", lastProtocol='" + getLastProtocol() + "'" +
      ", rejected='" + isRejected() + "'" +
      "}";
  }


  public class Iterateur implements Iterator<Trame>{
    public Iterator<Trame> ite  =  lsTrames.iterator();
    
    @Override
    public boolean hasNext() {
      return ite.hasNext();
    }

    @Override
    public Trame next() {
      return ite.next();
    }

  }


  @Override
  public Iterator<Trame> iterator() {
    
    return new Iterateur() ;
  }
}
