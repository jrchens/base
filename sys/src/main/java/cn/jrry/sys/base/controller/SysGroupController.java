package cn.jrry.sys.base.controller;

import cn.jrry.sys.domain.SysGroup;
import cn.jrry.sys.service.SysGroupService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(path = "sys_group")
public class SysGroupController {

    @Autowired
    private SysGroupService sysGroupService;

    private static final Logger logger = LoggerFactory.getLogger(SysGroupController.class);

    @RequestMapping(path = "save.json", method = RequestMethod.POST/*, params = {}, headers = {}, params = {}, consumes = {}, produces = {}*/)
    public Map<String, Object> save(
            @RequestHeader(name = "username") String user,
            /*@DateTimeFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss z",iso = DateTimeFormat.ISO.NONE)*/
            /*@DateTimeFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss 'GMT'")*/
            /*@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")*/
            /*@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")*/
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestHeader(name = "Date") Date time,
            /*@RequestHeader(name = "Date") String time,*/
            @RequestBody SysGroup sysGroup
    ) {
        // -Duser.timezone=GMT -Duser.language=en -Duser.country=US
        Map<String, Object> result = Maps.newLinkedHashMap();

        logger.info("username:{}, Date: {}", user, time);

        // SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);

        sysGroup.setCruser(user);
        sysGroup.setCrtime(time);
//        try {
//            sysGroup.setCrtime(format.parse(time));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        int id = sysGroupService.insert(sysGroup);

        result.put("code", 200);
        result.put("message", "save success");
        result.put("data", id);

        return result;
    }


}
