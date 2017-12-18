package cn.jrry.test.base.controller;

import com.google.common.collect.Maps;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class TestController {

    @RequestMapping(value = "datetime_bind.json", method = RequestMethod.POST)
    public Map<String, Object> testDateTimeBind(
            @DateTimeFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss 'GMT'")
            /*@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")*/
            @RequestHeader(value = "Date") Date time
    ) {

        Map<String, Object> result = Maps.newHashMap();

        result.put("code", 200);
        result.put("message", String.format("parse datetime (%s) success", time));
        result.put("data", time);

        return result;
    }

    @RequestMapping(value = "locale.json", method = RequestMethod.POST)
    public Map<String, Object> testLocale(
    ) {

        Map<String, Object> result = Maps.newHashMap();

        result.put("code", 200);
        result.put("message", "get locale");
        result.put("data", String.format("%s,%s", LocaleContextHolder.getLocale(), LocaleContextHolder.getTimeZone()));


        return result;
    }
}
