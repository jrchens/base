package cn.jrry.gen.mapper;

import cn.jrry.gen.domain.SimpleTab;
import java.util.List;

public interface SimpleTabMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SimpleTab record);

    SimpleTab selectByPrimaryKey(Long id);

    List<SimpleTab> selectAll();

    int updateByPrimaryKey(SimpleTab record);
}