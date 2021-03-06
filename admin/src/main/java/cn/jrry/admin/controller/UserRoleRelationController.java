package cn.jrry.admin.controller;

import cn.jrry.admin.domain.User;
import cn.jrry.admin.domain.UserRoleRelation;
import cn.jrry.admin.service.UserRoleRelationService;
import cn.jrry.admin.service.UserService;
import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.Get;
import cn.jrry.validation.group.Remove;
import com.google.common.base.Joiner;
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

@Controller
@RequestMapping(path = "admin/user-role-relation")
public class UserRoleRelationController {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleRelationController.class);
    private static final String CONTROLLER_CLASS_NAME = UserRoleRelationController.class.getName();

    @Autowired
    private UserRoleRelationService userRoleRelationService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"async-role-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncRoleQuery(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-role-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<UserRoleRelation> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int total = userRoleRelationService.countRole(record);
            if (total > 0) {
                rows = userRoleRelationService.selectRole(record);
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
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-role-query");
        return result;
    }

    @RequestMapping(path = "create", method = RequestMethod.GET)
    public String create(@RequestParam(value = "id") Long userId, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", userId);
        try {
//            List<Role> rows = Lists.newArrayList();
            User record = userService.selectByPrimaryKey(userId);
//            List<Role> roleList = roleService.selectAll();
            List<UserRoleRelation> userRoleRelationList = userRoleRelationService.selectRoleByUsername(record.getUsername());
            List<String> roleNames = Lists.newArrayList();
            for (UserRoleRelation urr:userRoleRelationList
                    ) {
                roleNames.add(urr.getRoleName());
            }

            model.addAttribute("roleNames", Joiner.on(",").join(roleNames));
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "create");
        return "admin/user/user_role_create";
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String save(@RequestParam(value = "id") Long userId, @RequestParam(name = "roleNames") String roleNames, Model model) {
        logger.info("--> {}.{}({},{})", CONTROLLER_CLASS_NAME, "save", userId, roleNames);
        User record = null;
        try {
            record = userService.selectByPrimaryKey(userId);

            String[] roleNameArray = roleNames.split(",");
            List<UserRoleRelation> userRoleRelationList = Lists.newArrayList();
            for (String roleName : roleNameArray
                    ) {
                UserRoleRelation UserRoleRelation = new UserRoleRelation();
                UserRoleRelation.setUsername(record.getUsername());
                UserRoleRelation.setRoleName(roleName);
                UserRoleRelation.setDef(Boolean.FALSE);

                userRoleRelationList.add(UserRoleRelation);
            }

            int aff = userRoleRelationService.insert(userRoleRelationList);

//            if (!bindingResult.hasErrors()) {
//                userRoleRelationService.insert(record);
//            } else {
//                return "admin/group/create";
//            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "admin/user/user_role_create";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
        return "redirect:/admin/user/detail?id=" + record.getId();// detail(record, bindingResult, model);
    }

    @RequestMapping(path = "async-delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> asyncDelete(@Validated(value = Remove.class) UserRoleRelation record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-delete", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = userRoleRelationService.deleteByPrimaryKey(record.getId());
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

    @RequestMapping(path = "async-update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDef(@Validated(value = Get.class) UserRoleRelation record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-update", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = userRoleRelationService.updateDefByPrimaryKey(record.getId());
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

    @RequestMapping(path = {"async-user-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncUserQuery(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-user-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<UserRoleRelation> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {

            int total = userRoleRelationService.countUser(record);
            if (total > 0) {
                rows = userRoleRelationService.selectUser(record);
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
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-user-query");
        return result;
    }

}
