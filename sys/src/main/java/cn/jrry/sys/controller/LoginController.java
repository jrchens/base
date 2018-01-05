package cn.jrry.sys.controller;

import cn.jrry.common.dto.LoginUser;
import cn.jrry.common.exception.UserNotFoundException;
import cn.jrry.common.exception.UserPasswordErrorException;
import cn.jrry.common.exception.UserStateException;
import cn.jrry.common.service.LoginService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "login.json", method = RequestMethod.POST)
    public Map<String, Object> login(
            @Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) {
        logger.debug("--> cn.jrry.sys.controller.LoginController.login({},{})", loginUser.getUsername(), loginUser.getPassword());
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> data = Maps.newHashMap();
        try {
            if (bindingResult.hasErrors()) {
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                result.put("code", 400);
                result.put("message", "bad.request");
                for (FieldError fieldError : fieldErrors
                        ) {
                    data.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                result.put("data", data);
                return result;
            }
            result.put("code", 200);
            result.put("message", "login.success");
            data = loginService.login(loginUser.getUsername(), loginUser.getPassword());
            if (data == null) {
                result.put("message", "login.failed,username.not.exists");
            }
            result.put("data", data);
        } catch (UserNotFoundException ex) {
            result.put("code", 1001);
            result.put("message", "login.failed,user.not.found");
            result.put("data", data);
        } catch (UserPasswordErrorException ex) {
            result.put("code", 1002);
            result.put("message", "login.failed,password.error");
            result.put("data", data);
        } catch (UserStateException ex) {
            String code = ex.getMessage();
            if (code.equals("locked")) {
                result.put("code", 1003);
                result.put("message", "login.failed,user.locked");
            } else if (code.equals("disabled")) {
                result.put("code", 1004);
                result.put("message", "login.failed,user.disabled");
            }
            result.put("data", data);
        } catch (Exception ex) {
            result.put("code", 1005);
            result.put("message", "login.failed");
            result.put("data", data);
        }
        logger.debug("<-- cn.jrry.sys.controller.LoginController.login");
        return result;
    }

}
