package guava;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

    @Test
    public void testToArray() throws Exception {
        String[] rps = {"1", "2", "3"};

        Integer max = 100;

        List<String> tlist = Lists.newArrayList(rps);
        tlist.add(String.valueOf(max));
        rps = tlist.toArray(new String[]{});

        logger.info("{}", Arrays.toString(rps));
    }



    @Test
    public void testRandomArray() throws Exception {
        String[] rps = {"1", "2", "3","4","5"};
        Random random = new Random();
        for (int i = 0;i<100; i++) {
            System.out.println(rps[random.nextInt(rps.length)]);
        }
    }
}
