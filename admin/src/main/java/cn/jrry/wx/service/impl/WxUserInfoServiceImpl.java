package cn.jrry.wx.service.impl;

import cn.jrry.common.exception.ServiceException;
import cn.jrry.common.exception.WxInvokeException;
import cn.jrry.wx.domain.WxResponse;
import cn.jrry.wx.domain.WxUserInfo;
import cn.jrry.wx.domain.WxUserInfoTagRelation;
import cn.jrry.wx.mapper.WxUserInfoMapper;
import cn.jrry.wx.service.WxInvokeService;
import cn.jrry.wx.service.WxUserInfoService;
import cn.jrry.wx.service.WxUserInfoTagRelationService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class WxUserInfoServiceImpl implements WxUserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(WxUserInfoService.class);

    @Autowired
    private WxUserInfoMapper wxUserInfoMapper;
    @Autowired
    private WxUserInfoTagRelationService wxUserInfoTagRelationService;
    @Autowired
    private WxInvokeService wxInvokeService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return wxUserInfoMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(WxUserInfo record) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCruser(record.getOpenid());
            record.setCrtime(now);

            int aff = wxUserInfoMapper.insert(record);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public WxUserInfo selectByPrimaryKey(Long id) {
        try {
            return wxUserInfoMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxUserInfo> selectAll() {
        try {
            return wxUserInfoMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(WxUserInfo record) {
        try {
            String openid = record.getOpenid();
            String user = openid;
            Subject subject = SecurityUtils.getSubject();
            if (null != subject && null != subject.getPrincipal()) {
                user = subject.getPrincipal().toString();
            }
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);


            List<Long> tag_list = Lists.newArrayList();
            List<WxUserInfoTagRelation> wxUserInfoTagRelationList = wxUserInfoTagRelationService.selectByOpenid(openid);

            Map<String, Object> params = Maps.newHashMap();
            params.put("openid_list", new String[]{openid});

            WxResponse wxResponse = null;

            for (WxUserInfoTagRelation wxUserInfoTagRelation : wxUserInfoTagRelationList
                    ) {
                params.put("tagid", wxUserInfoTagRelation.getTag_id());
                wxResponse = wxInvokeService.deleteUserInfoTag(params);
                if (wxResponse.getErrcode() == 0) {
                    wxUserInfoTagRelationService.deleteByRelationIds(openid, wxUserInfoTagRelation.getTag_id());
                } else {
                    throw new WxInvokeException(String.valueOf(wxResponse.getErrcode()));
                }
            }

            List<Long> tagidList = record.getTagid_list();
            if (null != tagidList && tagidList.size() > 0) {
                for (Long tag_id : tagidList
                        ) {
                    params.put("tagid", tag_id);
                    wxResponse = wxInvokeService.insertUserInfoTag(params);
                    if (wxResponse.getErrcode() == 0) {
                        WxUserInfoTagRelation wxUserInfoTagRelation = new WxUserInfoTagRelation();
                        wxUserInfoTagRelation.setOpenid(openid);
                        wxUserInfoTagRelation.setTag_id(tag_id);
                        wxUserInfoTagRelationService.insert(wxUserInfoTagRelation);
                    } else {
                        throw new WxInvokeException(String.valueOf(wxResponse.getErrcode()));
                    }
                }
            }

            return wxUserInfoMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return wxUserInfoMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxUserInfo> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return wxUserInfoMapper.select(record);

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public WxUserInfo selectByOpenid(String openid) {
        try {
            return wxUserInfoMapper.selectByOpenid(openid);
        } catch (Exception ex) {
            logger.error("selectByOpenid error {}{}{}", openid, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

}
