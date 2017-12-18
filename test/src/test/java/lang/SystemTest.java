package lang;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class SystemTest {

    private static final Logger logger = LoggerFactory.getLogger(SystemTest.class);

    @Test
    public void testProperties() {
        Properties p = System.getProperties();
        Iterator<Map.Entry<Object, Object>> iter = p.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = iter.next();
            logger.info("{}:{}", entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void testEnvs() {
        Map<String, String> m = System.getenv();

        Iterator<Map.Entry<String, String>> iter = m.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = iter.next();
            logger.info("{}:{}", entry.getKey(), entry.getValue());
        }
    }


    @Test
    public void testLocal() {
        // -Duser.timezone=GMT -Duser.language=en -Duser.country=US
        Locale locale = Locale.getDefault();
        logger.info("{}:{}:{}:{}", locale.getCountry(), locale.getLanguage(), locale.getDisplayName(), locale.toString());
        // US:en:English (United States):en_US
    }
}
