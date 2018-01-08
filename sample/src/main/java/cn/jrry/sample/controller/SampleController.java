package cn.jrry.sample.controller;

import cn.jrry.sample.pojo.Sample;
import cn.jrry.sample.service.SampleService;
import cn.jrry.util.ExceptionUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
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
            record = new Sample();
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("index_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "sample/index";
    }

    @RequestMapping(path = {"query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query(@RequestParam(required = false) Map<String, String> record) {
        logger.info("--> {}.{}({},{},{})", CONTROLLER_CLASS_NAME, "query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<Sample> rows = Lists.newArrayList();
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
    public String create(@Validated(value = Create.class) Sample record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", record.toString());
        try {
            if (record == null) {
                record = new Sample();
            }
            Date now = new Date(System.currentTimeMillis());
            record.setBdate(now);
            record.setBdatetime(now);
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("create_error", ExceptionUtils.getSimpleMessage(ex));
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
            model.addAttribute("save_error", ExceptionUtils.getSimpleMessage(ex));
            return "sample/create";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
    }


    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public String edit(@Validated(value = Edit.class) Sample record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "edit", record.toString());
        try {
            model.addAttribute(userService.selectByPrimaryKey(record.getId()));
        } catch (Exception ex) {
            model.addAttribute("edit_error", ExceptionUtils.getSimpleMessage(ex));
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
            model.addAttribute("update_error", ExceptionUtils.getSimpleMessage(ex));
            return "sample/edit";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "update");
        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public String detail(@Validated(value = Detail.class) Sample record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "detail");
        try {
            model.addAttribute(userService.selectByPrimaryKey(record.getId()));
        } catch (Exception ex) {
            model.addAttribute("detail_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "detail");
        return "sample/detail";
    }

    @RequestMapping(path = "remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> remove(@Validated(value = Remove.class) Sample record, BindingResult bindingResult) {
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
