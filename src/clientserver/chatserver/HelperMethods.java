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

    public static void sendMessage(SocketChannel socketChannel, String message){
        try {
            ByteBuffer buf = ByteBuffer.allocate(message.length() + 1);
            buf.put(message.getBytes());
            buf.put((byte)0x00);
            buf.flip();
            while(buf.hasRemaining()) socketChannel.write(buf);
            System.out.println("Sent: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String receiveMessage(SocketChannel socketChannel){
        try {
            ByteBuffer buf = ByteBuffer.allocate(16);
            String msg = "";
            while(socketChannel.read(buf)>0){
                char read = 0x00;
                buf.flip();
                while(buf.hasRemaining()){
                    read = (char) buf.get();
                    if(read == 0x00) break;
                    msg+=read;
                }
                if(read == 0x00) break;
                buf.clear();
            }
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
