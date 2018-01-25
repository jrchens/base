package cn.jrry.wx.controller;

import cn.jrry.cms.domain.Category;
import cn.jrry.cms.domain.TreeNode;
import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.*;
import cn.jrry.wx.domain.WxMenu;
import cn.jrry.wx.service.WxInvokeService;
import cn.jrry.wx.service.WxMenuService;
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

@Controller(value = "wxMenuController")
@RequestMapping(path = "wx/menu")
public class WxMenuController {
    private static final Logger logger = LoggerFactory.getLogger(WxMenuController.class);
    private static final String CONTROLLER_CLASS_NAME = WxMenuController.class.getName();

    @Autowired
    private WxMenuService wxMenuService;
    @Autowired
    private WxInvokeService wxInvokeService;

    @RequestMapping(path = {"index"}, method = RequestMethod.GET)
    public String index(@Validated WxMenu record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record);
        try {
            if (record == null) {
                record = new WxMenu();
            }
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "wx/menu/index";
    }

//    @RequestMapping(path = {"async-query"}, method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> asyncQuery(@RequestParam(required = false) Map<String, Object> record) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-query", record);
//        Map<String, Object> result = Maps.newLinkedHashMap();
//        List<WxMenu> rows = Lists.newArrayList();
//        Map<String, Object> data = Maps.newLinkedHashMap();
//        try {
//
//            int total = wxMenuService.count(record);
//            if (total > 0) {
//                rows = wxMenuService.select(record);
//            }
//            data.put("total", total);
//            data.put("rows", rows);
//
//            result.put("success", true);
//            result.put("message", "");
//            result.put("data", data);
//
//        } catch (Exception ex) {
//            data.put("total", 0);
//            data.put("rows", rows);
//
//            result.put("success", false);
//            result.put("message", ExceptionUtils.getSimpleMessage(ex));
//            result.put("data", data);
//        }
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-query");
//        return result;
//    }


    @RequestMapping(path = {"async-tree-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncTreeQuery(@RequestParam(required = false, defaultValue = "0") Long id) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-tree-query", id);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<TreeNode> data = Lists.newArrayList();
        try {

            result.put("success", true);
            result.put("message", "");
            result.put("data", wxMenuService.selectAsTree(id));

        } catch (Exception ex) {

            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", data);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-tree-query");
        return result;
    }

    @RequestMapping(path = "async-save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> asyncSave(@Validated(value = Save.class) WxMenu record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-save", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            wxMenuService.insert(record);
            result.put("success", true);
            result.put("message", "");
            result.put("data", record.getId());
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-save");
        return result;
    }

    @RequestMapping(path = "async-update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> asyncUpdate(@Validated(value = Update.class) WxMenu record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-update", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            int aff = wxMenuService.updateByPrimaryKey(record);
            result.put("success", true);
            result.put("message", "");
            result.put("data", aff);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-update");
        return result;
    }

    @RequestMapping(path = "async-detail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncDetail(@Validated(value = Detail.class) WxMenu record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-detail", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            WxMenu data = wxMenuService.selectByPrimaryKey(record.getId());
            result.put("success", true);
            result.put("message", "");
            result.put("data", data);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", new Object());
        }

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-detail");
        return result;
    }

    @RequestMapping(path = "async-delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> asyncDelete(@Validated(value = Remove.class) WxMenu record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-delete", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = wxMenuService.deleteByPrimaryKey(record.getId());
            result.put("success", true);
            result.put("message", "");
            result.put("data", aff);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-delete");
        return result;
    }


    @RequestMapping(path = "async-publish", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> asyncPublish(@Validated(value = Remove.class) WxMenu record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-publish", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = wxMenuService.publish(record.getId());
            result.put("success", true);
            result.put("message", "");
            result.put("data", aff);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-publish");
        return result;
    }



    @RequestMapping(path = "async-download", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncDownload() {
        logger.info("--> {}.{}", CONTROLLER_CLASS_NAME, "async-download");
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            result.put("success", true);
            result.put("message", "");
            result.put("data", wxInvokeService.getMenu());
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-download");
        return result;
    }
}
