package jdbc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTest {
    private static final Logger logger = LoggerFactory.getLogger(JdbcTest.class);

    @Test
    public void testConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn =
                    DriverManager.getConnection("jdbc:mysql://192.168.201.110:3306/test?" +
                            "user=develop&password=develop");
            logger.info("conn is null : {}", conn == null);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
