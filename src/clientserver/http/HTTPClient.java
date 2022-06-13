package src.clientserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class HTTPClient {
    public HTTPClient(){
        System.out.println("HTTP 클라이언트 시작");
        try {
            InetAddress address = InetAddress.getByName("127.0.0.1");
            Socket connection = new Socket(address, 80);

            try (OutputStream out = connection.getOutputStream()){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sendGet(out);
                System.out.println(getResponse(in));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HTTPClient();
    }

    private void sendGet(OutputStream out){
        try {
            out.write("GET /default\r\n".getBytes());
            out.write("User-Agent: Mozilla/5.0\r\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getResponse(BufferedReader in){
        try {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine=in.readLine())!=null){
                response.append(inputLine).append("\n");
            }
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
