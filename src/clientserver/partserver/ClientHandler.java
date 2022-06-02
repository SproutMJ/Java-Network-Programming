package src.clientserver.partserver;

import src.clientserver.chatserver.HelperMethods;

import java.nio.channels.SocketChannel;

public class ClientHandler implements Runnable {
    private final SocketChannel socketChannel;

    public ClientHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        System.out.println("클라이언트 시작됨: " + this.socketChannel);
        String partName = "";
        while(true){
            partName = HelperMethods.receiveMessage(socketChannel);
            if(partName.equalsIgnoreCase("exit")) break;
            else{
                Float price = PartsServer.getPrice(partName);
                HelperMethods.sendMessage(socketChannel, ""+price);
            }

        }
        System.out.println("클라이언트 핸들러가 종료됨: " + this.socketChannel);
    }
}
