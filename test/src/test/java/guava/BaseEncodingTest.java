package guava;

import com.google.common.io.BaseEncoding;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseEncodingTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseEncodingTest.class);

    @Test
    public void encode64() throws Exception {// "UTF-8"
        String encode = BaseEncoding.base64().encode("http://smejr.gov.cn/wx/bdc/".getBytes());
        logger.info("{}", encode);
        String decode = new String(BaseEncoding.base64().decode(encode));
        logger.info("{}", decode);


    }

}
