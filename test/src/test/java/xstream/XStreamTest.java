package xstream;

import cn.jrry.common.domain.Group;
import com.thoughtworks.xstream.XStream;
import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;

public class XStreamTest {
    private static final Logger logger = LoggerFactory.getLogger(XStreamTest.class);

    @Test
    public void write() throws Exception {
        XStream xstream = new XStream();
        Group record = new Group();
        record.setGroupname("admin");
        record.setViewname("Admin");
        record.setDeleted(false);
        record.setCrtime(new Timestamp(System.currentTimeMillis()));
        record.setCruser("jrchens");

        // xstream.ignoreUnknownElements("groupname");
        xstream.alias("SysGroup", Group.class);
        xstream.omitField(Group.class, "groupname");
        xstream.omitField(Group.class, "viewname");
        // xstream.allowTypes(new String[]{"groupname", "viewname"});
        logger.info("xml:{}", xstream.toXML(record));

    }


    @Test
    public void read() throws Exception {
        XStream xstream = new XStream();
        xstream.alias("SysGroup", Group.class);

        File file = new File("/Users/shengchen/Documents/Temporary/admin.xml");
        InputStream is = new FileInputStream(file);
//        SysGroup record =  (SysGroup)xstream.fromXML(is);
        Object record = xstream.fromXML(is);
        is.close();

        logger.info("xml:{}", record.toString());

    }
}
