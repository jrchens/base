package apache.velocity;


import com.google.common.collect.Lists;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Properties;

public class GenerateHtml {

    @Test
    public void generateHtml() {
        try {
            String vmDir = "/Users/shengchen/Documents/repos/sample/sample-common/src/main/resources/me/sample/cms/vm";
            Properties properties = new Properties();
            properties.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, vmDir);

            Velocity.init(properties);

            VelocityContext context = new VelocityContext();

            List<Integer> nums = Lists.newArrayList();
            for (int i = 0; i < 100; i++) {
                nums.add(i + 1);
            }

            context.put("nums", nums);

            Template template = Velocity.getTemplate("index.vm", "utf-8");

            // StringWriter sw = new StringWriter();
            File dir = new File("/usr/local/var/cms");
            File dest = new File(dir, "index.html");
            FileWriter fw = new FileWriter(dest);

            template.merge(context, fw);

            fw.flush();
            fw.close();


        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } catch (ParseErrorException e) {
            e.printStackTrace();
        } catch (MethodInvocationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
