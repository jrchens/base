package cn.jrry.wx.service.impl;

import cn.jrry.cms.domain.TreeNode;
import cn.jrry.common.exception.ServiceException;
import cn.jrry.wx.domain.WxMenu;
import cn.jrry.wx.domain.WxMenuMatchRule;
import cn.jrry.wx.domain.WxResponse;
import cn.jrry.wx.mapper.WxMenuMapper;
import cn.jrry.wx.mapper.WxMenuMatchRuleMapper;
import cn.jrry.wx.service.WxInvokeService;
import cn.jrry.wx.service.WxMenuService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class WxMenuServiceImpl implements WxMenuService {
    private static final Logger logger = LoggerFactory.getLogger(WxMenuService.class);
    private static final Gson gson = new Gson();

    @Autowired
    private WxMenuMapper wxMenuMapper;
    @Autowired
    private WxMenuMatchRuleMapper wxMenuMatchRuleMapper;
    @Autowired
    private WxInvokeService wxInvokeService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
//            Map<String, Object> params = Maps.newLinkedHashMap();
//            Map<String, Object> tag = Maps.newHashMap();
//            tag.put("id", id);
//            params.put("tag", tag);
//
//            WxResponse result = wxInvokeService.deleteTag(params);
//
//            if (result.getErrcode() == 0) {

            wxMenuMatchRuleMapper.deleteByMenuid(id);

            return wxMenuMapper.deleteByPrimaryKey(id);
//            } else {
//                throw new WxInvokeException(String.valueOf(result.getErrcode()));
//            }
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(WxMenu record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCruser(user);
            record.setCrtime(now);

            Long parent_id = record.getParent_id();
            List<WxMenu> wxMenuList = selectByParentId(parent_id);
            Integer sort = wxMenuList == null ? 5 : (wxMenuList.size() * 5) + 5;
            record.setSort(sort);

            String node_key = record.getNode_key();
            String node_url = record.getNode_url();
            String node_media_id = record.getNode_media_id();
            String node_appid = record.getNode_appid();
            String node_pagepath = record.getNode_pagepath();
            String view_scope = record.getView_scope();
            String rich_content = record.getRich_content();
            String url_state = record.getUrl_state();

            if (!StringUtils.hasText(node_key)) {
                record.setNode_key(null);
            }
            if (!StringUtils.hasText(node_url)) {
                record.setNode_url(null);
            }
            if (!StringUtils.hasText(node_media_id)) {
                record.setNode_media_id(null);
            }
            if (!StringUtils.hasText(node_appid)) {
                record.setNode_appid(null);
            }
            if (!StringUtils.hasText(node_pagepath)) {
                record.setNode_pagepath(null);
            }
            if (!StringUtils.hasText(view_scope)) {
                record.setView_scope(null);
            }
            if (!StringUtils.hasText(rich_content)) {
                record.setRich_content(null);
            }
            if (!StringUtils.hasText(url_state)) {
                record.setUrl_state(null);
            }

//            Map<String, Object> params = Maps.newLinkedHashMap();
//            Map<String, Object> tag = Maps.newHashMap();
//            tag.put("name", record.getName());
//            params.put("tag", tag);
//            Long id = wxInvokeService.insertTag(params);
//            record.setId(id);

            int aff = wxMenuMapper.insert(record);

            WxMenuMatchRule wxMenuMatchRule = new WxMenuMatchRule();
            wxMenuMatchRule.setMenu_id(record.getId());
            wxMenuMatchRule.setCruser(user);
            wxMenuMatchRule.setCrtime(now);
            wxMenuMatchRuleMapper.insert(wxMenuMatchRule);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public WxMenu selectByPrimaryKey(Long id) {
        try {
            WxMenu wxMenu = wxMenuMapper.selectByPrimaryKey(id);
            WxMenuMatchRule wxMenuMatchRule = wxMenuMatchRuleMapper.selectByMenuid(id);
            wxMenu.setMatchRule(wxMenuMatchRule);
            return wxMenu;
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxMenu> selectAll() {
        try {
            return wxMenuMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(WxMenu record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);

            if(record.getParent_id() == 1 && record.getId().longValue() != 2){
                wxMenuMatchRuleMapper.deleteByMenuid(record.getId());
                WxMenuMatchRule matchRule = record.getMatchRule();
                matchRule.setMenu_id(record.getId());
                matchRule.setCruser(user);
                matchRule.setCrtime(now);
                wxMenuMatchRuleMapper.insert(matchRule);
            }


//            Map<String, Object> params = Maps.newLinkedHashMap();
//            Map<String, Object> tag = Maps.newHashMap();
//            tag.put("id", record.getId());
//            tag.put("name", record.getName());
//            params.put("tag", tag);
//
//            WxResponse result = wxInvokeService.updateTag(params);
//
//            if (result.getErrcode() == 0) {
            return wxMenuMapper.updateByPrimaryKey(record);
//            } else {
//                throw new WxInvokeException(String.valueOf(result.getErrcode()));
//            }

        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return wxMenuMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxMenu> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return wxMenuMapper.select(record);

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxMenu> selectByParentId(Long parentId) {
        try {
            return wxMenuMapper.selectByParentId(parentId);
        } catch (Exception ex) {
            logger.error("selectByParentId error {}{}{}", parentId, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    private void selectAsTree(List<TreeNode> treeNodeList) {
        for (TreeNode treeNode : treeNodeList
                ) {
            List<TreeNode> treeNodes = wxMenuMapper.selectAsTreeNode(treeNode.getId());
            if (treeNodes != null && treeNodes.size() > 0) {
                treeNode.setChildren(treeNodes);
                selectAsTree(treeNodes);
            }
        }
    }

    private List<Map<String, Object>> selectAsWxMenuTree(Long id) {

        List<WxMenu> wxMenuList = selectByParentId(id);
        List<Map<String, Object>> buttonList = Lists.newArrayList();

        for (WxMenu wxMenu : wxMenuList
                ) {
            Map<String, Object> button = Maps.newHashMap();
            button.put("type", wxMenu.getNode_type());
            button.put("name", wxMenu.getNode_name());
            button.put("key", wxMenu.getNode_key());
            button.put("url", wxInvokeService.getMenuUrl(wxMenu));
            button.put("media_id", wxMenu.getNode_media_id());
            button.put("appid", wxMenu.getNode_appid());
            button.put("pagepath", wxMenu.getNode_pagepath());

            List<WxMenu> wxMenuList2 = selectByParentId(wxMenu.getId());
            List<Map<String, Object>> subButtonList = Lists.newArrayList();
            for (WxMenu wxMenu2 : wxMenuList2
                    ) {
                Map<String, Object> sub_button = Maps.newHashMap();
                sub_button.put("type", wxMenu2.getNode_type());
                sub_button.put("name", wxMenu2.getNode_name());
                sub_button.put("key", wxMenu2.getNode_key());
                sub_button.put("url", wxInvokeService.getMenuUrl(wxMenu2));
                sub_button.put("media_id", wxMenu2.getNode_media_id());
                sub_button.put("appid", wxMenu2.getNode_appid());
                sub_button.put("pagepath", wxMenu2.getNode_pagepath());

                subButtonList.add(sub_button);
            }

            if (subButtonList.size() > 0) {
                button.put("sub_button", subButtonList);
            }

            buttonList.add(button);

        }

        return buttonList;


    }

    @Override
    public List<TreeNode> selectAsTree(Long parentId) {
        try {
            List<TreeNode> treeNodeList = wxMenuMapper.selectAsTreeNode(parentId);
            selectAsTree(treeNodeList);
            return treeNodeList;
        } catch (Exception ex) {
            logger.error("selectAsTree error {}{}{}", parentId, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }


    @Override
    public int publish(Long id) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());

            WxMenu root = selectByPrimaryKey(id);

            if (root.getCustom()) {
                // 个性化菜单
                Map<String, Object> params = Maps.newLinkedHashMap();
                Long menuid = root.getMenuid();
                if (menuid != null && menuid.longValue() > 0) {
                    params.put("menuid", menuid);
                    wxInvokeService.deleteConditionalMenu(params);
                    params.clear();
                }

                params.put("button", selectAsWxMenuTree(root.getId()));
                WxMenuMatchRule wxMenuMatchRule = wxMenuMatchRuleMapper.selectByMenuid(root.getId());
                wxMenuMatchRule.setId(null);
                wxMenuMatchRule.setMenu_id(null);
                params.put("matchrule", wxMenuMatchRule);
                menuid = wxInvokeService.createConditionalMenu(params);

                WxMenu wxMenu = new WxMenu();
                wxMenu.setMenuid(menuid);
                wxMenu.setMduser(user);
                wxMenu.setMdtime(now);
                wxMenu.setId(id);
                wxMenuMapper.updateMenuid(wxMenu);

            } else {
                WxResponse wxResponse = wxInvokeService.deleteMenu();
                if (wxResponse.getErrcode() == 0) {
//                button	是	一级菜单数组，个数应为1~3个
//                sub_button	否	二级菜单数组，个数应为1~5个
//                type	是	菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型
//                name	是	菜单标题，不超过16个字节，子菜单不超过60个字节
//                key	click等点击类型必须	菜单KEY值，用于消息接口推送，不超过128字节
//                url	view、miniprogram类型必须	网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为miniprogram时，不支持小程序的老版本客户端将打开本url。
//                media_id	media_id类型和view_limited类型必须	调用新增永久素材接口返回的合法media_id
//                appid	miniprogram类型必须	小程序的appid（仅认证公众号可配置）
//                pagepath	miniprogram类型必须	小程序的页面路径
                    Map<String, Object> params = Maps.newHashMap();
                    params.put("button", selectAsWxMenuTree(root.getId()));
                    wxInvokeService.createMenu(params);

                }
            }

        } catch (Exception ex) {
            logger.error("publish error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
        return 0;
    }
}
