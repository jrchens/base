package gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.util.Map;

public class GsonTest {

    private static final Logger logger = LoggerFactory.getLogger(GsonTest.class);

    @Test
    public void testToJson() throws Exception {
        Gson gson = new Gson();
        JavaBeanObject obj = new JavaBeanObject();
        obj.setUrl("http://foo.com/path?a=b&c=d");
        logger.info("json: {}", gson.toJson(obj));
    }

    @Test
    public void testToMap() throws Exception {
        Gson gson = new Gson();
        String json = "{'key':'username','value':'jason'}";

        Map<String,String> map = gson.fromJson(json,new TypeToken<Map<String, String>>() {
        }.getType());

        logger.info("key:{}, value:{}",map.get("key"),map.get("value"));
    }

    @Test
    public void testToJsonUnSafeHtml() throws Exception {
        Gson gson = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().disableHtmlEscaping().create();
        JavaBeanObject obj = new JavaBeanObject();
        obj.setUrl("http://foo.com/path?a=b&c=d");
        logger.info("json: {}", gson.toJson(obj));


        gson = new GsonBuilder().disableHtmlEscaping().create();
        logger.info("json: {}", gson.toJson(obj));
    }
}

class JavaBeanObject implements java.io.Serializable {
    private String url;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JavaBeanObject{");
        sb.append("url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}