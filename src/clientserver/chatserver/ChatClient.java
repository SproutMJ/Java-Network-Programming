package src.clientserver.chatserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ChatClient {
    public ChatClient(){
        SocketAddress address = new InetSocketAddress("127.0.0.1", 5000);
        try (SocketChannel socketChannel = SocketChannel.open(address)){
            System.out.println("서버와 연결됨");
            String message = "";
            Scanner sc = new Scanner(System.in);
            while (true){
                System.out.println("서버로 부터 대기중...");
                // System.out.println("서버: " + HelperMethods.receiveFixedMessage(socketChannel)); 고정길이
                System.out.println("서버: " + HelperMethods.receiveMessage(socketChannel));
                System.out.print(">>> ");
                message = sc.nextLine();

                if(message.equalsIgnoreCase("exit")){
                    //HelperMethods.sendFixedMessage(socketChannel, "클라이언트 종료"); 고정길이
                    HelperMethods.sendMessage(socketChannel, "클라이언트 종료");
                    break;
                }
                // HelperMethods.sendFixedMessage(socketChannel, message); 고정길이
                HelperMethods.sendMessage(socketChannel, message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatClient();
    }
}
