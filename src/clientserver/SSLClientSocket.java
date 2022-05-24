package src.clientserver;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SSLClientSocket {
    public static void main(String[] args) {
        System.out.println("SSLClientSocket Started");
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (Socket socket = sf.createSocket("localhost", 8000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            Scanner sc = new Scanner(System.in);
            while(true){
                System.out.print("Enter Text: ");
                String line = sc.nextLine();
                if("exit".equalsIgnoreCase(line)) break;

                out.println(line);
                System.out.println("Server Response: " + line);
            }

            System.out.println("SSLClientSocket Terminated");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
