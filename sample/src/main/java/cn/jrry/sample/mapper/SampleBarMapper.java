package cn.jrry.sample.mapper;

import cn.jrry.sample.pojo.SampleBar;
import java.util.List;

public interface SampleBarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SampleBar record);

    SampleBar selectByPrimaryKey(Long id);

    List<SampleBar> selectAll();

    int updateByPrimaryKey(SampleBar record);
}