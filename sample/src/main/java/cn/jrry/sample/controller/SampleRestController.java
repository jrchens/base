package cn.jrry.sample.controller;

import cn.jrry.sample.pojo.Sample;
import cn.jrry.sample.service.SampleService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "rest/sample")
public class SampleRestController {
    private static final Logger logger = LoggerFactory.getLogger(SampleRestController.class);
    private static final String CONTROLLER_CLASS_NAME = SampleRestController.class.getName();

    @Autowired
    private SampleService userService;

    @RequestMapping(method = RequestMethod.POST)
    public Map<String,Object> save(@RequestHeader String username, @RequestBody Sample record){
        logger.info("--> {}.{}({})",CONTROLLER_CLASS_NAME,"save", record.toString());
        Map<String,Object> result = Maps.newLinkedHashMap();
        record.setCruser(username);
        userService.insert(record);
        result.put("code",200);
        result.put("status","success");
        result.put("message","save user success");
        result.put("data",userService.selectByPrimaryKey(record.getId()));
        logger.info("<-- {}.{}",CONTROLLER_CLASS_NAME,"save");
        return result;
    }


    @RequestMapping(path = "{id}",method = RequestMethod.DELETE)
    public Map<String,Object> remove(@RequestHeader String username,@PathVariable Long id){
        logger.info("--> {}.{}({})",CONTROLLER_CLASS_NAME,"remove", id);
        Map<String,Object> result = Maps.newLinkedHashMap();

        Sample record = new Sample();
        record.setId(id);
        record.setMduser(username);

        int aff = userService.removeByPrimaryKey(record);
        result.put("code",200);
        result.put("message","remove user success");
        result.put("data",aff);
        logger.info("<-- {}.{}",CONTROLLER_CLASS_NAME,"remove");
        return result;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Map<String,Object> update(@RequestHeader String username,@RequestBody Sample record){
        logger.info("--> {}.{}({})",CONTROLLER_CLASS_NAME,"update", record.toString());
        Map<String,Object> result = Maps.newLinkedHashMap();
        record.setMduser(username);
        userService.updateByPrimaryKey(record);
        result.put("code",200);
        result.put("message","update user success");
        result.put("data",userService.selectByPrimaryKey(record.getId()));
        logger.info("<-- {}.{}",CONTROLLER_CLASS_NAME,"update");
        return result;
    }

    @RequestMapping(path = "{id}",method = RequestMethod.GET)
    public Map<String,Object> get(@RequestHeader String username,@PathVariable(value = "id") Long id){
        logger.info("--> {}.{}({})",CONTROLLER_CLASS_NAME,"get", id);
        Map<String,Object> result = Maps.newLinkedHashMap();
        result.put("code",200);
        result.put("message","get user success");
        result.put("data",userService.selectByPrimaryKey(id));
        logger.info("<-- {}.{}",CONTROLLER_CLASS_NAME,"get");
        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Map<String,Object> query(@RequestHeader String username,
                                    @RequestParam(required = false) Map<String,String> record ){
        logger.info("--> {}.{}({},{},{})",CONTROLLER_CLASS_NAME,"query", record);
        Map<String,Object> result = Maps.newLinkedHashMap();
        result.put("code",200);
        result.put("message","query user success");

        Map<String,Object> data = Maps.newHashMap();
        data.put("total",userService.count(record));
        data.put("rows",userService.select(record));

        result.put("data",data);
        logger.info("<-- {}.{}",CONTROLLER_CLASS_NAME,"query");
        return result;
    }
}
