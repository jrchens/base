package cn.jrry.admin.service;

import cn.jrry.admin.domain.Config;

import java.util.List;

public interface ConfigService {
    int deleteByPrimaryKey(String cfgCode);

    int insert(Config record);

    Config selectByPrimaryKey(String cfgCode);

    List<Config> selectAll();

    int updateByPrimaryKey(Config record);
    int removeByPrimaryKey(Config record);

}
