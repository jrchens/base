package joda;

import org.joda.time.DateTime;

public class DateTimeTest {

    public static void main(String[] args) {
        DateTime dt = DateTime.now();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        System.out.println(dt.getMillis());
        System.out.println(dt.toString(pattern));
        System.out.println(dt.plusSeconds(7200).toDateTime().getMillis());
        System.out.println(dt.plusSeconds(7200).toDateTime().toString(pattern));
        System.out.println(dt.plusDays(30).toDateTime().getMillis());
        System.out.println(dt.plusDays(30).toDateTime().toString(pattern));
    }

}
