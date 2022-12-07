package Filtre;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Backend.Trame;

public class Filtre {
  private Boolean isVide = false;
  private Expression e;
  private VisitorEval v;

  public Filtre(String f, Trame t) {
    if(!f.equals("")){
      List<String> reversePolish = parsePostfix(InfixToPostfix.f(f+" "));
      Expression e = makeExpression(reversePolish);
      this.e = e;
      this.v = new VisitorEval(t);
    } else {
        this.isVide = true;
    }
  }

  public Boolean getValue() {
    return isVide || e.accept(v);
  }
  private List<String> parsePostfix(String f) {
    List<String> reversePolish = new ArrayList<>();
    String s = "";
    for (int i = 0; i < f.length(); i++) {
      Character c = f.charAt(i);
      if (c.equals(' ')) {
        if (!s.equals("")){
          reversePolish.add(s);
        }
        s = "";
      }
      else{
        s += c;
      }
    }
    if (!s.equals("")){
        reversePolish.add(s);
    }

	return reversePolish;
}

private Boolean isOperation(String s) {
    return (s.equals("|") || s.equals("&"));
    
  }

  //  

  /**
    prends une string et revoie soit un Protocl soir un Equals Soit un Different 
   */
  private Expression makeConstant(String s) {
    if (s.contains("=")) {
      String[] tbl = s.split("=");
      Field f = new Field(tbl[0]);
      String val = tbl[1];
      return new Equals(f, val);
    }

    if (s.contains("!")) {
      String[] tbl = s.split("!");
      Field f = new Field(tbl[0]);
      String val = tbl[1];
      return new Different(f, val);
    }

    return new Protocol(s);
    
    
  }


  private Expression makeExpression(List<String> reversePolish) {
    Stack<Expression> s = new Stack<>();


    for (String courant : reversePolish){
      if (!isOperation(courant)) {
        s.push(makeConstant(courant));
      } else {
        switch (courant) {
          case "|":
            Expression l = s.pop();
            Expression r = s.pop();
            
            s.push(new Or(l, r));
            break;
          case "&":
            Expression l2 = s.pop();
            Expression r2 = s.pop();
           
            s.push(new And(l2,r2));
            break;
          default:
            throw new IllegalArgumentException("Operateur invalide: " + courant);

        }

      }

    }
    return s.pop();
  }

  public Expression getExpression() {
    return this.e;
}
}