import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Exec{
    public static void main(String[] args) throws IOException {
        File f = new File("./WireSharkBis/ProjectRoot/data/1.txt");

        StringBuilder trame = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(f));

        for(String line = br.readLine() ; line != null ; line = br.readLine() ) {

            if(!(line.charAt(0) == '\n')){
                for(int i=6; i<line.length(); i++){
                    if(line.charAt(i) != ' ' || line.charAt(i+1) != ' '){
                        trame.append(line.charAt(i));
                    }
                    else{
                        trame.append(' ');
                        break;
                    }
                }
            }

            else{
                System.out.println("Bonjour");
            }

        }
        br.close();
    }
}