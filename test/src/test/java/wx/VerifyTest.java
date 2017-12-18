package wx;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class VerifyTest {
    private static final Logger logger = LoggerFactory.getLogger(VerifyTest.class);

    @Test
    public void verify() throws Exception {

        String token = "uW4FnzeSKkGtoLgPV8fwRvP041EFURDX"; // store in database


        String signature = "3eb3564f4ab34291c7cf9121b180dd733e98c831", nonce = "8CA511708BE2", echostr = "4DAB";
        long timestamp = 1506660660l;


        String[] arr = Lists.newArrayList(token, String.valueOf(timestamp), nonce).toArray(new String[]{});
        Arrays.sort(arr);

        StringBuffer buffer = new StringBuffer();
        for (String str : arr) {
            buffer.append(str);
        }
        String sha1 = Hashing.sha1().hashString(buffer.toString(), Charsets.UTF_8).toString();

        logger.info("signature: {},sha1: {}", signature, sha1);


        URIBuilder uriBuilder = new URIBuilder("http://localhost:8080/");
        uriBuilder.addParameter("signature", signature);
        uriBuilder.addParameter("nonce", nonce);
        uriBuilder.addParameter("echostr", echostr);
        uriBuilder.addParameter("timestamp", String.valueOf(timestamp));
        logger.info("uri: {}", uriBuilder.build().toURL());

        // http://localhost:8080/?signature=2e813689ba128fae0d0c81516998bc5ef899e5b9&nonce=8CA511708BE2&echostr=4DAB&timestamp=1506660660


    }
}
