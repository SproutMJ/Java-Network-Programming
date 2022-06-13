package src.clientserver.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable{
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println(this.socket + "시작");
        handleRequest(this.socket);
        System.out.println(this.socket + "종료");
    }

    public void handleRequest(Socket socket){
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            String header = in.readLine();
            StringTokenizer tokenizer = new StringTokenizer(header);
            String method = tokenizer.nextToken();

            if(method.equals("GET")){
                System.out.println("GET method");
                String query = tokenizer.nextToken();
                StringBuilder response = new StringBuilder();
                response.append("<html><h1>Home Page</h1></html");
                sendResponse(socket, 200, response.toString());
            }else{
                System.out.println("method not recognized");
                sendResponse(socket, 405, "Method Not Allowed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendResponse(Socket socket, int statusCode, String response){
        String status;
        String header = "Server: WebServer\r\n";
        String contentType = "Content-Type: text/html\r\n";

        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream())){
            if(statusCode == 200){
                status = "HTTP/1.0 200 OK\r\n";
                String lengthHeader = "Content-Length: " + response.length() + "\r\n";
                out.writeBytes(status);
                out.writeBytes(header);
                out.writeBytes(contentType);
                out.writeBytes(lengthHeader);
                out.writeBytes("\r\n");
                out.writeBytes(response);
            }else if (statusCode == 405){
                status = "HTTP/1.0 405 Method Not Allowed\r\n";
                out.writeBytes(status);
                out.writeBytes("\r\n");
            }else{
                status = "HTTP/1.0 404 Not Found\r\n";
                out.writeBytes(status);
                out.writeBytes("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
