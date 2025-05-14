package com.wang.web.config;

import com.wang.common.constants.Constants;
import com.wang.common.constants.RedisConst;
import com.wang.common.enums.RedisEnum;
import com.wang.common.jwt.Audience;
import com.wang.common.jwt.JwtTokenUtil;
import com.wang.common.object.entity.OnlineAdmin;
import com.wang.common.utils.*;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Value("${tokenHeader}")
    private String tokenHeader;

    @Value("${tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${audience.base64Secret}")
    private String base64Secret;

    @Autowired
    private Audience audience;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${excludeUrls}")
    private List<String> excludeUrls;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(tokenHeader);
        // 不过滤的路径
        if (isExcludeUrls(request)) {
            filterChain.doFilter(request,response);
            return;
        }
        if (StringUtils.isNotEmpty(authHeader) && authHeader.startsWith(tokenHead)){
            String token = authHeader.substring(tokenHead.length());
            String onlineAdminStr = redisUtil.get(RedisEnum.LOGIN_TOKEN_KEY.getRedisKey(RedisConst.SEGMENTATION,authHeader));
            OnlineAdmin onlineAdmin = JsonUtil.parseJson(onlineAdminStr, OnlineAdmin.class);
            if (onlineAdmin!=null && !jwtTokenUtil.isExpiration(token,audience.getBase64Secret())){
                // 获取过期时间，计算存活时间，和token刷新时间比较，如果存活时间小于刷新时间，则进行token的更新。
                // 猜想应该是在请求量大的情况下，为了token不频繁过期。
                Date expiresDate = jwtTokenUtil.getExpiration(token,audience.getBase64Secret());
                Date now = new Date(System.currentTimeMillis());
                Long aliveSeconds = DateUtils.getSecondBetweenTwo(expiresDate, now);
                if (aliveSeconds<audience.getRefreshSecond()){
                    String newToken = tokenHead + jwtTokenUtil.refreshToken(token, base64Secret, audience.getExpiresSecond());
                    authHeader = newToken;
                    CookieUtil.setCookie("Admin-Token",newToken,audience.getExpiresSecond(),false);
                    redisUtil.deleteKey(RedisEnum.LOGIN_TOKEN_KEY.getRedisKey(RedisConst.SEGMENTATION,token));
                    redisUtil.deleteKey(RedisEnum.LOGIN_UUID_KEY.getRedisKey(RedisConst.SEGMENTATION, onlineAdmin.getTokenId()));
                    // 设置新的线上管理员
                    onlineAdmin.setExpireTime(DateUtils.getDateStr(now,audience.getExpiresSecond()));
                    onlineAdmin.setLoginTime(DateUtils.getNowTime());
                    onlineAdmin.setToken(newToken);
                    String tokenId = StrUtils.getUUID();
                    onlineAdmin.setTokenId(tokenId);
                    redisUtil.setExTime(RedisEnum.LOGIN_TOKEN_KEY.getRedisKey(RedisConst.SEGMENTATION,newToken), JsonUtil.toJsonString(onlineAdmin),audience.getExpiresSecond() ,TimeUnit.SECONDS);
                    redisUtil.setExTime(RedisEnum.LOGIN_UUID_KEY.getRedisKey(RedisConst.SEGMENTATION,tokenId), token,audience.getExpiresSecond() ,TimeUnit.SECONDS);
                }
            } else{
                // 未获取到token就不在请求中设置adminUid等信息
                filterChain.doFilter(request,response);
                return;
            }
            String adminUid = jwtTokenUtil.getAdminUid(token, audience.getBase64Secret());
            String userName = jwtTokenUtil.getUserName(token, audience.getBase64Secret());
            request.setAttribute(Constants.ADMIN_UID,adminUid);
            request.setAttribute(Constants.USER_NAME,userName);
            request.setAttribute(Constants.TOKEN,authHeader);
            // todo: 验证token有效性
        }
        filterChain.doFilter(request,response);
    }

    private boolean isExcludeUrls(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        if (requestURI==null) return false;
        for (String excludeUrl : excludeUrls) {
            if (antPathMatcher.match(excludeUrl,requestURI)) return true;
        }
        return false;
    }
}
