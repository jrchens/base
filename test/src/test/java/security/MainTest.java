package security;

import jjwt.JwtsTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Random;

public class MainTest {

    private static final Logger logger = LoggerFactory.getLogger(JwtsTest.class);

    @Test
    public void encode() throws NoSuchAlgorithmException, NoSuchProviderException {

//        String provider = "SUN";
//        String algorithm = "SHA1PRNG";
//        String res = new String(SecureRandom.getInstance(algorithm,provider).generateSeed(16));
//        logger.info("res: {}",res);

        char a = 'a';
        char A = 'A';
        char z = 'z';
        char Z = 'Z';
        StringBuffer buffer = new StringBuffer();
        for (char i = a; i <= z; i++) {
            buffer.append(i);
        }
        for (char i = A; i <= Z; i++) {
            buffer.append(i);
        }
        for (int i = 0; i <= 9; i++) {
            buffer.append(i);
        }

        String src = buffer.toString();
        logger.info("src: {}", src);

        Random rd = new Random();
        buffer.setLength(0);

        int len = src.length();
        for (int i = 0; i < 16; i++) {
            buffer.append(src.charAt(rd.nextInt(len)));
        }
        String res = buffer.toString();
        logger.info("res: {}", res);

//        RandomStringUtils
//        Provider[] ps = Security.getProviders();
//        for (Provider provider : ps) {
//            logger.info("provider: {}",provider.getName());
//        SUN
//        SunRsaSign
//        SunEC
//        SunJSSE
//        SunJCE
//        SunJGSS
//        SunSASL
//        XMLDSig
//        SunPCSC
//        Apple
//        }

    }
}
