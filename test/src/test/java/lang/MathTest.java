package lang;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MathTest {
    private static final Logger logger = LoggerFactory.getLogger(MathTest.class);

    @Test
    public void testPowSqrt() {
        NumberFormat format = new DecimalFormat("#,###.##");
        for (double i = 0; i < 32; i++) {
            logger.info("pow(2d, {}d): {}", i,format.format(Math.pow(2d, i)));
        }
        double p = Math.pow(2d, 16d);
        logger.info("pow(2d, 16d): {}", p);

        double s = Math.pow(16, 1d / 4d);
        logger.info("pow(16, 1d/4d): {}", s);
    }
}
