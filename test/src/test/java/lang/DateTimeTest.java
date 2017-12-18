package lang;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeTest {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeTest.class);

    @Test
    public void testDateTimeSeconds() {
//        LocalDateTime ldt = LocalDateTime.now();
//        String datetime = ldt.toString("yyyy-MM-dd HH:mm:ss");
//        logger.info("datetime: {}", datetime);
//
//        int millisOfSecond = ldt.getMillisOfSecond();
//        logger.info("millisOfSecond: {}", millisOfSecond);
//
//        int secondOfMinute = ldt.getSecondOfMinute();
//        logger.info("secondOfMinute: {}", secondOfMinute);
//
//        long milliseconds = ldt.toDate().getTime();
//        logger.info("milliseconds: {}", milliseconds);
//
//        long seconds = milliseconds / 1000;
//        logger.info("seconds: {}", seconds);


        @SuppressWarnings("deprecation")
        Date now = new Date(2037 - 1900, 11, 31, 23, 59, 59);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("now: {}", format.format(now));
        long milliseconds = now.getTime();
        logger.info("milliseconds: {}", milliseconds);
        long seconds = milliseconds / 1000;
        logger.info("seconds: {}", seconds);
        long in = Integer.MAX_VALUE;
        logger.info("Integer.MAX_VALUE: {}", in);
    }
}
