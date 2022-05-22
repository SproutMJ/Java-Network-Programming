package src.clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadEchoServer implements Runnable{
    private static Socket clientSocket;

    public ThreadEchoServer(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public static void main(String[] args) {
        System.out.println("Threaded Echo Server");
        try (ServerSocket serverSocket = new ServerSocket(6000)){
            while (true){
                System.out.println("Waiting for Connection");
                clientSocket = serverSocket.accept();
                ThreadEchoServer echoServer = new ThreadEchoServer(clientSocket);
                new Thread(echoServer).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("실행 종료");
    }

    @Override
    public void run() {
        System.out.println("다음으로 연결됨: " + Thread.currentThread());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)){
            String line;
            while((line=br.readLine())!=null){
                System.out.println("Client Request [" + Thread.currentThread() + "]: " + line);
                out.println(line);
            }
            System.out.println("Client Terminated [" + Thread.currentThread() + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

