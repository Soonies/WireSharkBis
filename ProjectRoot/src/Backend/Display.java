package Backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Affichage.Panneau;
import Filtre.Filtre;


public class Display {
    


  public static void main(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException("Pas de fichier cible specifie: indiquer l'adresse du fichier cible");
    }
    String file = args[0];
    String filterTxt = args[1];

    String arguments = parseFilterFile(filterTxt);

    File f = new File(file);
    new Parser(f);
    afficheurFinal(arguments);
  }

  private static String parseFilterFile(String path){
    File fileFilter = new File(path);
    try {
      BufferedReader br = new BufferedReader(new FileReader(fileFilter));
      String line = br.readLine();
      br.close();
      if(line != null){
        return line;
      }
      else{
        return "";
      }
    } catch (IOException e) {
      System.err.println("Fichier non trouve:");
      e.printStackTrace();
    }
    return "";
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

    public static void afficheurFinal(String args) {

      Trame t = Trame.getTrame(0);
      Iterator<Trame> ite = t.iterator();
      List<Trame> lsTrames = new ArrayList<>();
      while (ite.hasNext()) {
        Trame next = ite.next();
        Filtre f = new Filtre(args, next);
        if (!next.isRejected() && f.getValue()) {
          lsTrames.add(next);
        }
      }
      Panneau p = new Panneau(lsTrames);
      String out = p + "";
      System.out.println(out);
      writer(out);
    }
  }