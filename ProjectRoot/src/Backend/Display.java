package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Affichage.Panneau;


public class Display {
    
  public static void main(String[] args) {
      if(args.length == 0){
        System.err.println("Attention ! Il est nécéssaire de mettre en argument lors de l'éxecution un fichier comprenant une ou plusieurs trames.");
      }
      File f = new File(args[0]);
      new Parser(f);
      afficheurFinal();
    }

    public static void writer(String output) {
      try{
        PrintWriter writer = new PrintWriter("ProjectRoot/data/output.txt", "UTF-8");
        writer.println(output);
        writer.close();
      }catch(FileNotFoundException | UnsupportedEncodingException e){
        e.printStackTrace();
      }

      
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
      Panneau p = new Panneau(lsTrames);
      String out = p + "";
      System.out.println(out);
      writer(out);
    }
  }