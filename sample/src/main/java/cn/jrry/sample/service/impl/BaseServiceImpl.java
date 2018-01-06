package cn.jrry.sample.service.impl;

import cn.jrry.sample.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class BaseServiceImpl implements BaseService {

    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
    private static final String SERVICE_CLASS_NAME = BaseServiceImpl.class.getName();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public String getNow() throws RuntimeException {
        logger.info("--> {}.{}", SERVICE_CLASS_NAME, "now");
        String now = jdbcTemplate.queryForObject("select now()", String.class);
        logger.info("<-- {}.{}", SERVICE_CLASS_NAME, "now");
        return now;
    }
}
