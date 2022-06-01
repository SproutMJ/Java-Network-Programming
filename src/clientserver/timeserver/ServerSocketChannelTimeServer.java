package src.clientserver.timeserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ServerSocketChannelTimeServer {
    public static void main(String[] args) {
        System.out.println("ServerSocketChannelTimeServer started");
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(5000));

            while(true){
                System.out.println("요청을 기다리는 중");
                SocketChannel socketChannel = serverSocketChannel.accept();

                if(socketChannel!=null){
                    String message = "Date: " + new Date(System.currentTimeMillis());
                    ByteBuffer buf = ByteBuffer.allocate(64);
                    buf.put(message.getBytes(StandardCharsets.UTF_8));
                    buf.flip();
                    while(buf.hasRemaining()) socketChannel.write(buf);
                    System.out.println("보냄: " + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
