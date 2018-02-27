package cn.jrry.wx.controller;

import cn.jrry.wx.service.InvokeService;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class VerifyController {
    private static final Logger logger = LoggerFactory.getLogger(VerifyController.class);

    @Autowired
    private InvokeService invokeService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = {"text/plain; charset=UTF-8"})
    public String verifyUrl(@RequestParam(required = false, defaultValue = "") String signature, @RequestParam(required = false, defaultValue = "") String timestamp, @RequestParam(required = false, defaultValue = "") String nonce, @RequestParam(required = false, defaultValue = "") String echostr) {

        String token = "";
        logger.info("signature {},  timestamp {}, nonce {}, echostr {} , token {} ", signature, timestamp, nonce, echostr, token);

        String[] sort = {token, timestamp, nonce};
        Arrays.sort(sort);

        String str = Joiner.on("").join(sort);
        String hashString = Hashing.sha1().hashString(str, Charsets.UTF_8).toString();
        if (hashString.equals(signature)) {
            return echostr;
        }
        return "error";
    }

    @RequestMapping(path = "verify-url",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> signature(@RequestParam(required = true) String url) {
        logger.info("--> {}.{}({})", "signature", url);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            result.put("success", true);
            result.put("message", "");
            result.put("data", invokeService.verifyWebUrl(URLDecoder.decode(url, Charsets.UTF_8.name())));

        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ex.getMessage());
            result.put("data", 0);
        }
        logger.info("<-- {}.{}","signature");
        return result;
    }


}
