package src.clientserver.chatserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class HelperMethods {
    public static void sendFixedMessage(SocketChannel socketChannel, String message){
        try {
            ByteBuffer buf = ByteBuffer.allocate(64);
            buf.put(message.getBytes(StandardCharsets.UTF_8));
            buf.flip();
            while(buf.hasRemaining()) socketChannel.write(buf);

            System.out.println("보냄: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String receiveFixedMessage(SocketChannel socketChannel){
        String message = "";
        try {
            ByteBuffer buf = ByteBuffer.allocate(64);
            socketChannel.read(buf);
            buf.flip();
            while(buf.hasRemaining()) message+=(char)buf.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
