package src.clientserver.chatserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ChatServer {
    public ChatServer(){
        System.out.println("ChatServer started");
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(5000));

            boolean isRun = true;
            while (isRun){
                System.out.println("요청 대기중...");
                SocketChannel socketChannel = serverSocketChannel.accept();

                System.out.println("클라이언트와 연결됨");
                String message;
                Scanner sc = new Scanner(System.in);
                while(true){
                    System.out.print(">>> ");
                    message = sc.nextLine();
                    if(message.equalsIgnoreCase("exit")){
                        HelperMethods.sendFixedMessage(socketChannel, "서버 종료");
                        isRun = false;
                        break;
                    }else{
                        HelperMethods.sendFixedMessage(socketChannel, message);
                        System.out.println("클라이언트로 부터 대기중...");
                        System.out.println("클라이언트: " + HelperMethods.receiveFixedMessage(socketChannel));
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatServer();

    }
}
