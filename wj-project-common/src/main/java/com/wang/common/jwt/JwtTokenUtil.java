package com.wang.common.jwt;

import com.wang.common.constants.Constants;
import com.wang.common.utils.CompressedUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
@Slf4j
public class JwtTokenUtil {


    /**
     * 构建jwt
     *
     * @param userName       账户名
     * @param adminUid       账户id
     * @param roleName       账户拥有角色名
     * @param audience       代表这个Jwt的接受对象
     * @param issuer         代表这个Jwt的签发主题
     * @param TTLMillis      jwt有效时间
     * @param base64Security 加密方式
     * @return
     */
    public String createJwt(String userName,String adminUid,String roleName,String audience,String issuer,long TTLMillis,String base64Security){
        // HS256是一种对称算法, 双方之间仅共享一个 密钥
        // 由于使用相同的密钥生成签名和验证签名, 因此必须注意确保密钥不被泄密
        // 也可以改成RS256: 非对称加密算法，使用私钥进行加密，使用公钥来验证Token的有效性
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT") // 设置header
                .claim("adminUid", adminUid) //设置payload
                .claim("role", roleName)
                .claim("createTime", now)
                .setSubject(userName)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm, signingKey); // 签名算法
        //添加Token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }


    public Claims parseJwt(String token,String base64Security){
        String realToken = token;
        try{
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(realToken).getBody();
            return claims;
        } catch(Exception e){
            log.info("解析token失败,失败原因{0}",e);
            return null;
        }
    }


    public String refreshToken(String token,String base64Security,long TTLMillis){
        String newToken;
        try{
            Claims claims = parseJwt(token, base64Security);
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("typ", "JWT") // 设置header
                    .setClaims(claims)
                    .setSubject(getUserName(token,base64Security))
                    .setIssuer(getIssuer(token,base64Security))
                    .setAudience(getAudience(token,base64Security))
                    .signWith(signatureAlgorithm, signingKey); // 签名算法
            if (TTLMillis>0){
                long expMillis = nowMillis+TTLMillis;
                jwtBuilder.setExpiration(new Date(expMillis)).setNotBefore(now);
            }
            newToken =  jwtBuilder.compact();
        } catch (Exception e){
            return null;
        }
        return newToken;
    }


    public String getUserName(String token,String base64Security){
        return parseJwt(token,base64Security).getSubject();
    }

    public String getAdminUid(String token,String base64Security){
        return parseJwt(token,base64Security).get(Constants.ADMIN_UID,String.class);
    }

    public boolean isExpiration(String token,String base64Security){
        return parseJwt(token,base64Security).getExpiration().before(new Date());
    }

    public Date getExpiration(String token,String base64Security){
        return parseJwt(token,base64Security).getExpiration();
    }

    public String getIssuer(String token,String base64Security){
        return parseJwt(token,base64Security).getIssuer();
    }

    public String getAudience(String token,String base64Security){
        return parseJwt(token,base64Security).getAudience();
    }


}
