package cn.jrry.admin.controller;

import cn.jrry.admin.domain.User;
import cn.jrry.admin.domain.UserGroupRelation;
import cn.jrry.admin.service.GroupService;
import cn.jrry.admin.service.UserGroupRelationService;
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

    @RequestMapping(path = {"async-group-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-group-query", record);
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
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-group-query");
        return result;
    }

    @RequestMapping(path = "create", method = RequestMethod.GET)
    public String create(@RequestParam(name = "id") Long userId, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", userId);
        try {
            User record = userService.selectByPrimaryKey(userId);
            List<UserGroupRelation> userGroupRelationList = userGroupRelationService.selectGroupByUsername(record.getUsername());
            List<String> groupNames = Lists.newArrayList();
            for (UserGroupRelation ugr : userGroupRelationList
                    ) {
                groupNames.add(ugr.getGroupName());
            }

            model.addAttribute(record);
            model.addAttribute("exclusiveGroupNames", Joiner.on(",").join(groupNames));
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "create");
        return "admin/user/user_group_create";
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String save(@RequestParam(name = "id") Long userId, @RequestParam(name = "groupNames") String groupNames, Model model) {
        logger.info("--> {}.{}({},{})", CONTROLLER_CLASS_NAME, "save", userId, groupNames);
        User record = null;
        try {
            record = userService.selectByPrimaryKey(userId);

            String[] groupNameArray = groupNames.split(",");
            List<UserGroupRelation> userGroupRelationList = Lists.newArrayList();
            for (String groupName : groupNameArray
                    ) {
                UserGroupRelation userGroupRelation = new UserGroupRelation();
                userGroupRelation.setUsername(record.getUsername());
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

    @RequestMapping(path = "async-delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@Validated(value = Remove.class) UserGroupRelation record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-delete", record);
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

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-delete");
        return result;
    }

    @RequestMapping(path = "async-update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDef(@Validated(value = Get.class) UserGroupRelation record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-update", record);
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

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-update");
        return result;
    }

    @RequestMapping(path = {"async-user-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUser(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-user-query", record);
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
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-user-query");
        return result;
    }
}
