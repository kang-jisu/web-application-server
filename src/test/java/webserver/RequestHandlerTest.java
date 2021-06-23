package webserver;


import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class RequestHandlerTest extends Thread {

    @Test
    public void HTTP_응답헤더(){
        ZonedDateTime now = ZonedDateTime.now();
        String date = now.format(DateTimeFormatter.RFC_1123_DATE_TIME);
        System.out.println(date);
    }

    @Test
    public void splitHeader(){
        String line = "GET /index.html HTTP/1.1";
        String[] tokens = line.split(" ");
        assertEquals("/index.html", tokens[1]);
    }

    @Test
    public void 파일_경로() throws IOException {
        File file = new File("webapp"+"/index.html");
        byte[] body = Files.readAllBytes(file.toPath());
        System.out.write(body);
    }
}
