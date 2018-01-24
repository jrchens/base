package cn.jrry.wx.service.impl;

import cn.jrry.common.exception.ServiceException;
import cn.jrry.wx.mapper.WxAccessTokenMapper;
import cn.jrry.wx.service.WxAccessTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxAccessTokenServiceImpl implements WxAccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(WxAccessTokenService.class);
    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;

    public String getAccessToken() {
        try {
            return wxAccessTokenMapper.getAccessToken();
        } catch (Exception ex) {
            logger.error("getAccessToken error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }
}
