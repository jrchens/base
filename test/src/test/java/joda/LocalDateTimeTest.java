package joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

public class LocalDateTimeTest {

    public static void main(String[] args) {
        String now = new LocalDateTime().toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(now);
        now = DateTime.now(DateTimeZone.getDefault()).toDate().toString();
        System.out.println(now);
        now = DateTime.now().toDate().toString();
        System.out.println(now);
    }

}
