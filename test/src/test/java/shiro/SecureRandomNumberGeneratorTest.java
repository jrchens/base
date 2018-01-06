package shiro;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;

public class SecureRandomNumberGeneratorTest {
    @Test
    public void test() throws Exception {

//        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
//        String randomSalt = secureRandomNumberGenerator.nextBytes(4).toHex();
//        System.out.println(String.format("randomSalt:%s",randomSalt));

        String username = "admin";
        String password = "123456";
        String md5Password = Hashing.md5().hashString(password, Charsets.UTF_8).toString();
        String md5UsernamePassword = Hashing.md5().hashString(username.concat(":").concat(md5Password), Charsets.UTF_8).toString();


        String salt = "ZuVQXbee";
        Sha256Hash sha256Hash = new Sha256Hash(md5UsernamePassword, salt);
        String hex = sha256Hash.toHex();
        String str = sha256Hash.toString();

        System.out.println(String.format("hex:%s , str:%s",hex,str));


    }
}
