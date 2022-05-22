package src.clientserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class MulticastServer {
    public static void main(String[] args) {
        System.out.println("Multicast Time Server");
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();

            while (true){
                String text = new Date().toString();
                byte[] buffer = new byte[256];
                buffer = text.getBytes();

                InetAddress group = InetAddress.getByName("224.0.0.0");
                DatagramPacket packet;
                packet = new DatagramPacket(buffer, buffer.length, group, 8888);
                socket.send(packet);
                System.out.println("Time sent: " + text);

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){

                }
            }
        }catch (SocketException e){

        }catch (Exception e){

        }
    }
}
