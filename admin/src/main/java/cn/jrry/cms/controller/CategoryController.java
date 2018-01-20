package cn.jrry.cms.controller;

import cn.jrry.cms.domain.Category;
import cn.jrry.cms.domain.TreeNode;
import cn.jrry.cms.service.CategoryService;
import cn.jrry.common.exception.ServiceException;
import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.Detail;
import cn.jrry.validation.group.Remove;
import cn.jrry.validation.group.Save;
import cn.jrry.validation.group.Update;
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
@RequestMapping(path = "cms/category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private static final String CONTROLLER_CLASS_NAME = CategoryController.class.getName();

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(path = {"index"}, method = RequestMethod.GET)
    public String index(@Validated Category record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record);
        try {
            if (record == null) {
                record = new Category();
            }
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "cms/category/index";
    }

    @RequestMapping(path = {"async-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncQuery(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<Category> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {

            int total = categoryService.count(record);
            if (total > 0) {
                rows = categoryService.select(record);
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

    @RequestMapping(path = {"async-tree-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncTreeQuery(@RequestParam(required = false, defaultValue = "0") Long id) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-query", id);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<Category> rows = Lists.newArrayList();
        List<TreeNode> data = Lists.newArrayList();
        try {

            rows = categoryService.selectByParentId(id);
            for (Category category : rows
                    ) {
//                List<TreeNode> children = Lists.newArrayList();
                List<Category> categoryList = categoryService.selectByParentId(category.getId());
//                for (Category ca :
//                        categoryList) {
//                    children.add(new TreeNode(ca.getId(), ca.getCategoryName()));
//                }
                String state = "open";
                if (categoryList!=null && categoryList.size()>0){
                    state = "closed";
                }
                TreeNode node = new TreeNode(category.getId(), category.getCategoryName());
                node.setState(state);
//                if (children.size() > 0) {
//                    node.setChildren(children);
//                }
                data.add(node);
            }

            result.put("success", true);
            result.put("message", "");
            result.put("data", data);

        } catch (Exception ex) {

            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", data);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-query");
        return result;
    }

    @RequestMapping(path = "async-detail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncDetail(@Validated(value = Detail.class) Category record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-detail", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            Category data = categoryService.selectByPrimaryKey(record.getId());
            result.put("success", true);
            result.put("message", "");
            result.put("data", data);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", new Object());
        }

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-detail");
        return result;
    }

    @RequestMapping(path = "async-save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> asyncSave(@Validated(value = Save.class) Category record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-save", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            categoryService.insert(record);
            result.put("success", true);
            result.put("message", "");
            result.put("data", record.getId());
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-save");
        return result;
    }

    @RequestMapping(path = "async-update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> asyncUpdate(@Validated(value = Update.class) Category record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-update", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            int aff = categoryService.updateByPrimaryKey(record);
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

    @RequestMapping(path = "async-remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> asyncRemove(@Validated(value = Remove.class) Category record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {

            List<Category> children = categoryService.selectByParentId(record.getId());
            if (children != null && children.size() > 0) {
                throw new ServiceException("parent node can't remove");
            }
            int aff = categoryService.removeByPrimaryKey(record);
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
