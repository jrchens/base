package lang;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathTest {
    private static final Logger logger = LoggerFactory.getLogger(MathTest.class);

    @Test
    public void testPowSqrt() {
        double p = Math.pow(2d, 16d);
        logger.info("pow(2d, 16d): {}", p);

        double s = Math.pow(16, 1d / 4d);
        logger.info("pow(16, 1d/4d): {}", s);
    }
}
