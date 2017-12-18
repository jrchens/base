package service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserServiceTest {

//    @Autowired
//    private UserService userService;
//    private JdbcTemplate jdbcTemplate;
//    private final static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
//
//    @Autowired
//    public void setDataSource(DataSource dataSource) {
//	this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }
//
//    @Test
//    public void resetUserPassword() {
//	String password = "123456";
//	int affected = jdbcTemplate.update("update sys_user set password = ? where deleted = 0", password);
//	logger.info("affect sys_user rows: {}", affected);
//
//	Pagination<User> pagination = new Pagination<User>();
//	pagination.setPageSize(1000);
//	List<User> userList = userService.queryUser(new User(), pagination);
//
//	for (User user : userList) {
//	    SecretKey key = new SecretKeySpec(user.getUsername().getBytes(Charsets.UTF_8), "HmacSHA512");
//	    String cipherText = Hashing.hmacSha512(key).hashBytes(password.getBytes(Charsets.UTF_8)).toString();
//	    jdbcTemplate.update("update sys_user set password = ? where username = ?", cipherText, user.getUsername());
//	}
//    }
//
//
//
//    @Test
//    public void testResetUserPassword() {
//
//	Pagination<User> pagination = new Pagination<User>();
//	pagination.setPageSize(1);
//	List<User> userList = userService.queryUser(new User(), pagination);
//
//	for (User user : userList) {
//	    String password = userService.resetUserPassword(user.getUsername());
//	    logger.info("username: {}, password: {}",user.getUsername(),password);
//	}
//    }
}
