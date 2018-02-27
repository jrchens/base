package cn.jrry.wx.mapper;

import cn.jrry.wx.domain.WxJsapiTicket;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface WxJsapiTicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxJsapiTicket record);

    WxJsapiTicket selectByPrimaryKey(Integer id);

    List<WxJsapiTicket> selectAll();

    int updateByPrimaryKey(WxJsapiTicket record);

    List<WxJsapiTicket> selectNeedRefresh(@Param(value = "expires_time") Date expires_time);
    String getJsapiTicket();
}