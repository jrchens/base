package cn.jrry.sample.mapper;

import cn.jrry.sample.pojo.SampleFooBarRelation;

import java.util.List;

public interface SampleFooBarRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SampleFooBarRelation record);

    SampleFooBarRelation selectByPrimaryKey(Long id);

    List<SampleFooBarRelation> selectAll();

    int updateByPrimaryKey(SampleFooBarRelation record);
}