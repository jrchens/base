package cn.jrry.admin.controller;

import cn.jrry.admin.domain.Role;
import cn.jrry.admin.service.RoleService;
import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.*;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "admin/role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private static final String CONTROLLER_CLASS_NAME = RoleController.class.getName();

    @Autowired
    private RoleService roleService;

    @RequestMapping(path = {"index"}, method = RequestMethod.GET)
    public String index(@Validated Role record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record);
        try {
            if (record == null) {
                record = new Role();
            }
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "admin/role/index";
    }

    @RequestMapping(path = {"async-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncQuery(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<Role> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {

            String exclusiveRoleNames = ObjectUtils.getDisplayString(record.get("exclusiveRoleNames"));
            if(StringUtils.hasText(exclusiveRoleNames)){
                record.put("exclusiveRoleNames", Splitter.on(",").splitToList(exclusiveRoleNames));
            }
            
            int total = roleService.count(record);
            if (total > 0) {
                rows = roleService.select(record);
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

    @RequestMapping(path = "create", method = RequestMethod.GET)
    public String create(@Validated(value = Create.class) Role record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", record);
        try {
            if (record == null) {
                record = new Role();
            }
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "create");
        return "admin/role/create";
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String save(@Validated(value = Save.class) Role record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "save", record);
        try {
            if (!bindingResult.hasErrors()) {
                roleService.insert(record);
            } else {
                return "admin/role/create";
            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "admin/role/create";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
    }


    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public String edit(@Validated(value = Edit.class) Role record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "edit", record);
        try {
            model.addAttribute(roleService.selectByPrimaryKey(record.getId()));
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "edit");
        return "admin/role/edit";
    }

    @RequestMapping(path = "update", method = RequestMethod.POST)
    public String update(@Validated(value = Update.class) Role record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "update", record);
        try {
            if (!bindingResult.hasErrors()) {
                roleService.updateByPrimaryKey(record);
            } else {
                return "admin/role/edit";
            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "admin/role/edit";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "update");
        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public String detail(@Validated(value = Detail.class) Role record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "detail", record);
        try {
            model.addAttribute(roleService.selectByPrimaryKey(record.getId()));
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "detail");
        return "admin/role/detail";
    }

    @RequestMapping(path = "async-remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> asyncQuery(@Validated(value = Remove.class) Role record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = roleService.removeByPrimaryKey(record);
            result.put("success", true);
            result.put("message", "");
            result.put("data", aff);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-remove");
        return result;
    }
}
