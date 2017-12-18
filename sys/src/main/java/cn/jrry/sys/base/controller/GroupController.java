package cn.jrry.sys.base.controller;

import cn.jrry.sys.domain.Group;
import cn.jrry.sys.service.GroupService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(path = "group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @RequestMapping(path = "save.json", method = RequestMethod.POST/*, params = {}, headers = {}, params = {}, consumes = {}, produces = {}*/)
    public Map<String, Object> save(
            @RequestBody Group record

    ) {

        Map<String, Object> result = Maps.newLinkedHashMap();


        int id = groupService.insert(record);

        result.put("code", 200);
        result.put("message", "save success");
        result.put("data", id);

        return result;
    }


    @RequestMapping(path = "env.json", method = RequestMethod.POST/*, params = {}, headers = {}, params = {}, consumes = {}, produces = {}*/)
    public Map<String, Object> env(

    ) {

        Map<String, Object> result = Maps.newLinkedHashMap();

        String tz = System.getProperty("user.timezone");
        String ul = System.getProperty("user.language");
        String uc = System.getProperty("user.country");

        result.put("code", 200);
        result.put("message", String.format("%s,%s,%s", tz, ul, uc));
        result.put("data", new Date().toString());

        return result;
    }


}
