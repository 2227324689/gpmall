package com.gpmall.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gpmall.commons.tool.exception.ValidateException;
import com.gpmall.user.constants.SysRetCodeConstants;
import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Slf4j
@Builder
public class JwtTokenUtils {
    /**
     * 传输信息，必须是json格式
     */
    private String msg;
    /**
     * 所验证的jwt
     */
    @Setter private String token;

    {

    }

    /**
     * 生成jwt
     * https://blog.csdn.net/sinat_25235033/article/details/80324006
     */
    public String creatJwtToken () {
        msg = new AESUtil(msg).encrypt();//先对信息进行aes加密(防止被破解）
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("wlgzs").withExpiresAt(DateTime.now().plusDays(1).toDate())
                    .withClaim("user", msg)
                    .sign(Algorithm.HMAC256(createSecret()));
        } catch (Exception e) {
            log.info("jwt 生成问题:"+e);
            throw e;
        }
        log.info("加密后：" + token);
        return token;
    }
    /**
     * 解密jwt并验证是否正确
     */
    public String freeJwt () {
        DecodedJWT decodedJWT = null;
        try {

            //使用hmac256加密算法
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(createSecret()))
                    .withIssuer("wlgzs")
                    .build();
            decodedJWT = verifier.verify(token);
            log.info("签名人：" + decodedJWT.getIssuer() + " 加密方式：" + decodedJWT.getAlgorithm() + " 携带信息：" + decodedJWT.getClaim("user").asString());
        } catch (Exception e) {
            log.info("jwt解密出现错误，jwt或私钥或签证人不正确");
            throw new ValidateException(SysRetCodeConstants.TOKEN_VALID_FAILED.getCode(),SysRetCodeConstants.TOKEN_VALID_FAILED.getMessage());
        }
        //获得token的头部，载荷和签名，只对比头部和载荷
        String [] headPayload = token.split("\\.");
        //获得jwt解密后头部
        String header = decodedJWT.getHeader();
        //获得jwt解密后载荷
        String payload = decodedJWT.getPayload();
        if(!header.equals(headPayload[0]) && !payload.equals(headPayload[1])){
            throw new ValidateException(SysRetCodeConstants.TOKEN_VALID_FAILED.getCode(),SysRetCodeConstants.TOKEN_VALID_FAILED.getMessage());
        }
        return decodedJWT.getClaim("user").asString();
    }
    public String createSecret(){
        Properties properties = new Properties();
        //加载resource目录下的配置文件
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("secret.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.info("读取密钥文件错误:"+e);
        }
        return properties.getProperty("jwtsecret");
    }
}
