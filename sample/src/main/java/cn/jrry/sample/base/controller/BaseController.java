package cn.jrry.sample.base.controller;

import cn.jrry.sample.base.service.BaseService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    private static final String CONTROLLER_CLASS_NAME = BaseController.class.getName();

    @Autowired
    private BaseService baseService;

    @RequestMapping(path = "now.json",method = RequestMethod.GET)
    public Map<String,Object> now(){
        logger.info("--> {}.{}",CONTROLLER_CLASS_NAME,"now");
        Map<String,Object> result = Maps.newLinkedHashMap();
        result.put("now",baseService.getNow());
        logger.info("<-- {}.{}",CONTROLLER_CLASS_NAME,"now");
        return result;
    }
}
