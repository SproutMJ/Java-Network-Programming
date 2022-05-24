package src.clientserver;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SSLServerSocket {
    public static void main(String[] args) {
        try {
            SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            ServerSocket ss = ssf.createServerSocket(8000);
            System.out.println("SSLServerSocket Started");
            try (Socket socket = ss.accept();
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
                System.out.println("Client Socket Created");
                String line = null;
                while((line = br.readLine())!=null){
                    System.out.println(line);
                    out.println(line);
                }
                br.close();
                System.out.println("SSLServerSocket Terminated");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
