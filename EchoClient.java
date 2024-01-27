import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoClient {

    public static BufferedReader getFlujo(InputStream is) {
        InputStreamReader isr
                = new InputStreamReader(is);
        BufferedReader bfr
                = new BufferedReader(isr);
        return bfr;
    }
    public static void main(String[] args) {
        String local = "localhost";
        int port = 5555;
        Socket socket = new Socket();
        InetSocketAddress direccion = new InetSocketAddress(local, port);
        try {
            socket.connect(direccion);
            while (true) {
                System.out.println("Client text:");
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(System.in));
                String txt = reader.readLine();
                String nombre1, nombre2;
                System.out.println("Introduex el primer nombre: ");
                nombre1 = reader.readLine();
                System.out.println("Introduex el segon nombre: ");
                nombre2 = reader.readLine();
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println(txt);
                pw.println(nombre1);
                pw.println(nombre2);
                pw.flush();
                BufferedReader bfr = EchoClient.getFlujo(socket.getInputStream());
                System.out.println(bfr.readLine());
            }
        } catch (IOException e) {
            System.out.println("Error Client");
        }
    }
}
