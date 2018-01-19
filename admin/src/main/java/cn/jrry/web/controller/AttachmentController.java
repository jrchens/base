package cn.jrry.web.controller;

import cn.jrry.admin.service.ConfigService;
import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.Remove;
import cn.jrry.web.domain.Attachment;
import cn.jrry.web.service.AttachmentService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "attachment")
public class AttachmentController {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);
    private static final String CONTROLLER_CLASS_NAME = AttachmentController.class.getName();

    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private ConfigService configService;

    private static final String DOWNLOAD_DOMAIN = "d3cbc840-c8ca-4d39-ab60-e62535728338";

    @RequestMapping(path = {"index"}, method = RequestMethod.GET)
    public String index(@Validated Attachment record, @RequestParam(name = "errorType", required = false, defaultValue = "") String errorType, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record);
        try {
            if (record == null) {
                record = new Attachment();
            }
            model.addAttribute(record);

            String downloadDomain = configService.selectByPrimaryKey(DOWNLOAD_DOMAIN).getCfgValue();
            model.addAttribute("downloadDomain",downloadDomain);

//            if ("multipartException".equals(errorType)){ // web.xml error-page
//                model.addAttribute("controller_error","Upload File Size Limit Error");
//            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "attachment/index";
    }

    @RequestMapping(path = {"async-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<Attachment> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {

            Subject subject = SecurityUtils.getSubject();
            if (!subject.hasRole("admin")) {
                record.put("owner", subject.getPrincipal().toString());
            }

            int total = attachmentService.count(record);
            if (total > 0) {
                rows = attachmentService.select(record);
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

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String save(@RequestParam(name = "file", required = true) MultipartFile[] file, @RequestParam(name = "tag", required = false) String tag, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "save");
        try {
            // TODO page verify file size,MultipartException process
            attachmentService.insert(file, tag);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "attachment/index";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
        return "redirect:index";
    }

    @RequestMapping(path = "async-remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> remove(@Validated(value = Remove.class) Attachment record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = attachmentService.removeByPrimaryKey(record);
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
