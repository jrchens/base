package spring;

import com.google.common.base.CaseFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class JdbcTemplateTest {

    private final static Logger logger = LoggerFactory.getLogger(JdbcTemplateTest.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testQueryForObject() {
        String uuid = jdbcTemplate.queryForObject("select uuid()", String.class);
        logger.debug("uuid: {}", uuid);
        Assert.isTrue(uuid.length() == 36);
    }

    @Test
    public void testShowVariables() {
        List<Map<String, Object>> variables = jdbcTemplate.queryForList("show variables like '%character%'");
        for (Map<String, Object> map : variables) {
//	    Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
//	    while(iterator.hasNext()){
//		Entry<String, Object> entry = iterator.next();
//		logger.info("{} : {}",entry.getKey(),entry.getValue());
//	    }
            logger.info("{} : {}", map.get("Variable_name"), map.get("Value"));
        }
    }


    @Test
    public void testWriteDomain() {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT  * FROM  column_type WHERE 1 = 2");
        SqlRowSetMetaData sqlRowSetMetaData = sqlRowSet.getMetaData();

        int columnCount = sqlRowSetMetaData.getColumnCount() + 1;
        for (int i = 1; i < columnCount; i++) {
            String columnName = sqlRowSetMetaData.getColumnName(i);
            String fieldName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,columnName);
            int columnType = sqlRowSetMetaData.getColumnType(i);

            String columnTypeName = sqlRowSetMetaData.getColumnTypeName(i);
            String columnClassName = sqlRowSetMetaData.getColumnClassName(i);

            int columnDisplaySize = sqlRowSetMetaData.getColumnDisplaySize(i);

            int precision = sqlRowSetMetaData.getPrecision(i);
            int scale = sqlRowSetMetaData.getScale(i);

            logger.info("{} {} {} {}", columnName, fieldName,columnTypeName, columnClassName);


            if (Types.BIGINT == columnType || Types.INTEGER == columnType || Types.DECIMAL == columnType || Types.DOUBLE == columnType || Types.DOUBLE == columnType) {
                // number
            } else if (Types.DATE == columnType || Types.TIME == columnType || Types.TIMESTAMP == columnType) {
                // date,time,timestamp
            } else {
                // String
            }

        }


    }
}
