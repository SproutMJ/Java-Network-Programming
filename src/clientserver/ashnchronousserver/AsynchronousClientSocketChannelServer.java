package src.clientserver.ashnchronousserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsynchronousClientSocketChannelServer {
    public static void main(String[] args) {
        System.out.println("비동기 클라이언트 시작됨");
        try (AsynchronousSocketChannel client = AsynchronousSocketChannel.open()){
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5000);
            Future future =client.connect(hostAddress);
            future.get();

            System.out.println("클라이언트 시작됨: " + client.isOpen());
            System.out.print("서버로 메시지 전송: ");

            Scanner sc = new Scanner(System.in);
            String msg;
            while (true){
                System.out.print(">>> ");
                msg = sc.nextLine();
                ByteBuffer buf = ByteBuffer.wrap(msg.getBytes());
                Future result = client.write(buf);
                while (!result.isDone());
                if(msg.equalsIgnoreCase("exit")) break;
            }

        } catch (ExecutionException | IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
