package Backend;
import java.security.InvalidParameterException;
import java.util.Stack;

/**
 * ready to push
 * TODO tests
 */
public class Filtre {

  private Trame t = null;
  private String expr = null;

  private Symbol[] reversePolish = null;

  private Stack<Symbol> stck = new Stack<>();
  private Boolean b = null;

  public Filtre(String s, Trame t) {
    this.expr = s;
    this.t = t;
    parseInput();
    this.b = evalReversePolish();
  }

  public Boolean getResult() {
    return this.b;
  }

  private void parseInput() {
    String[] tbl = expr.split(" ");
    Symbol[] out = new Symbol[tbl.length];

    for (int i = 0; i < tbl.length; i++) {
      String s = tbl[i];
      if (s.equals("||") || s.equals("&&")) {
        out[i] = new Operation(s);
      } else {
        out[i] = new Operand(evalKeyword(s));
      }
    }
    this.reversePolish = out;

  }

  private int indexChar(String s, char c) {
    int i = 0;
    while (s.charAt(i) != c) {
      i++;
    }
    return i;
  }

  private String getFieldTrame(Trame t, String protocol, String field) {
    int idTrame = t.getId();
    switch (protocol) {
      case "ethernet":
        return Protocols.getEthernetInfos(idTrame).getField(field);
        break;
      case "ipv4":
        return Protocols.getIpv4Infos(idTrame).getField(field);
        break;
      case "tcp":
        return Protocols.getTcpInfos(idTrame).getField(field);
        break;
      case "http":
        return Protocols.getHttpInfos(idTrame).getField(field);
        break;
      default:
        throw new InvalidParameterException("Erreur de frappe protocol passe en param");

    }
  }

  private Boolean evalKeyword(String s) {

    int i_p = indexChar(s, '.'),
        i_eg = indexChar(s, '=');

    String protocol = s.substring(0, i_p),
        field = s.substring(i_p + 1, i_eg),
        value = s.substring(i_eg, s.length());

    return U.binToDec(getFieldTrame(t, protocol, field)) == value;
  }

  private boolean evalReversePolish() {
    for (Symbol x : reversePolish) {
      if (x instanceof Operation) {
        Operand o1 = (Operand) stck.pop(),
            o2 = (Operand) stck.pop();
        stck.push(eval(x, o1, o2));

      } else {
        stck.push(x);
      }

    }
    Operand result = (Operand) stck.pop();
    return result.getValue();
  }

  private Symbol eval(Symbol op, Operand o1, Operand o2) {
    if (!(op instanceof Operation)) {
      throw new InvalidParameterException("pas une op bouffon");
    }

    Boolean b = true;

    switch (op.getContent()) {
      case "||":
        b = o1.getValue() || o2.getValue();
        break;
      case "&&":
        b = o1.getValue() && o2.getValue();
        break;
      default:
        throw new InvalidParameterException("What the gell");

    }
    return new Operand(b);
  }

  private class Symbol {
    private String content = " Prblm d'heritage";

    public String getContent() {
      return this.content;
    }
  }

  private class Operation extends Symbol {
    private String content;

    public Operation(String s) {
      this.content = s;
    }

    @Override
    public String getContent() {
      return this.content;
    }

  }

  private class Operand extends Symbol {
    private Boolean content = null;

    public Operand(Boolean b) {
      this.content = b;
    }

    public boolean getValue() {
      return this.content;
    }
  }

}