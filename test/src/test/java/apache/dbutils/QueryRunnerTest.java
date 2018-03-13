package apache.dbutils;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.dbutils.BaseResultSetHandler;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryRunnerTest {
    private static final Logger logger = LoggerFactory.getLogger(QueryRunnerTest.class);

    private MysqlDataSource dataSource;

    @Before
    public void init() throws Exception {
        dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUser("develop");
        dataSource.setPassword("develop");
        // MysqlConnectionPoolDataSource
    }

    @Test
    public void query4MapListHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        List<Map<String, Object>> list = run.query("show variables", new MapListHandler());
        for (Map<String, Object> map : list) {
            logger.info("{}", map);
        }
    }

    @Test
    public void query4ColumnListHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        // new ColumnListHandler<String>(1)
        List<String> list = run.query("show variables", new ColumnListHandler<String>("Value"));
        for (String vn : list) {
            logger.info("{}", vn);
        }
    }

    @Test
    public void query4ArrayListHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        List<Object[]> list = run.query("show variables", new ArrayListHandler());
        for (Object[] vn : list) {
            List<Object> row = Lists.newArrayList();
            for (Object obj : vn) {
                row.add(obj);
            }
            logger.info("{}", row);
        }
    }

    @Test
    public void query4KeyedHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        Map<String, Map<String, Object>> maps = run.query("show variables", new KeyedHandler<String>());
        logger.info("{}", maps);
    }

    @Test
    public void query4BeanMapHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        Map<String, Variables> maps = run.query("show variables",
                new BeanMapHandler<String, Variables>(Variables.class, 1));
        logger.info("{}", maps);
    }

    @Test
    public void query4ArrayHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        Object[] maps = run.query("show variables like 'innodb_max_purge_lag'", new ArrayHandler());
        List<Object> row = Lists.newArrayList();
        for (Object obj : maps) {
            row.add(obj);
        }
        logger.info("{}", row);
    }

    @Test
    public void query4VariablesBaseResultSetHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        Variables var = run.query("show variables like 'innodb_max_purge_lag'", new VariablesBaseResultSetHandler());
        logger.info("{}", var);
    }

    @Test
    public void query4VariablesListBaseResultSetHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        List<Variables> list = run.query("show variables like 'char%'",
                new VariablesListBaseResultSetHandler());
        for (Variables var : list) {
            logger.info("{}", var);
        }
    }

    @Test
    public void query4BeanHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        Variables var = run.query("show variables like 'innodb_max_purge_lag'",
                new BeanHandler<Variables>(Variables.class));
        logger.info("{}", var);
    }

    @Test
    public void query4BeanListHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        List<Variables> variables = run.query("show variables like 'character%'",
                new BeanListHandler<Variables>(Variables.class));
        for (Variables var : variables) {
            logger.info("{}", var);
        }
    }

    @Test
    public void query4MapHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        Map<String, Object> var = run.query("show variables like 'innodb_max_purge_lag'", new MapHandler());
        logger.info("{}", var);
    }

    @Test
    public void query4ScalarHandler() throws Exception {
        QueryRunner run = new QueryRunner(dataSource);
        // new ScalarHandler<String>(2)
        String var = run.query("show variables like 'innodb_max_purge_lag'",
                new ScalarHandler<String>("Variable_name"));
        logger.info("{}", var);
    }

    @Test
    public void update() throws Exception {
        QueryRunner run = new QueryRunner();

        Gson gson = new Gson();
        File dir = new File("/Users/sheng/Documents/tmp/wx_user_info");
        File[] files = dir.listFiles();
        Object[][] params = new Object[14][];

        for (File file : files
                ) {
            String json = Files.toString(file, Charsets.UTF_8);
            // logger.info("json:{}", json);

            Map<String, Object> row = gson.fromJson(json, new TypeToken<Map<String, Object>>() {

            }.getType());

            Connection conn = dataSource.getConnection();
            int res = run.update(conn,"insert into wx_user_info  (openid,subscribe,nickname,sex,province,city,country,headimgurl,unionid,remark,subscribe_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    row.get("openid"),
                    row.get("subscribe"),
                    row.get("nickname"),
                    row.get("sex"),
                    row.get("province"),
                    row.get("city"),
                    row.get("country"),
                    row.get("headimgurl"),
                    row.get("unionid"),
                    row.get("remark"),
                    row.get("subscribe_time") );

            logger.info("res:{}",res);
            DbUtils.close(conn);

            }


//        run.batch("insert into wx_user_info  (openid,subscribe,nickname,sex,province,city,country,headimgurl,unionid,remark,subscribe_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
//               params );


    }

    // TODO insert update batch
}

class VariablesBaseResultSetHandler extends BaseResultSetHandler<Variables> {

    @Override
    protected Variables handle() throws SQLException {
        Variables var = new Variables();
        if (next()) {
            var.setVariable_name(getString("Variable_name"));
            var.setValue(getString("Value"));
        }
        return var;
    }
}

class VariablesListBaseResultSetHandler extends BaseResultSetHandler<List<Variables>> {

    @Override
    protected List<Variables> handle() throws SQLException {
        List<Variables> list = new ArrayList<Variables>();
        while (next()) {
            Variables var = new Variables();
            var.setVariable_name(getString("Variable_name"));
            var.setValue(getString("Value"));
            list.add(var);
        }
        return list;
    }
}

class Variables implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1858170329561848704L;
    private String variable_name;
    private String value;

    public String getVariable_name() {
        return variable_name;
    }

    public void setVariable_name(String variable_name) {
        this.variable_name = variable_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Variables [variable_name=" + variable_name + ", value=" + value + "]";
    }
}
