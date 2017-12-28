package domain;

import com.google.common.base.CaseFormat;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
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

import javax.sql.DataSource;
import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class GenMapperXmlTest {
    private final static Logger logger = LoggerFactory.getLogger(GenMapperXmlTest.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testGen() throws Exception {

        String packageName = "cn.jrry.sample";
        String tableName = "sys_user";
        String className = "User";

        List<Map<String, String>> fields = Lists.newArrayList();
        List<Map<String, String>> likes = Lists.newArrayList();
        List<Map<String, String>> equals = Lists.newArrayList();

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT  * FROM  "+tableName+" WHERE 1 = 2");
        SqlRowSetMetaData sqlRowSetMetaData = sqlRowSet.getMetaData();

//        DatabaseMetaDataCallback databaseMetaDataCallback;
//        TableMetaDataContext tableMetaDataContext;



        int columnCount = sqlRowSetMetaData.getColumnCount() + 1;
        for (int i = 1; i < columnCount; i++) {
            String columnName = sqlRowSetMetaData.getColumnName(i);
            String columnClassName = sqlRowSetMetaData.getColumnClassName(i);
            int columnDisplaySize = sqlRowSetMetaData.getColumnDisplaySize(i);
            int columnType = sqlRowSetMetaData.getColumnType(i);

            String columnTypeName = sqlRowSetMetaData.getColumnTypeName(i);

//            if (columnType == Types.TIMESTAMP) {
//                columnTypeName = "TIMESTAMP";
//            }
//            if (columnType == Types.BIT || (columnType == Types.TINYINT && columnDisplaySize == 1)) {
//                columnTypeName = "BIT";
//            }

//            String fieldName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,columnName);

//            if ("DATETIME".equals(columnTypeName)){
//                logger.info("columnName:{} , columnTypeName:{} , columnType:{}",columnName,columnTypeName,columnType);
//            }
//
//            int precision = sqlRowSetMetaData.getPrecision(i);
//            int scale = sqlRowSetMetaData.getScale(i);

            Map<String, String> field = Maps.newHashMap();
            field.put("className", columnClassName);
            field.put("columnName", columnName);
            field.put("columnTypeName", columnTypeName);
            field.put("name", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field.get("columnName")));
            fields.add(field);


//            if (Types.BIGINT == columnType || Types.INTEGER == columnType || Types.DECIMAL == columnType || Types.DOUBLE == columnType || Types.DOUBLE == columnType) {
//                // number
//            } else if (Types.DATE == columnType || Types.TIME == columnType || Types.TIMESTAMP == columnType) {
//                // date,time,timestamp
//            } else {
//                // String
//            }

        }



        VelocityEngine ve = new VelocityEngine();

        ve.setProperty("resource.loader", "class");
        ve.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        ve.init();

        VelocityContext context = new VelocityContext();
        context.put("packageName", packageName);
        context.put("tableName", tableName);
        context.put("className", className);
        context.put("fields", fields);
        context.put("likes", likes);
        context.put("equals", equals);

        StringWriter writer = new StringWriter();
        Template t = ve.getTemplate("vm/mapper-xml-test.vm");
        t.merge(context, writer);

//        System.out.println(writer.toString());

        Files.write(writer.toString(),new File("/Users/shengchen/Documents/Temporary/"+className+"Mapper.xml"), Charsets.UTF_8);
    }
}
