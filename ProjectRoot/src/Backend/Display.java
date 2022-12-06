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
	  String file = "ProjectRoot/data/1.txt";
      if(args.length != 0){
        file = args[0];
      }
      File f = new File(file);
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