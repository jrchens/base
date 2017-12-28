package cn.jrry.gen.mapper;

import cn.jrry.gen.domain.Tab;
import cn.jrry.gen.domain.TabExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabMapper {
    long countByExample(TabExample example);

    int deleteByExample(TabExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Tab record);

    int insertSelective(Tab record);

    List<Tab> selectByExampleWithBLOBs(TabExample example);

    List<Tab> selectByExample(TabExample example);

    Tab selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Tab record, @Param("example") TabExample example);

    int updateByExampleWithBLOBs(@Param("record") Tab record, @Param("example") TabExample example);

    int updateByExample(@Param("record") Tab record, @Param("example") TabExample example);

    int updateByPrimaryKeySelective(Tab record);

    int updateByPrimaryKeyWithBLOBs(Tab record);

    int updateByPrimaryKey(Tab record);
}