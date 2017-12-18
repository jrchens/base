package guava;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ListsTest {
    private final static Logger logger = LoggerFactory.getLogger(ListsTest.class);

    @Test
    public void testAsList() throws Exception {
        List<String> base = Lists.newArrayList();
        String[] arr = {"123,456", "789", "a,,b,c"};

        logger.info("data: {}", Arrays.toString(Lists.asList(base, arr).toArray()));
        ;

    }


    @Test
    public void testNewArrayList() throws Exception {
        String[] arr = {"123,456", "789", "a,,b,c"};
        logger.info("data: {}", Lists.newArrayList(arr));
    }
}
