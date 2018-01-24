package cn.jrry.admin.service;

import cn.jrry.admin.domain.Config;

import java.util.List;
import java.util.Map;

public interface ConfigService {
    int deleteByPrimaryKey(String cfgCode);

    int insert(Config record);

    Config selectByPrimaryKey(String cfgCode);
    String getString(String cfgCode);
    Integer getInteger(String cfgCode);
    Long getLong(String cfgCode);

    List<Config> selectAll();

    int updateByPrimaryKey(Config record);
    int removeByPrimaryKey(Config record);
    int count(Map<String,Object> record);
    List<Config> select(Map<String,Object> record);

}
