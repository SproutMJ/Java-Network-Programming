package src.clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("Echo Client");

        try{
            System.out.println("Waiting for Connection....");
            InetAddress localAddress = InetAddress.getLocalHost();

            try(Socket clientSocket = new Socket(localAddress, 6000);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
                System.out.println("Connected Server");
                Scanner scanner = new Scanner(System.in);
                while(true){
                    System.out.print("Enter text: ");
                    String line = scanner.nextLine();

                    if("exit".equals(line))break;

                    out.println(line);
                    String response = br.readLine();
                    System.out.println("Server Response: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e){

        }
    }
}
