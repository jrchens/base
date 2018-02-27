package cn.jrry.mobile.controller;

import cn.jrry.util.ExceptionUtils;
import cn.jrry.wx.domain.WxConfig;
import cn.jrry.wx.domain.WxMenu;
import cn.jrry.wx.domain.WxUserInfo;
import cn.jrry.wx.domain.WxWebAccessToken;
import cn.jrry.wx.service.WxInvokeService;
import cn.jrry.wx.service.WxMenuService;
import cn.jrry.wx.service.WxUserInfoService;
import com.google.common.base.Charsets;
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

import java.io.File;
import java.net.URLDecoder;
import java.util.Map;

@Controller(value = "wxMobileController")
@RequestMapping(path = "mobile")
public class MobileController {
    private static final Logger logger = LoggerFactory.getLogger(MobileController.class);
    private static final String CONTROLLER_CLASS_NAME = MobileController.class.getName();

    @Autowired
    private WxMenuService wxMenuService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private WxInvokeService wxInvokeService;

    @RequestMapping(path = "article",method = RequestMethod.GET)
    public String article(String code, String state, Model model) {
        logger.info("--> {}.{}({},{})", CONTROLLER_CLASS_NAME, "article", code, state);
        try {
            WxMenu wxMenu = wxMenuService.selectByPrimaryKey(Long.valueOf(state));
            String url = wxMenu.getNode_url();
            WxConfig wxConfig = wxInvokeService.getWxConfig(url,code,state);

            model.addAttribute("PAGE_CONTEXT_TITLE", wxMenu.getNode_name());
            model.addAttribute(wxConfig);

        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}({},{})", CONTROLLER_CLASS_NAME, "article", code, state);
        return "mobile/article";
    }


    @RequestMapping(path = "engineering",method = RequestMethod.GET)
    public String engineering(String code, String state, Model model) {
        logger.info("--> {}.{}({},{})", CONTROLLER_CLASS_NAME, "engineering", code, state);
        try {
            WxMenu wxMenu = wxMenuService.selectByPrimaryKey(Long.valueOf(state));
            String url = wxMenu.getNode_url();
            WxConfig wxConfig = wxInvokeService.getWxConfig(url,code,state);

            model.addAttribute("PAGE_CONTEXT_TITLE", wxMenu.getNode_name());
            model.addAttribute(wxConfig);

        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}({},{})", CONTROLLER_CLASS_NAME, "engineering", code, state);
        return "mobile/engineering";
    }

    @RequestMapping(path = "register",method = RequestMethod.GET)
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
            wxInvokeService.deleteWebAccessTokenByOpenid(wxWebAccessToken.getOpenid());
            wxInvokeService.insertWebAccessToken(wxWebAccessToken);

            WxMenu wxMenu = wxMenuService.selectByPrimaryKey(Long.valueOf(state));
            String url = wxMenu.getNode_url();
            WxConfig wxConfig = wxInvokeService.getWxConfig(url,code,state);

            model.addAttribute("PAGE_CONTEXT_TITLE", wxMenu.getNode_name());
            model.addAttribute(wxWebAccessToken);
            model.addAttribute(wxConfig);

        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}({},{})", CONTROLLER_CLASS_NAME, "register", code, state);
        return "mobile/register";
    }

    @RequestMapping(path = "register",method = RequestMethod.POST)
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

    @RequestMapping(path = "signature",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> signature(@RequestParam(required = true) String url) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "signature", url);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            WxConfig wxConfig = wxInvokeService.getWxConfig(URLDecoder.decode(url, Charsets.UTF_8.name()),null,null );
            result.put("success", true);
            result.put("message", "");
            result.put("data", wxConfig);

        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "signature");
        return result;
    }

    @RequestMapping(path = "engineering-progress",method = RequestMethod.GET)
    public String engineeringProgress(String code, String state, Model model) {
        logger.info("--> {}.{}({},{})", CONTROLLER_CLASS_NAME, "engineering-progress", code, state);
        try {
            WxMenu wxMenu = wxMenuService.selectByPrimaryKey(Long.valueOf(state));
            String url = wxMenu.getNode_url();
            logger.info("signature url: {}", url);

            WxConfig wxConfig = wxInvokeService.getWxConfig(url,code,state);

            model.addAttribute("PAGE_CONTEXT_TITLE", wxMenu.getNode_name());
            model.addAttribute(wxConfig);

        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}({},{})", CONTROLLER_CLASS_NAME, "engineering-progress", code, state);
        return "mobile/engineering_progress";
    }

    @RequestMapping(path = "engineering-progress",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(required = true) String serverId) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "engineering-progress-upload", serverId);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            File file = wxInvokeService.getMedia(serverId);
            logger.info("file path: {}",file.getAbsolutePath());
            result.put("success", true);
            result.put("message", "");
            result.put("data", serverId+","+file.getAbsolutePath());

        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "engineering-progress-upload");
        return result;
    }


    @RequestMapping(path = "user-info",method = RequestMethod.GET)
    public String userInfo(String code, String state, Model model) {
        logger.info("--> {}.{}({},{})", CONTROLLER_CLASS_NAME, "user-info", code, state);
        try {
            WxMenu wxMenu = wxMenuService.selectByPrimaryKey(Long.valueOf(state));
            String url = wxMenu.getNode_url();
            WxConfig wxConfig = wxInvokeService.getWxConfig(url,code,state);

            model.addAttribute("PAGE_CONTEXT_TITLE", wxMenu.getNode_name());
            model.addAttribute(wxConfig);

        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}({},{})", CONTROLLER_CLASS_NAME, "user-info", code, state);
        return "mobile/user_info";
    }

}
