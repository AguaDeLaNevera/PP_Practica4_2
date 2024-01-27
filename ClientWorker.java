import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientWorker implements Runnable {
    private Socket client;
    Random rndm = new Random();
    int random = rndm.nextInt(100)+1;
    public ClientWorker(Socket c) {
        client = c;
    }
    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader bf = new BufferedReader(new
                        InputStreamReader(client.getInputStream()));
                String linea;
                List<String> llista = new ArrayList<>();
                while ((linea = bf.readLine()) != null && !linea.isEmpty()) {
                    System.out.println("Received line: " + linea);
                    llista.add(linea);
                    if(llista.size()==3){
                        break;
                    }
                }

                double resultat = 0;
                int n1 = 0;
                int n2 = 0;
                try{
                    n1 = Integer.parseInt(llista.get(1));
                    n2 = Integer.parseInt(llista.get(2));
                }
                catch(NumberFormatException e){
                    e.printStackTrace();
                }

                switch (llista.get(0)){
                    case "suma": resultat = n1+n2;
                        break;
                    case "resta": resultat = n1-n2;
                        break;
                    case "multiplicacio": resultat = n1*n2;
                        break;
                    case "divisio": resultat = (double)n1/n2;
                        break;
                    default:
                        PrintWriter pw = new PrintWriter(client.getOutputStream());
                        pw.println("operació incorrecte, introduix suma, resta, multiplicacio o divisio");
                        pw.flush();
                }
                llista.clear();
                PrintWriter pw = new PrintWriter(client.getOutputStream());
                pw.println(resultat);
                pw.flush();
            }
        } catch (IOException ex) {
            System.err.println("ClientWorker error.");
        }

    }
}
