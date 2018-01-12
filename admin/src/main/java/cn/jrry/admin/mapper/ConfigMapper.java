package cn.jrry.admin.mapper;

import cn.jrry.admin.domain.Config;
import java.util.List;

public interface ConfigMapper {
    int deleteByPrimaryKey(String cfgCode);

    int insert(Config record);

    Config selectByPrimaryKey(String cfgCode);

    List<Config> selectAll();

    int updateByPrimaryKey(Config record);
    int removeByPrimaryKey(Config record);
}