package cn.jrry.sample.service;

import cn.jrry.sample.pojo.Sample;

import java.util.List;
import java.util.Map;

public interface SampleService {
    int insert(Sample record);

    int deleteByPrimaryKey(Long id);

    int removeByPrimaryKey(Sample record);

    int updateByPrimaryKey(Sample record);

    Sample selectByPrimaryKey(Long id);

    int count(Map<String, String> record);

    List<Sample> select(Map<String, String> record);

}