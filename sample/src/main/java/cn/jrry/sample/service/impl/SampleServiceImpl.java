package cn.jrry.sample.service.impl;

import cn.jrry.common.exception.ServiceException;
import cn.jrry.sample.mapper.SampleMapper;
import cn.jrry.sample.pojo.Sample;
import cn.jrry.sample.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class SampleServiceImpl implements SampleService {

    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

    @Autowired
    private SampleMapper sampleMapper;

    @Override
    public int insert(Sample record) {
        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCrtime(now);
            record.setDeleted(Boolean.FALSE);
            return sampleMapper.insert(record);
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return sampleMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int removeByPrimaryKey(Sample record) {
        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMdtime(now);
            return sampleMapper.removeByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("removeByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(Sample record) {
        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMdtime(now);
            return sampleMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public Sample selectByPrimaryKey(Long id) {
        try {
            return sampleMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, String> record) {
        try {
            return sampleMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Sample> select(Map<String, String> record) {
        try {
            int page = Integer.parseInt(record.get("page"));
            int rows = Integer.parseInt(record.get("rows"));
            int offset = (page - 1) * rows;
            record.put("offset", String.valueOf(offset));
            return sampleMapper.query(record);
        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }
}
