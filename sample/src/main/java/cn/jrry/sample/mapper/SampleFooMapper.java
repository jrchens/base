package cn.jrry.sample.mapper;

import cn.jrry.sample.pojo.SampleFoo;

import java.util.List;

public interface SampleFooMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SampleFoo record);

    SampleFoo selectByPrimaryKey(Long id);

    List<SampleFoo> selectAll();

    int updateByPrimaryKey(SampleFoo record);
}