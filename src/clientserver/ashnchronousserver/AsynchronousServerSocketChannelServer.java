package src.clientserver.ashnchronousserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsynchronousServerSocketChannelServer {
    public AsynchronousServerSocketChannelServer(){
        System.out.println("비동기 서버 시작됨");
        try (AsynchronousServerSocketChannel socketChannel = AsynchronousServerSocketChannel.open()){
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5000);
            socketChannel.bind(hostAddress);

            System.out.println(" 클라이언트 접속 대기중....");
            Future result = socketChannel.accept();

            try (AsynchronousSocketChannel clientChannel = (AsynchronousSocketChannel) result.get()){
                System.out.print("클라이언트로 부터 수신: ");
                while (clientChannel != null && clientChannel.isOpen()){
                    ByteBuffer buf = ByteBuffer.allocate(32);
                    Future ret = clientChannel.read(buf);

                    while (ret.isDone());
                    ret.get();
                    ret.get(10, TimeUnit.SECONDS);

                    buf.flip();
                    String msg = new String(buf.array()).trim();
                    System.out.println(msg);
                    if(msg.equals("exit")) break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AsynchronousServerSocketChannelServer();
    }
}
