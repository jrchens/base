package jsoup;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class HTMLParserTest {
    private static final Logger logger = LoggerFactory.getLogger(HTMLParserTest.class);

    @Test
    public void testParse() {
        try {

            Writer writer = new FileWriter(new File("/Users/ChenSheng/Desktop/data.csv"));
            CSVPrinter printer = CSVFormat.DEFAULT.withHeader("Title", "Summary", "Author", "PubDate").print(writer);

            File file = new File("/Users/ChenSheng/Desktop/data.txt");
            String html = FileCopyUtils.copyToString(Files.newReader(file, Charsets.UTF_8));
            Document doc = Jsoup.parse(html);

            Elements eles = doc.select(".main-info");
            int size = eles.size();
            // List<Object[]> data = Lists.newArrayList();
            for (int i = 0; i < size; i++) {
                Element ele = eles.get(i);
                String title = ele.select(".text-ellipsis").html();
                String summary = ele.select(".summary").html();
                String[] _data = ele.select(".from > .mr").text().split(" ");
                String author = _data[0];
                String date = _data[_data.length - 1];

                if (_data.length != 3) {
                    logger.info("title:{},summary:{},_data:{}", title, summary, _data);
                    continue;
                }

                // title,summary,mr a mr
                // Object[] e4 = new Object[]{
                // title,summary,author,date
                // };
                // data.add(e4);

                printer.printRecord(title, summary, author, date);
            }

            printer.close();

            // logger.info("size: {}, data[0]: {}",size,data.get(0));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
