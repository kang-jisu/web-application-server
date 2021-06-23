package webserver;


import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
}
