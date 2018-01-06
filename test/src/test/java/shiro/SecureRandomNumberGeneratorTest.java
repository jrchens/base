package shiro;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.junit.Test;

public class SecureRandomNumberGeneratorTest {
    @Test
    public void test() throws Exception {

        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        String randomSalt = secureRandomNumberGenerator.nextBytes().toHex();
        System.out.println(String.format("randomSalt:%s",randomSalt));

        String[] usernames = {"admin","jason","mason"};
        String[] salts = {"ZuVQXbee","w0N3N6OL","sPxiGWxX"};
        String password = "123456";
        String md5Password = Hashing.md5().hashString(password, Charsets.UTF_8).toString();
        int idx = 0;
        for (String username:usernames
             ) {
            String md5UsernamePassword = Hashing.md5().hashString(username.concat(":").concat(md5Password), Charsets.UTF_8).toString();
            String salt = salts[idx];idx++;
            Sha512Hash sha256Hash = new Sha512Hash(md5UsernamePassword, salt);
            String hex = sha256Hash.toHex();
            String str = sha256Hash.toString();

            System.out.println(String.format("hex:%s , str:%s",hex,str));
        }



    }
}
