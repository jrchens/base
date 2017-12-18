package spring;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class StringUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(StringUtilsTest.class);

    @Test
    public void testTokenizeToStringArray() throws Exception {
        String[] strs = {"123,456", "789", "a,,b,c"};
        for (String str : strs) {
            logger.info(str);
            String[] arr = StringUtils.tokenizeToStringArray(str, ",", true, true);
            logger.info("array : {}", Arrays.toString(arr));
        }

    }
}
