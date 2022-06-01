package src.clientserver.timeserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelTimeClient {
    public static void main(String[] args) {
        SocketAddress address = new InetSocketAddress("127.0.0.1", 5000);
        try(SocketChannel socketChannel=SocketChannel.open(address)){
            ByteBuffer buf = ByteBuffer.allocate(64);
            int size = socketChannel.read(buf);
            while(size>0){
                buf.flip();
                while(buf.hasRemaining()) System.out.print((char)buf.get());
                System.out.println();
                size = socketChannel.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
