package cn.jrry.sample.mapper;

import cn.jrry.sample.pojo.Sample;
import java.util.List;
import java.util.Map;

public interface SampleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Sample record);

    Sample selectByPrimaryKey(Long id);

    List<Sample> selectAll();

    int count(Map<String,String> record);
    List<Sample> query(Map<String,String> record);

    int updateByPrimaryKey(Sample record);
    int removeByPrimaryKey(Sample record);
}