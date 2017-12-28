package cn.jrry.sample.service.impl;

import cn.jrry.common.domain.User;
import cn.jrry.sample.service.UserService;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:applicationContext-test.xml"})
public class UserServiceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);
    private static final String USERNAME = "admin";


    @Autowired
    private UserService userService;

    @Test
    public void testInsert() throws Exception {
        User record = new User();
        String username = "alex";// RandomStringUtils.randomAlphanumeric(12);
        record.setUsername(username);
        record.setViewname(StringUtils.capitalize(username));

        String password = "123456";
        String md5pwd = Hashing.md5().hashString(username.concat(":").concat(password), Charsets.UTF_8).toString();
        String salt = RandomStringUtils.randomAlphanumeric(8);
        SecretKey key = new SecretKeySpec(salt.getBytes(Charsets.UTF_8), "HmacSHA512");
        String pwd = Hashing.hmacSha512(key).hashBytes(md5pwd.getBytes(Charsets.UTF_8)).toString();

        record.setPassword(pwd);
        record.setPasswordSalt(salt);
        record.setEmail(username.concat("@gmail.com"));
        record.setCruser(USERNAME);

        userService.insert(record);

    }

    @Test
    public void deleteByPrimaryKey() throws Exception {
        Long id = 7L;
        userService.deleteByPrimaryKey(id);
    }

    @Test
    public void removeByPrimaryKey() throws Exception {
        User record = new User();

        record.setId(7L);
        record.setMduser(USERNAME);
        userService.removeByPrimaryKey(record);
    }

    @Test
    public void updateByPrimaryKey() throws Exception {
        User record = userService.selectByPrimaryKey(9L);
        record.setMduser(USERNAME);

        record.setViewname(RandomStringUtils.randomAlphanumeric(8));

        userService.updateByPrimaryKey(record);
    }

    @Test
    public void selectByPrimaryKey() throws Exception {
        User record = userService.selectByPrimaryKey(9L);
        logger.info("username:{}", record.getUsername());
    }

    @Test
    public void count() throws Exception {
        User record = new User();
        int count = userService.count(record);
        logger.info("count:{}", count);
    }

    @Test
    public void select() throws Exception {
        User record = new User();
        List<User> list = userService.select(record, 0, 10);
        for (User user : list
                ) {
            logger.info("{}", user.toString());
        }
    }
}
