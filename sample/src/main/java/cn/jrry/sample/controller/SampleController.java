package cn.jrry.sample.controller;

import cn.jrry.sample.pojo.Sample;
import cn.jrry.sample.service.SampleService;
import cn.jrry.validation.group.*;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "sample")
public class SampleController {
    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
    private static final String CONTROLLER_CLASS_NAME = SampleController.class.getName();

    @Autowired
    private SampleService userService;

    @RequestMapping(path = {"index"}, method = RequestMethod.GET)
    public String index(@Validated Sample record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record.toString());
        try {

            if (bindingResult.hasErrors()) {
                logger.info("{},{},{}", record, bindingResult, model);
            } else {
                model.addAttribute(record);
            }

        } catch (Exception ex) {
            model.addAttribute("index.sample.error", ex.getMessage());
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "sample/index";
    }

    @RequestMapping(path = {"query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query(Map<String, String> record) {
        logger.info("--> {}.{}({},{},{})", CONTROLLER_CLASS_NAME, "query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<Sample> rows = Lists.newArrayList();
        try {

            int total = userService.count(record);
            if (total > 0) {
                rows = userService.select(record);
            }
            Map<String, Object> data = Maps.newLinkedHashMap();
            data.put("total", total);
            data.put("rows", rows);

            result.put("success", true);
            result.put("message", "");
            result.put("data", data);

        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ex.getMessage());
            result.put("data", false);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "query");
        return result;
    }

    @RequestMapping(path = "create", method = RequestMethod.GET)
    public String create(@Validated(value = Create.class) Sample record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", record.toString());
        try {
            if (record == null) {
                record = new Sample();
            }
            record.setBdate(new Date(System.currentTimeMillis()));
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("create.sample.error", ex.getMessage());
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "create");
        return "sample/create";
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String save(@Validated(value = Save.class) Sample record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "save", record.toString());
        try {
            if (!bindingResult.hasErrors()) {
                userService.insert(record);
            } else {
                return "sample/create";
            }
        } catch (Exception ex) {
            model.addAttribute("save.sample.error", ex.getMessage());
            return "sample/create";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
    }


    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public String edit(@Validated(value = Edit.class) Sample record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "edit", record.toString());
        try {
            if (!bindingResult.hasErrors()) {
                model.addAttribute(userService.selectByPrimaryKey(record.getId()));
            }
        } catch (Exception ex) {
            model.addAttribute("edit.sample.error", ex.getMessage());
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "edit");
        return "sample/edit";
    }

    @RequestMapping(path = "update", method = RequestMethod.POST)
    public String update(@Validated(value = Update.class) Sample record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "update", record.toString());
        try {
            if (!bindingResult.hasErrors()) {
                userService.updateByPrimaryKey(record);
            } else {
                return "sample/edit";
            }
        } catch (Exception ex) {
            model.addAttribute("update.sample.error", ex.getMessage());
            return "sample/edit";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "update");
        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public String detail(@Validated(value = Detail.class) Sample record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "detail");
        try {
            if (!bindingResult.hasErrors()) {
                model.addAttribute(userService.selectByPrimaryKey(record.getId()));
            }
        } catch (Exception ex) {
            model.addAttribute("detail.sample.error", ex.getMessage());
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "detail");
        return "sample/detail";
    }

    @RequestMapping(path = "remove", method = RequestMethod.POST)
    public String remove(@Validated(value = Remove.class) Sample record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "remove");
        try {
            if (!bindingResult.hasErrors()) {
                userService.removeByPrimaryKey(record);
            } else {
                return "sample/detail";
            }
        } catch (Exception ex) {
            model.addAttribute("create.sample.error", ex.getMessage());
            return "sample/detail";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "remove");
        return "redirect:index";//index(record, bindingResult,model);
    }

    @RequestMapping(path = "async-remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> asyncRemove(@Validated(value = Remove.class) Sample record, BindingResult bindingResult) {
        logger.info("--> {}.{}({},{},{})", CONTROLLER_CLASS_NAME, "async-remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = userService.removeByPrimaryKey(record);
            result.put("success", true);
            result.put("message", "");
            result.put("data", aff);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ex.getMessage());
            result.put("data", 0);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-remove");
        return result;
    }
}
