package joda;

import org.joda.time.LocalDateTime;

public class LocalDateTimeTest {

    public static void main(String[] args) {
        String now = new LocalDateTime().toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(now);
    }

}
