package cn.jrry.wx.controller;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
@RequestMapping(value = "/")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

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

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = "application/xml; charset=UTF-8")
    public String message(HttpServletRequest request) {
        return "";
    }


}
