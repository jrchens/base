package cn.jrry.wx.controller;

import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.*;
import cn.jrry.wx.domain.WxTag;
import cn.jrry.wx.domain.WxUserInfo;
import cn.jrry.wx.domain.WxUserInfoTagRelation;
import cn.jrry.wx.service.WxTagService;
import cn.jrry.wx.service.WxUserInfoService;
import cn.jrry.wx.service.WxUserInfoTagRelationService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller(value = "wxUserInfoController")
@RequestMapping(path = "wx/user-info")
public class WxUserInfoController {
    private static final Logger logger = LoggerFactory.getLogger(WxUserInfoController.class);
    private static final String CONTROLLER_CLASS_NAME = WxUserInfoController.class.getName();

    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private WxUserInfoTagRelationService wxUserInfoTagRelationService;
    @Autowired
    private WxTagService wxTagService;

    @RequestMapping(path = {"index"}, method = RequestMethod.GET)
    public String index(@Validated WxUserInfo record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record);
        try {
            if (record == null) {
                record = new WxUserInfo();
            }
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "wx/user_info/index";
    }

    @RequestMapping(path = {"async-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncQuery(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<WxUserInfo> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            
            int total = wxUserInfoService.count(record);
            if (total > 0) {
                rows = wxUserInfoService.select(record);
            }
            data.put("total", total);
            data.put("rows", rows);

            result.put("success", true);
            result.put("message", "");
            result.put("data", data);

        } catch (Exception ex) {
            data.put("total", 0);
            data.put("rows", rows);

            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", data);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-query");
        return result;
    }

//    @RequestMapping(path = "create", method = RequestMethod.GET)
//    public String create(@Validated(value = Create.class) WxUserInfo record, BindingResult bindingResult, Model model) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", record);
//        try {
//            if (record == null) {
//                record = new WxUserInfo();
//            }
//            model.addAttribute(record);
//        } catch (Exception ex) {
//            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
//        }
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "create");
//        return "wx/user_info/create";
//    }

//    @RequestMapping(path = "save", method = RequestMethod.POST)
//    public String save(@Validated(value = Save.class) WxUserInfo record, BindingResult bindingResult, Model model) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "save", record);
//        try {
//            if (!bindingResult.hasErrors()) {
//                wxUserInfoService.insert(record);
//            } else {
//                return "wx/user_info/create";
//            }
//        } catch (Exception ex) {
//            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
//            return "wx/user_info/create";
//        }
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
//        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
//    }

    private void initTag(String openid,Model model){
        List<WxTag> wxTagList = wxTagService.selectAll();

        List<WxUserInfoTagRelation> wxUserInfoTagRelationList = wxUserInfoTagRelationService.selectByOpenid(openid);

        for (WxTag wxTag: wxTagList
                ) {
            Long id = wxTag.getId();
            wxTag.setChecked(Boolean.FALSE);
            for (WxUserInfoTagRelation wxUserInfoTagRelation: wxUserInfoTagRelationList
                    ) {
                Long tagid = wxUserInfoTagRelation.getTag_id();
                if(tagid.longValue() == id.longValue()){
                    wxTag.setChecked(Boolean.TRUE);
                    break;
                }
            }

        }
        model.addAttribute(wxTagList);
    }

    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public String edit(@Validated(value = Edit.class) WxUserInfo record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "edit", record);
        try {
            WxUserInfo wxUserInfo = wxUserInfoService.selectByPrimaryKey(record.getId());
            model.addAttribute(wxUserInfo);
            initTag(wxUserInfo.getOpenid(),model);

        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "edit");
        return "wx/user_info/edit";
    }

    @RequestMapping(path = "update", method = RequestMethod.POST)
    public String update(@Validated(value = Update.class) WxUserInfo record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "update", record);
        try {
            if (!bindingResult.hasErrors()) {
                wxUserInfoService.updateByPrimaryKey(record);
            } else {
                return "wx/user_info/edit";
            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "wx/user_info/edit";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "update");
        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public String detail(@Validated(value = Detail.class) WxUserInfo record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "detail", record);
        try {
            WxUserInfo wxUserInfo = wxUserInfoService.selectByPrimaryKey(record.getId());
            model.addAttribute(wxUserInfo);
            initTag(wxUserInfo.getOpenid(),model);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "detail");
        return "wx/user_info/detail";
    }

//    @RequestMapping(path = "async-delete", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> asyncQuery(@Validated(value = Remove.class) WxUserInfo record, BindingResult bindingResult) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-delete", record);
//        Map<String, Object> result = Maps.newLinkedHashMap();
//        Map<String, Object> data = Maps.newLinkedHashMap();
//        try {
//
//            int aff = wxUserInfoService.deleteByPrimaryKey(record.getId());
//            result.put("success", true);
//            result.put("message", "");
//            result.put("data", aff);
//        } catch (Exception ex) {
//            result.put("success", false);
//            result.put("message", ExceptionUtils.getSimpleMessage(ex));
//            result.put("data", 0);
//        }
//
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-delete");
//        return result;
//    }
}
