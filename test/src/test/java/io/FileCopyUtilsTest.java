package io;

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileReader;

public class FileCopyUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(FileCopyUtilsTest.class);

    @Test
    public void readFile2String() throws Exception {
        File file = new File("/Users/ChenSheng/Desktop/data.csv");
        String res = FileCopyUtils.copyToString(new FileReader(file));
        // logger.info("{}",res);
        System.out.println(BaseEncoding.base64().encode(res.getBytes(Charsets.UTF_8)));
    }
}
