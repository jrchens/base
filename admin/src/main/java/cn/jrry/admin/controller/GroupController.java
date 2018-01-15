package cn.jrry.admin.controller;

import cn.jrry.admin.domain.GroupDO;
import cn.jrry.admin.domain.GroupVO;
import cn.jrry.admin.domain.UserDO;
import cn.jrry.admin.domain.UserVO;
import cn.jrry.admin.service.GroupService;
import cn.jrry.admin.service.UserService;
import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtils;
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

@Controller
@RequestMapping(path = "admin/group")
public class GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    private static final String CONTROLLER_CLASS_NAME = GroupController.class.getName();

    @Autowired
    private GroupService groupService;

    @RequestMapping(path = {"index"}, method = RequestMethod.GET)
    public String index(@Validated GroupDO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record);
        try {
            if (record == null) {
                record = new GroupDO();
            }
            GroupVO voRecord = new GroupVO();
            PropertyUtils.copyProperties(voRecord, record);
            model.addAttribute(voRecord);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "admin/group/index";
    }

    @RequestMapping(path = {"query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<GroupVO> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {

            int total = groupService.count(record);
            if (total > 0) {
                rows = groupService.select(record);
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
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "query");
        return result;
    }

    @RequestMapping(path = "create", method = RequestMethod.GET)
    public String create(@Validated(value = Create.class) GroupDO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", record);
        try {
            if (record == null) {
                record = new GroupDO();
            }
            GroupVO voRecord = new GroupVO();
            PropertyUtils.copyProperties(voRecord, record);

            model.addAttribute(voRecord);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "create");
        return "admin/group/create";
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String save(@Validated(value = Save.class) GroupDO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "save", record);
        try {
            if (!bindingResult.hasErrors()) {
                groupService.insert(record);
            } else {
                return "admin/group/create";
            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "admin/group/create";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
    }


    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public String edit(@Validated(value = Edit.class) GroupDO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "edit", record);
        try {
            GroupVO voRecord = groupService.selectByPrimaryKey(record.getId());
            model.addAttribute(voRecord);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "edit");
        return "admin/group/edit";
    }

    @RequestMapping(path = "update", method = RequestMethod.POST)
    public String update(@Validated(value = Update.class) GroupDO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "update", record);
        try {
            if (!bindingResult.hasErrors()) {
                groupService.updateByPrimaryKey(record);
            } else {
                return "admin/group/edit";
            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "admin/group/edit";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "update");
        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public String detail(@Validated(value = Detail.class) GroupDO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "detail",record);
        try {
            model.addAttribute(groupService.selectByPrimaryKey(record.getId()));
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "detail");
        return "admin/group/detail";
    }

    @RequestMapping(path = "remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> remove(@Validated(value = Remove.class) GroupDO record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = groupService.removeByPrimaryKey(record);
            result.put("success", true);
            result.put("message", "");
            result.put("data", aff);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "remove");
        return result;
    }
}
