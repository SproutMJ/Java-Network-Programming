package src.clientserver.partserver;

import src.clientserver.chatserver.HelperMethods;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class PartsClient {
    public PartsClient(){
        System.out.println("부품 클라이언트 시작됨");
        SocketAddress address = new InetSocketAddress("127.0.0.1", 5000);
        try (SocketChannel socketChannel = SocketChannel .open(address)){
            System.out.println("부품 서버와 연결됨");
            Scanner sc = new Scanner(System.in);
            while (true){
                System.out.print("파트 이름 입력: ");
                String partName = sc.nextLine();
                if(partName.equalsIgnoreCase("exit")){
                    HelperMethods.sendMessage(socketChannel, "exit");
                    break;
                }else {
                    HelperMethods.sendMessage(socketChannel, partName);
                    System.out.println("가격: " + HelperMethods.receiveMessage(socketChannel));
                }
            }
            System.out.println("부품 클라이언트 종료됨");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PartsClient();
    }
}
