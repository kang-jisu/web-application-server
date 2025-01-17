package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

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

            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));

            String line = br.readLine();
            if( line==null ) {
                return;
            }

            String[] tokens = line.split(" "); // 첫줄

//            while(!"".equals(line)) { // 라인 마지막인경우 while문 break
//                line = br.readLine();
//                log.debug("request line : {}", line);
//            }
            String url = HttpRequestUtils.getUrl(line);
            responseToClient(out, url);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getDefaultUrl(String[] tokens){
        String url = tokens[1];
        if(url.equals("/")){
            url = "/index.html";
        }
        return url;
    }

    private void responseToClient(OutputStream out, String url) throws  IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = Files.readAllBytes( new File("webapp" + url).toPath());
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private String getResponseHeaderDate(){
        ZonedDateTime now = ZonedDateTime.now();
        String date = now.format(DateTimeFormatter.RFC_1123_DATE_TIME);
        return date;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Date: "+getResponseHeaderDate()+"\r\n");
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
