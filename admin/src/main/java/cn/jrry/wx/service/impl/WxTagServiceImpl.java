package cn.jrry.wx.service.impl;

import cn.jrry.common.exception.ServiceException;
import cn.jrry.common.exception.WxInvokeException;
import cn.jrry.wx.domain.WxResponse;
import cn.jrry.wx.domain.WxTag;
import cn.jrry.wx.mapper.WxTagMapper;
import cn.jrry.wx.service.WxInvokeService;
import cn.jrry.wx.service.WxTagService;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class WxTagServiceImpl implements WxTagService {
    private static final Logger logger = LoggerFactory.getLogger(WxTagService.class);
    private static final Gson gson = new Gson();

    @Autowired
    private WxTagMapper wxTagMapper;
    @Autowired
    private WxInvokeService wxInvokeService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            Map<String, Object> params = Maps.newLinkedHashMap();
            Map<String, Object> tag = Maps.newHashMap();
            tag.put("id", id);
            params.put("tag", tag);

            WxResponse result = wxInvokeService.deleteTag(params);

            if (result.getErrcode() == 0) {
                return wxTagMapper.deleteByPrimaryKey(id);
            } else {
                throw new WxInvokeException(String.valueOf(result.getErrcode()));
            }
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(WxTag record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCruser(user);
            record.setCrtime(now);

            Map<String, Object> params = Maps.newLinkedHashMap();
            Map<String, Object> tag = Maps.newHashMap();
            tag.put("name", record.getName());
            params.put("tag", tag);
            Long id = wxInvokeService.insertTag(params);
            record.setId(id);

            int aff = wxTagMapper.insert(record);
            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public WxTag selectByPrimaryKey(Long id) {
        try {
            return wxTagMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxTag> selectAll() {
        try {
            return wxTagMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(WxTag record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);

            Map<String, Object> params = Maps.newLinkedHashMap();
            Map<String, Object> tag = Maps.newHashMap();
            tag.put("id", record.getId());
            tag.put("name", record.getName());
            params.put("tag", tag);

            WxResponse result = wxInvokeService.updateTag(params);

            if (result.getErrcode() == 0) {
                return wxTagMapper.updateByPrimaryKey(record);
            } else {
                throw new WxInvokeException(String.valueOf(result.getErrcode()));
            }

        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return wxTagMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxTag> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return wxTagMapper.select(record);

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }
}
