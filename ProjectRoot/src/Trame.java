import java.util.ArrayList;
import java.util.List;

public class Trame {
  private static List<Trame> lsTrames = new ArrayList<>();

  private static int counter = -1;
  private final int id;
  private final String content;
  private String lastProtocol;
  private boolean rejected = false;

  public Trame(String trame) {
    counter++;
    id = counter;
    content = trame;
    lsTrames.add(this);
  }

  public int getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public String getLastProtocol() {
    return this.lastProtocol;
  }

  public boolean isRejected() {
    return this.rejected;
  }
}
