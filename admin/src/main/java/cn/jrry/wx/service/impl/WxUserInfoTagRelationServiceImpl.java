package cn.jrry.wx.service.impl;

import cn.jrry.common.exception.ServiceException;
import cn.jrry.common.exception.WxInvokeException;
import cn.jrry.wx.domain.WxResponse;
import cn.jrry.wx.domain.WxUserInfoTagRelation;
import cn.jrry.wx.mapper.WxUserInfoTagRelationMapper;
import cn.jrry.wx.service.WxInvokeService;
import cn.jrry.wx.service.WxUserInfoTagRelationService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class WxUserInfoTagRelationServiceImpl implements WxUserInfoTagRelationService {
    private static final Logger logger = LoggerFactory.getLogger(WxUserInfoTagRelationService.class);
    private static final Gson gson = new Gson();

    @Autowired
    private WxUserInfoTagRelationMapper wxUserInfoTagRelationMapper;
    @Autowired
    private WxInvokeService wxInvokeService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            WxUserInfoTagRelation wxUserInfoTagRelation = selectByPrimaryKey(id);

            Map<String, Object> params = Maps.newLinkedHashMap();
            List<String> openidList = Lists.newArrayList();
            openidList.add(wxUserInfoTagRelation.getOpenid());
            params.put("openid_list", openidList);
            params.put("tagid", wxUserInfoTagRelation.getTag_id());

            WxResponse wxResponse = wxInvokeService.deleteUserInfoTag(params);

            if (wxResponse.getErrcode() == 0) {
                return wxUserInfoTagRelationMapper.deleteByPrimaryKey(id);
            } else {
                throw new WxInvokeException(String.valueOf(wxResponse.getErrcode()));
            }

        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(WxUserInfoTagRelation record) {
        try {
            Map<String, Object> params = Maps.newLinkedHashMap();
            List<String> openidList = Lists.newArrayList();
            openidList.add(record.getOpenid());
            params.put("openid_list", openidList);
            params.put("tagid", record.getTag_id());

            WxResponse wxResponse = wxInvokeService.insertUserInfoTag(params);
            if (wxResponse.getErrcode() == 0) {
                return wxUserInfoTagRelationMapper.insert(record);
            } else {
                throw new WxInvokeException(String.valueOf(wxResponse.getErrcode()));
            }
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }


    @Override
    public WxUserInfoTagRelation selectByPrimaryKey(Long id) {
        try {
            return wxUserInfoTagRelationMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxUserInfoTagRelation> selectAll() {
        try {
            return wxUserInfoTagRelationMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(WxUserInfoTagRelation record) {
        try {
            return wxUserInfoTagRelationMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return wxUserInfoTagRelationMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxUserInfoTagRelation> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return wxUserInfoTagRelationMapper.select(record);

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int deleteByOpenid(String openid) {
        try {
            return wxUserInfoTagRelationMapper.deleteByOpenid(openid);
        } catch (Exception ex) {
            logger.error("deleteByOpenid error {}{}{}", openid, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }
    @Override
    public int deleteByTagid(Long tag_id) {
        try {
            return wxUserInfoTagRelationMapper.deleteByTagid(tag_id);
        } catch (Exception ex) {
            logger.error("deleteByTagid error {}{}{}", tag_id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }
    @Override
    public int deleteByRelationIds(String openid,Long tag_id) {
        try {
            return wxUserInfoTagRelationMapper.deleteByRelationIds(openid,tag_id);
        } catch (Exception ex) {
            logger.error("deleteByRelationIds error openid:{}, tag_id:{}, {}{}", openid,tag_id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxUserInfoTagRelation> selectByOpenid(String openid) {
        try {
            return wxUserInfoTagRelationMapper.selectByOpenid(openid);
        } catch (Exception ex) {
            logger.error("selectByOpenid error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

}
