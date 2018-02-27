package lang;

import org.junit.Test;
import org.springframework.util.Assert;

public class IntegerTest {
    @Test
    public void testEquals() throws Exception {
        Integer a = 0;
        Integer b = 0;
        Assert.isTrue(a.equals(b));
    }
}
