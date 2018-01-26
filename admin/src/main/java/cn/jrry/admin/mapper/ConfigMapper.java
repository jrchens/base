package cn.jrry.admin.mapper;

import cn.jrry.admin.domain.Config;

import java.util.List;
import java.util.Map;

public interface ConfigMapper {
    int deleteByPrimaryKey(String cfgCode); // @Param(value = "cfgCode")

    int insert(Config record);

    Config selectByPrimaryKey(String cfgCode);

    List<Config> selectAll();

    int updateByPrimaryKey(Config record);
    int removeByPrimaryKey(Config record);
    int count(Map<String,Object> record);
    List<Config> select(Map<String,Object> record);
}