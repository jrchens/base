package cn.jrry.gen.mapper;

import cn.jrry.gen.domain.SampleDetail;
import java.util.List;

public interface SampleDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SampleDetail record);

    SampleDetail selectByPrimaryKey(Long id);

    List<SampleDetail> selectAll();

    int updateByPrimaryKey(SampleDetail record);
}