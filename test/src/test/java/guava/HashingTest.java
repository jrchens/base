package guava;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//import org.springframework.util.Assert;

public class HashingTest {
    private static final Logger logger = LoggerFactory.getLogger(HashingTest.class);

    @Test
    public void testHmacSha512() {
        String password = "123456";
        String username = "admin";
        SecretKey key = new SecretKeySpec(username.getBytes(Charsets.UTF_8), "HmacSHA512");
        String pwd = Hashing.hmacSha512(key).hashBytes(password.getBytes(Charsets.UTF_8)).toString();

        String sec = "0f6b6faebd3bed09de0ef528d53623013d8c4e7a25370379162b9f9f6bbb4bcd1bd017b3cb3ade8a06aa0afa5cbcb0880aec1368236b252a846c15f9eca36da1";

        Assert.assertEquals(pwd, sec);
    }

    @Test
    public void testUserPasswordEncrypt() {
        // md5(123456) --> e10adc3949ba59abbe56e057f20f883e
        String[] datas = {"admin:e10adc3949ba59abbe56e057f20f883e,ZuVQXbee",
                "jason:e10adc3949ba59abbe56e057f20f883e,w0N3N6OL",
                "mason:e10adc3949ba59abbe56e057f20f883e,sPxiGWxX"};

        for (String data: datas
             ) {
            String[] ups = data.split(","); // user,password,salt
            String[] up = ups[0].split(":");

            String encryptPassword = Hashing.md5().hashString(ups[0],Charsets.UTF_8).toString();

            SecretKey key = new SecretKeySpec(ups[1].getBytes(Charsets.UTF_8), "HmacSHA512");
            String pwd = Hashing.hmacSha512(key).hashBytes(encryptPassword.getBytes(Charsets.UTF_8)).toString();

            System.out.println(String.format("%s\t%s\t%s",up[0],pwd,ups[1]));

        }
    }

    @Test
    public void encrypt() throws Exception {
        String username = "admin";
        String password = "12345";

        String input = username.concat("@@").concat(password);
        String frontCode = Hashing.sha256().hashString(input, Charsets.UTF_8).toString();

        String salt = "CwkEINJ8YXR6";
        // Key key = new SecretKeySpec(salt.getBytes(Charsets.UTF_8),"HmacSHA256");
        // String result = Hashing.hmacSha256(key).hashString(frontCode,Charsets.UTF_8).toString();
        String result = Hashing.hmacSha256(salt.getBytes(Charsets.UTF_8)).hashString(frontCode, Charsets.UTF_8).toString();

        logger.info("username:{},password:{},salt:{},result:{}", username, password, salt, result);
    }


    @Test
    public void testCRC32() {
        int input = 100;
        String crc32 = Hashing.crc32().hashInt(input).toString();
        logger.info(crc32);
        Assert.assertTrue(crc32.length() == 8);
    }

    @Test
    public void testCRC32C() {
        int input = 100;
        String crc32c = Hashing.crc32c().hashInt(input).toString();
        logger.info(crc32c);
        Assert.assertTrue(crc32c.length() == 8);
    }



    @Test
    public void testMD5() {
        String pwd = "123456";
        String result = Hashing.md5().hashString(pwd,Charsets.UTF_8).toString();

        logger.info(result);
    }
}
