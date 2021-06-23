package webserver;

import java.io.*;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            //InputStream 한 줄 단위로 읽기
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            boolean getUrl = false;
            String url;
            System.out.println("=====HTTP요청 값 read start=====");
            while( (line = br.readLine()) != null ){ // line이 Null인경우 처리
                if("".equals(line)) break; // 라인 마지막인경우 while문 break
                if(!getUrl) {
                    url =  getUrlFromHeader(line);
                    getUrl = true;
                }
                System.out.println(line);
            }
            System.out.println("=====HTTP요청 값 end=====");

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getUrlFromHeader(String line){
        String[] tokens = line.split(" ");
        return tokens[1];
    }

    private String getResponseHeaderDate(){
        ZonedDateTime now = ZonedDateTime.now();
        String date = now.format(DateTimeFormatter.RFC_1123_DATE_TIME);
        return date;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Connection: Keep-Alive");
            dos.writeBytes("Date: "+getResponseHeaderDate());
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
