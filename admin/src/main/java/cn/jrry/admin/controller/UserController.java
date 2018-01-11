package cn.jrry.admin.controller;

import cn.jrry.admin.domain.User;
import cn.jrry.admin.domain.UserDTO;
import cn.jrry.admin.domain.UserVO;
import cn.jrry.admin.service.UserService;
import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
@RequestMapping(path = "admin/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String CONTROLLER_CLASS_NAME = UserController.class.getName();

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"index"}, method = RequestMethod.GET)
    public String index(@Validated UserDTO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record.toString());
        try {
            if (record == null) {
                record = new UserDTO();
            }
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "admin/user/index";
    }

    @RequestMapping(path = {"query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({},{},{})", CONTROLLER_CLASS_NAME, "query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<UserVO> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {

            int total = userService.count(record);
            if (total > 0) {
                rows = userService.select(record);
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
    public String create(@Validated(value = Create.class) UserDTO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", record.toString());
        try {
            if (record == null) {
                record = new UserDTO();
            }
            record.setLocked(Boolean.FALSE);
            record.setDisabled(Boolean.FALSE);
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "create");
        return "admin/user/create";
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String save(@Validated(value = Save.class) UserDTO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "save", record.toString());
        try {
            if (!bindingResult.hasErrors()) {
                userService.insert(record);
            } else {
                record.setPassword(null);
                return "admin/user/create";
            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            record.setPassword(null);
            return "admin/user/create";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
    }


    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public String edit(@Validated(value = Edit.class) UserDTO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "edit", record.toString());
        try {
            UserVO userVO = userService.selectByPrimaryKey(record.getId());
            BeanUtils.copyProperties(userVO,record);
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "edit");
        return "admin/user/edit";
    }

    @RequestMapping(path = "update", method = RequestMethod.POST)
    public String update(@Validated(value = Update.class) UserDTO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "update", record.toString());
        try {
            if (!bindingResult.hasErrors()) {
                userService.updateByPrimaryKey(record);
            } else {
                return "admin/user/edit";
            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "admin/user/edit";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "update");
        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public String detail(@Validated(value = Detail.class) UserDTO record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "detail");
        try {
            model.addAttribute(userService.selectByPrimaryKey(record.getId()));
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "detail");
        return "admin/user/detail";
    }

    @RequestMapping(path = "remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> remove(@Validated(value = Remove.class) UserDTO record, BindingResult bindingResult) {
        logger.info("--> {}.{}({},{},{})", CONTROLLER_CLASS_NAME, "remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = userService.removeByPrimaryKey(record);
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
