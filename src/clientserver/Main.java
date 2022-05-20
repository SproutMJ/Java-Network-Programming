package src.clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        System.out.println("에코 서버입니다.");

        Socket clientSocket = null;
        try(ServerSocket serverSocket = new ServerSocket(6000)){
            System.out.println("Waiting....");
            clientSocket = serverSocket.accept();
            System.out.println("Connected");
        }catch (Exception e){

        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)){
            String line;
            while((line = br.readLine()) != null){
                System.out.println("Server: " + line);
                out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
