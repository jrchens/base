package cn.jrry.mobile.controller;

import cn.jrry.util.ExceptionUtils;
import cn.jrry.wx.domain.WxUserInfo;
import cn.jrry.wx.domain.WxWebAccessToken;
import cn.jrry.wx.service.WxInvokeService;
import cn.jrry.wx.service.WxUserInfoService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller(value = "wxRegisterController")
@RequestMapping(path = "register")
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private static final String CONTROLLER_CLASS_NAME = RegisterController.class.getName();

    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private WxInvokeService wxInvokeService;

    @RequestMapping(method = RequestMethod.GET)
    public String register(String code, String state, Model model) {
        logger.info("--> {}.{}({},{})", CONTROLLER_CLASS_NAME, "register", code, state);
        try {

//            返回码	说明
//            10003	redirect_uri域名与后台配置不一致
//            10004	此公众号被封禁
//            10005	此公众号并没有这些scope的权限
//            10006	必须关注此测试号
//            10009	操作太频繁了，请稍后重试
//            10010	scope不能为空
//            10011	redirect_uri不能为空
//            10012	appid不能为空
//            10013	state不能为空
//            10015	公众号未授权第三方平台，请检查授权状态
//            10016	不支持微信开放平台的Appid，请使用公众号Appid


            WxWebAccessToken wxWebAccessToken = wxInvokeService.getWebAccessToken(code);
            wxInvokeService.insertWebAccessToken(wxWebAccessToken);

            model.addAttribute("state", state);
            model.addAttribute("PAGE_CONTEXT_TITLE", "监理用户注册");
            model.addAttribute(wxWebAccessToken);

        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}({},{})", CONTROLLER_CLASS_NAME, "register", code, state);
        return "mobile/register";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> register(@RequestParam(required = false) String viewname, @RequestParam(required = false) String mobile, @RequestParam(required = false) String openid) {
        logger.info("--> {}.{}({},{},{})", CONTROLLER_CLASS_NAME, "register", viewname,mobile,openid);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {

            WxUserInfo wxUserInfo = wxUserInfoService.selectByOpenid(openid);

            wxUserInfo.setViewname(viewname);
            wxUserInfo.setMobile(mobile);
            int aff = wxUserInfoService.updateByPrimaryKey(wxUserInfo);

            result.put("success", true);
            result.put("message", "");
            result.put("data", aff);

        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "register");
        return result;
    }
}
