package cn.jrry.admin.controller;

import cn.jrry.admin.domain.Role;
import cn.jrry.admin.domain.RolePermissionRelation;
import cn.jrry.admin.service.RolePermissionRelationService;
import cn.jrry.admin.service.RoleService;
import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.Remove;
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
@RequestMapping(path = "admin/role-permission-relation")
public class RolePermissionRelationController {
    private static final Logger logger = LoggerFactory.getLogger(RolePermissionRelationController.class);
    private static final String CONTROLLER_CLASS_NAME = RolePermissionRelationController.class.getName();

    @Autowired
    private RolePermissionRelationService rolePermissionRelationService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(path = {"async-permission-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncPermissionQuery(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-permission-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<RolePermissionRelation> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int total = rolePermissionRelationService.countPermission(record);
            if (total > 0) {
                rows = rolePermissionRelationService.selectPermission(record);
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
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-permission-query");
        return result;
    }

    @RequestMapping(path = "create", method = RequestMethod.GET)
    public String create(@RequestParam(name = "id") Long roleId, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", roleId);
        try {
            Role record = roleService.selectByPrimaryKey(roleId);
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "create");
        return "admin/role/role_permission_create";
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String save(@RequestParam(name = "id") Long roleId, @RequestParam(name = "permissions") String permissions, Model model) {
        logger.info("--> {}.{}({},{})", CONTROLLER_CLASS_NAME, "save", roleId, permissions);
        Role record = null;
        try {
            record = roleService.selectByPrimaryKey(roleId);

            String[] permissionArray = permissions.split(",");
            List<RolePermissionRelation> rolePermissionRelationList = Lists.newArrayList();
            for (String permission : permissionArray
                    ) {
                RolePermissionRelation rolePermissionRelation = new RolePermissionRelation();
                rolePermissionRelation.setPermission(permission);
                rolePermissionRelation.setRoleName(record.getRoleName());

                rolePermissionRelationList.add(rolePermissionRelation);
            }

            int aff = rolePermissionRelationService.insert(rolePermissionRelationList);

        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "admin/role/role_permission_create";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
        return "redirect:/admin/role/detail?id=" + record.getId();// detail(record, bindingResult, model);
    }

    @RequestMapping(path = "async-delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> asyncDelete(@Validated(value = Remove.class) RolePermissionRelation record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            int aff = rolePermissionRelationService.deleteByPrimaryKey(record.getId());
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
