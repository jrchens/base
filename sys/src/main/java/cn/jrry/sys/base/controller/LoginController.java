package cn.jrry.sys.base.controller;

import cn.jrry.sys.domain.User;
import cn.jrry.sys.service.UserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "login", method = RequestMethod.POST/*, params = {}, headers = {}, params = {}, consumes = {}, produces = {}*/)
    public Map<String, Object> login(
            @RequestParam(name = "username", required = true) String username,
            @RequestParam(name = "password", required = true) String password,
            @RequestParam(name = "reCaptcha", required = false) String reCaptcha,
            @RequestParam(name = "rememberMe", required = false, defaultValue = "0") String rememberMe

    ) {

        Map<String, Object> result = Maps.newLinkedHashMap();

        HttpMessageConverter hmc = null;

        User user = userService.getUserByUsername(username);

        result.put("code", 200);
        result.put("message", "login success");
        result.put("data", user);

        return result;
    }

    @RequestMapping(path = "login.json", method = RequestMethod.POST/*, params = {}, headers = {}, params = {}, consumes = {}, produces = {}*/)
    public Map<String, Object> loginJSON(
            @RequestBody LoginUser loginUser

    ) {

        Map<String, Object> result = Maps.newLinkedHashMap();

        HttpMessageConverter hmc = null;

        User user = userService.getUserByUsername(loginUser.getUsername());

        result.put("code", 200);
        result.put("message", "login success");
        result.put("data", user);

        return result;
    }


}

class LoginUser implements java.io.Serializable {
    private static final long serialVersionUID = 1373114224448675799L;
    private String username;
    private String password;
    private String reCaptcha;
    private String rememberMe;

    public LoginUser(String username, String password, String reCaptcha, String rememberMe) {
        this.username = username;
        this.password = password;
        this.reCaptcha = reCaptcha;
        this.rememberMe = rememberMe;
    }

    public LoginUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReCaptcha() {
        return reCaptcha;
    }

    public void setReCaptcha(String reCaptcha) {
        this.reCaptcha = reCaptcha;
    }

    public String getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }
}