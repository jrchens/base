package apache.commons;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomStringUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(RandomStringUtilsTest.class);

    @Test
    public void testRandomAlphanumeric() throws Exception {
        String random = RandomStringUtils.randomAlphanumeric(12);
        logger.info("random:{}", random);
    }
    //
}
