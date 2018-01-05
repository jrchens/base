package cn.jrry.sample.service.impl;

import cn.jrry.sample.mapper.SampleMapper;
import cn.jrry.sample.pojo.Sample;
import cn.jrry.sample.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleMapper sampleMapper;

    @Override
    public int insert(Sample record) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        record.setCrtime(now);
        if (record.getBtinyint() == null) {
            record.setBtinyint(Boolean.FALSE);
        }
        record.setDeleted(Boolean.FALSE);
        return sampleMapper.insert(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return sampleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int removeByPrimaryKey(Sample record) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        record.setMdtime(now);
        return sampleMapper.removeByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKey(Sample record) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        record.setMdtime(now);
        if (record.getBtinyint() == null) {
            record.setBtinyint(Boolean.FALSE);
        }

        return sampleMapper.updateByPrimaryKey(record);
    }

    @Override
    public Sample selectByPrimaryKey(Long id) {
        return sampleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int count(Map<String, String> record) {
        return sampleMapper.count(record);
    }

    @Override
    public List<Sample> select(Map<String, String> record) {
        int page = Integer.parseInt(record.get("page"));
        int rows = Integer.parseInt(record.get("rows"));
        int offset = (page - 1) * rows;
        record.put("offset", String.valueOf(offset));

        return sampleMapper.query(record);
    }
}
