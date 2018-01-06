package cn.jrry.sample.service.impl;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(value = {"classpath:applicationContext-test.xml"})
public class UserServiceImplTest {
//    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);
//    private static final String USERNAME = "admin";

//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    public void testInsert() throws Exception {
//        User record = new User();
//        String username = "alex";// RandomStringUtils.randomAlphanumeric(12);
//        record.setUsername(username);
//        record.setViewname(StringUtils.capitalize(username));
//
//        String password = "123456";
//        String md5pwd = Hashing.md5().hashString(username.concat(":").concat(password), Charsets.UTF_8).toString();
//        String salt = RandomStringUtils.randomAlphanumeric(8);
//        SecretKey key = new SecretKeySpec(salt.getBytes(Charsets.UTF_8), "HmacSHA512");
//        String pwd = Hashing.hmacSha512(key).hashBytes(md5pwd.getBytes(Charsets.UTF_8)).toString();
//
//        record.setPassword(pwd);
//        record.setPasswordSalt(salt);
//        record.setEmail(username.concat("@gmail.com"));
//        record.setCruser(USERNAME);
//
//        userService.insert(record);
//
//    }
//
//    @Test
//    public void deleteByPrimaryKey() throws Exception {
//        Long id = 7L;
//        userService.deleteByPrimaryKey(id);
//    }
//
//    @Test
//    public void removeByPrimaryKey() throws Exception {
//        User record = new User();
//
//        record.setId(7L);
//        record.setMduser(USERNAME);
//        userService.removeByPrimaryKey(record);
//    }
//
//    @Test
//    public void updateByPrimaryKey() throws Exception {
//        User record = userService.selectByPrimaryKey(9L);
//        record.setMduser(USERNAME);
//
//        record.setViewname(RandomStringUtils.randomAlphanumeric(8));
//
//        userService.updateByPrimaryKey(record);
//    }
//
//    @Test
//    public void selectByPrimaryKey() throws Exception {
//        User record = userService.selectByPrimaryKey(9L);
//        logger.info("username:{}", record.getUsername());
//    }
//
//    @Test
//    public void count() throws Exception {
//        Map<String,Object> record = Maps.newHashMap();
//        int count = userService.count(record);
//        logger.info("count:{}", count);
//    }
//
//    @Test
//    public void select() throws Exception {
//        Map<String,Object> record = Maps.newHashMap();
//        record.put("offset",0); // required
//        record.put("limit",10); // required
//
//        record.put("username","");
//        record.put("viewname","");
//        record.put("email","");
//        record.put("disabled",0);
//        record.put("locked",0);
//
//        List<User> list = userService.select(record);
//        for (User user : list
//                ) {
//            logger.info("{}", user.toString());
//        }
//    }
}
