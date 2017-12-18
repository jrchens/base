package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:applicationContext.xml"})
public class SysUserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceTest.class);
//    @Autowired
//    private SysUserService sysUserService;

//    @Test
//    public void testDeleteByPrimaryKey() {
//        Integer id = 1501;
//        sysUserService.deleteByPrimaryKey(id);
//    }

//    public String getSalt(int saltLength) {

//        Random random = new Random();
//        String saltSource = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";
//        if (saltLength > saltSource.length() || saltLength <= 0) {
//            saltLength = 16;
//        }
//        // int saltLength = 12;
//        StringBuffer rs = new StringBuffer();
//        for (int j = 0; j < saltLength; j++) {
//            rs.append(saltSource.charAt(random.nextInt(saltSource.length())));
//        }
//        String salt = rs.toString();
//
//        return salt;
//    }

    @Test
    public void testInsert() {
//        String password = "123456";
//        String username = "jrchens";
//        // md5(jrchensh@123456)
//        password = Hashing.md5().hashBytes(username.concat("@").concat(password).getBytes()).toString();
//
//        String salt = getSalt(16);
//        logger.info("test insert sys user");
//        SysUser record = new SysUser();
//        record.setEmail(username.concat("@hotmail.com"));
//        record.setUsername(username);
//        record.setViewname(username.toUpperCase());
//        record.setPassword(Hashing.hmacSha256(salt.getBytes()).hashBytes(password.getBytes()).toString());
//        record.setPasswordSalt(salt);
//        record.setGroupname("admin");
//        record.setState(1);
//        record.setDeleted(false);
//        record.setDisabled(false);
//        record.setCrtime(DateTime.now().toDate());
//        record.setCruser("jrchens");
//
//        int aff = sysUserService.insert(record);
//        logger.info("aff: {}", aff);
    }

    @Test
    public void testSelectByPrimaryKey() {
//        Integer id = 1;
//        SysUser record = sysUserService.selectByPrimaryKey(id);
//        logger.info("record: {}", record);
    }

    @Test
    public void testSelectAll() {
//        List<SysUser> list = sysUserService.selectAll();
//        for (SysUser record : list) {
//
//            String salt = getSalt(16);
//            String username = record.getUsername();
//            String password = "12345678";
//
//            String md5 = Hashing.md5().hashBytes(username.concat("@").concat(password).getBytes()).toString();
//            String pass = Hashing.hmacSha256(salt.getBytes()).hashBytes(md5.getBytes()).toString();
//
//            record.setPasswordSalt(salt);
//            record.setPassword(pass);
//
//            record.setMduser("jrchens");
//            record.setMdtime(DateTime.now().toDate());
//
//            sysUserService.updateByPrimaryKey(record);
//
//
//            logger.info("record: {}", record);
//    }

    }

    @Test
    public void testUpdateByPrimaryKey() {
//        Integer id = 1;
//        SysUser record = sysUserService.selectByPrimaryKey(id);
//        record.setDeleted(true);
//        record.setMduser("jrchens");
//        record.setMdtime(DateTime.now().toDate());
//        int aff = sysUserService.updateByPrimaryKey(record);
//        logger.info("aff: {}", aff);
    }

    @Test
    public void testQuery() {
//        int pageNum = 1, pageSize = 10;
//        SysUser query = new SysUser();
//        query.setViewname("");
//
//        PageInfo<SysUser> pi = sysUserService.query(query, pageNum, pageSize);
//
//        logger.info("pi: {}", pi);
//
//        List<SysUser> list = pi.getList();
//        for (SysUser record : list) {
//            logger.info("record: {}", record);
//        }
    }
}
