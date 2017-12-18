package joda;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.springframework.format.datetime.joda.DateTimeParser;

import java.util.Locale;

public class DateTimeParserTest {

    @Test
    public void testParse() throws Exception {
        String datePattern = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
        DateTimeFormatter df = DateTimeFormat.forPattern(datePattern);


        DateTimeParser dateTimeParser = new DateTimeParser(df);
        DateTime dateTime = dateTimeParser.parse("Mon, 18 Dec 2017 02:28:32 GMT", Locale.US);

        System.out.println(dateTime);


    }
}
