package joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.util.Date;

public class DateTimeTest {


    @Test
    public void testPlus() throws Exception {

        DateTime dt =DateTime.parse("2012-09-09 10:12:12",
                DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDateTime(DateTimeZone.getDefault());

//        DateTime dt = DateTime.now();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        System.out.println(dt.getMillis());
        System.out.println(dt.toString(pattern));
        System.out.println(dt.plusSeconds(7200).toDateTime().getMillis());
        System.out.println(dt.plusSeconds(7200).toDateTime().toString(pattern));
        System.out.println(dt.plusDays(30).toDateTime().getMillis());
        System.out.println(dt.plusDays(30).toDateTime().toString(pattern));
    }


    @Test
    public void testTodayTime() throws Exception {

        DateTime now = DateTime.now();
        Date start = now.withTime(0, 0, 0, 0).toDate();
        Date end = now.withTime(23, 59, 59, 0).toDate();

        String startStr = DateTime.now(DateTimeZone.getDefault()).withMillis(start.getTime()).toString("yyyy-MM-dd HH:mm:ss");
        String endStr = DateTime.now(DateTimeZone.getDefault()).withMillis(end.getTime()).toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(startStr);
        System.out.println(endStr);
    }

    @Test
    public void testParse() throws Exception {
        String str = "2018-02-04 23:23:23";
        Date date = DateTime.parse(str, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDateTime(DateTimeZone.getDefault()).toDate();

        System.out.println(date);
    }
}
