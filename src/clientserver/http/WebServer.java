package src.clientserver.http;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public WebServer(){
        System.out.println("웹서버 시작됨");
        try (ServerSocket serverSocket = new ServerSocket(80)){
            while (true){
                System.out.println("클라이언트 대기중");
                Socket remote = serverSocket.accept();
                System.out.println("연결 생성");
                new Thread(new ClientHandler(remote)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new WebServer();
    }
}
