import java.util.ArrayList;
import java.util.List;

public class Trame {
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
    return "{" +
      " id='" + getId() + "'" +
      ", content='" + getContent() + "'" +
      ", lastProtocol='" + getLastProtocol() + "'" +
      ", rejected='" + isRejected() + "'" +
      "}";
  }
}
