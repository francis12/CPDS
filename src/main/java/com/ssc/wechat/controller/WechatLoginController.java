package com.ssc.wechat.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssc.wechat.util.WechatUtil;
import com.ssc.wechat.vo.UserAccessToken;
import com.ssc.wechat.vo.WechatUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxaxxxxxxxxxxxxx&redirect_uri=https://www.yyzheng.cn/o2o/wechatlogin/logincheck&role_type=1&response_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则在这里将会获取到code，之后再通过code获取到access_token进而获取到用户信息
 *
 * @author yy
 *
 */
@Controller
@RequestMapping("wechatlogin")
public class WechatLoginController {
    private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);

    @RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin login get...");
        // 获取微信公众号传输过来的code，通过code可获取access_token,进而获取用户信息
        String code = request.getParameter("code");
        // 这个State可以用来传我们自定义的信息，方便程序调用，这里也可以不用
        // String roleType = request.getParameter("state");
        log.debug("weixin login code:" + code);
        WechatUser user = null;
        String openId = null;
        if (null != code) {
            UserAccessToken token;
            try {
                // 通过code获取access_token
                token = WechatUtil.getUserAccessToken(code);
                log.debug("weixin login token:" + token.toString());
                // 通过token获取accessToken
                String accessToken = token.getAccessToken();
                // 通过token获取openId
                openId = token.getOpenId();
                // 通过access_token和openId获取用户昵称等信息
                user = WechatUtil.getUserInfo(accessToken, openId);
                log.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId", openId);
            } catch (Exception e) {
                log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                e.printStackTrace();
            }
        }

        // ========todo begin ===========
        // 前面咱门获取到openid后，可以通过它去数据库判断该微信账号是否在我们网站里有对应的 账号了
        // 没有的话这里可以自动创建上，直接实现微信与咱门网站的无缝对接
        // ========todo end =============

        if (user != null) {
            // 获取到微信验证的信息后返回到指定的路由（需要自己设定）
            return "frontend/index";                                  /*******修改******/
        } else {
            return null;
        }
    }
}

