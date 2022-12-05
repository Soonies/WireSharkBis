package Backend;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Affichage.Panneau;


public class Display {
    
    public static void main(String[] args) {
      File f = new File("ProjectRoot/data/1.txt");
      new Parser(f);
      afficheurFinal();
    }


    public static void afficheurFinal() {

      Trame t = Trame.getTrame(0);
      Iterator<Trame> ite = t.iterator();
      List<Trame> lsTrames = new ArrayList<>();
      while (ite.hasNext()) {
        Trame next = ite.next();
        if (!next.isRejected()) {
          lsTrames.add(next);
        }
      }
      Panneau p  =  new Panneau(lsTrames);
      String out = p + "";
      System.out.println(out);
    }
  }