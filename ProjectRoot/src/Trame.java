import java.util.ArrayList;
import java.util.List;

public class Trame {
  private static List<Trame> lsTrames = new ArrayList<>();

  private static int counter = 0;
  private final int id;
  private final List<String> content;
  private String lastProtocol;
  private boolean rejected = false;

  public Trame(List<String> trame) {
    id = counter;
    content = trame;
    lsTrames.add(this);
    counter++;
  }

  public int getId() {
    return id;
  }

  public List<String> getContent() {
    return content;
  }

  public String getLastProtocol() {
    return this.lastProtocol;
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
