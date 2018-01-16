package cn.jrry.admin.controller;

import cn.jrry.admin.domain.Group;
import cn.jrry.admin.domain.User;
import cn.jrry.admin.domain.UserGroupRelation;
import cn.jrry.admin.service.GroupService;
import cn.jrry.admin.service.UserGroupRelationService;
import cn.jrry.admin.service.UserService;
import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.Create;
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
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "admin/user-group-relation")
public class UserGroupRelationController {
    private static final Logger logger = LoggerFactory.getLogger(UserGroupRelationController.class);
    private static final String CONTROLLER_CLASS_NAME = UserGroupRelationController.class.getName();

    @Autowired
    private UserGroupRelationService userGroupRelationService;

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

//    @RequestMapping(path = {"user/index"}, method = RequestMethod.GET)
//    public String index(@Validated Group record, BindingResult bindingResult, Model model) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record);
//        try {
//            if (record == null) {
//                record = new Group();
//            }
//            model.addAttribute(record);
//        } catch (Exception ex) {
//            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
//        }
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
//        return "admin/group/index";
//    }

    @RequestMapping(path = {"user/async-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "user/async-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<UserGroupRelation> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int total = userGroupRelationService.countGroup(record);
            if (total > 0) {
                rows = userGroupRelationService.selectGroup(record);
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
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "user/async-query");
        return result;
    }

    @RequestMapping(path = "user/create/{username}", method = RequestMethod.GET)
    public String create(@PathVariable(value = "username") String username, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", username);
        try {
            User record = userService.selectByUsername(username);
            List<UserGroupRelation> userGroupRelationList = userGroupRelationService.selectGroupByUsername(username);
            List<String> groupNames = Lists.newArrayList();
            for (UserGroupRelation ugr:userGroupRelationList
                 ) {
                groupNames.add(ugr.getGroupName());
            }

            model.addAttribute(record);
            model.addAttribute("groupNames", Joiner.on(",").join(groupNames));
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "create");
        return "admin/user/user_group_create";
    }

    @RequestMapping(path = "user/save/{username}", method = RequestMethod.POST)
    public String save(@PathVariable(value = "username") String username, @RequestParam(name = "groupNames") String groupNames, Model model) {
        logger.info("--> {}.{}({},{})", CONTROLLER_CLASS_NAME, "user/save", username, groupNames);
        User record = null;
        try {
            record = userService.selectByUsername(username);

            String[] groupNameArray = groupNames.split(",");
            List<UserGroupRelation> userGroupRelationList = Lists.newArrayList();
            for (String groupName : groupNameArray
                    ) {
                UserGroupRelation userGroupRelation = new UserGroupRelation();
                userGroupRelation.setUsername(username);
                userGroupRelation.setGroupName(groupName);
                userGroupRelation.setDef(Boolean.FALSE);

                userGroupRelationList.add(userGroupRelation);
            }

            int aff = userGroupRelationService.insert(userGroupRelationList);

//            if (!bindingResult.hasErrors()) {
//                userGroupRelationService.insert(record);
//            } else {
//                return "admin/group/create";
//            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "admin/user/user_group_create";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
        return "redirect:/admin/user/detail?id=" + record.getId();// detail(record, bindingResult, model);
    }

    @RequestMapping(path = "user/async-delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@Validated(value = Remove.class) UserGroupRelation record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = userGroupRelationService.deleteByPrimaryKey(record.getId());
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

    @RequestMapping(path = "user/async-update-def", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDef(@Validated(value = Get.class) UserGroupRelation record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = userGroupRelationService.updateDefByPrimaryKey(record.getId());
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

    // ===
    // group

    @RequestMapping(path = {"group/async-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUser(Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "group/async-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<UserGroupRelation> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {

            int total = userGroupRelationService.countUser(record);
            if (total > 0) {
                rows = userGroupRelationService.selectUser(record);
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
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "group/async-query");
        return result;
    }


//    @RequestMapping(path = "edit", method = RequestMethod.GET)
//    public String edit(@Validated(value = Edit.class) Group record, BindingResult bindingResult, Model model) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "edit", record);
//        try {
//            model.addAttribute(userGroupRelationService.selectByPrimaryKey(record.getId()));
//        } catch (Exception ex) {
//            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
//        }
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "edit");
//        return "admin/group/edit";
//    }
//
//    @RequestMapping(path = "update", method = RequestMethod.POST)
//    public String update(@Validated(value = Update.class) Group record, BindingResult bindingResult, Model model) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "update", record);
//        try {
//            if (!bindingResult.hasErrors()) {
//                userGroupRelationService.updateByPrimaryKey(record);
//            } else {
//                return "admin/group/edit";
//            }
//        } catch (Exception ex) {
//            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
//            return "admin/group/edit";
//        }
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "update");
//        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
//    }
//
//    @RequestMapping(path = "detail", method = RequestMethod.GET)
//    public String detail(@Validated(value = Detail.class) Group record, BindingResult bindingResult, Model model) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "detail",record);
//        try {
//            model.addAttribute(userGroupRelationService.selectByPrimaryKey(record.getId()));
//        } catch (Exception ex) {
//            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
//        }
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "detail");
//        return "admin/group/detail";
//    }
//
//    @RequestMapping(path = "async-remove", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> remove(@Validated(value = Remove.class) Group record, BindingResult bindingResult) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-remove", record);
//        Map<String, Object> result = Maps.newLinkedHashMap();
//        Map<String, Object> data = Maps.newLinkedHashMap();
//        try {
//            int aff = userGroupRelationService.removeByPrimaryKey(record);
//            result.put("success", true);
//            result.put("message", "");
//            result.put("data", aff);
//        } catch (Exception ex) {
//            result.put("success", false);
//            result.put("message", ExceptionUtils.getSimpleMessage(ex));
//            result.put("data", 0);
//        }
//
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-remove");
//        return result;
//    }
}
