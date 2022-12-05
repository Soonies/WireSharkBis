package Backend;
import java.io.File;

public class Exec {
    public static void main(String[] args) {
      File f = new File("../data/1.txt");
      new Parser(f);
    }   
}
