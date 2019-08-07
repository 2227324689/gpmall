package com.gpmall.user.utils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/22-17:41
 */
@Slf4j
public class AESUtil {
    //加密或解密内容
    @Setter
    private String content;
    //加密密钥
    private String secret;

    public AESUtil(String content) {
        this.content = content;
        this.secret = "iwofnoadnsa922342mnjaolkdsao9423242niosadopa_a02402sad";
    }

    /**
     * 加密
     * @return 加密后内容
     */
    public String encrypt () {
        Key key = getKey();
        byte[] result = null;
        try{
            //创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            //初始化为加密模式
            cipher.init(Cipher.ENCRYPT_MODE,key);
            //加密
            result = cipher.doFinal(content.getBytes("UTF-8"));
        } catch (Exception e) {
            log.info("aes加密出错:"+e);
        }
        //将二进制转换成16进制
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            String hex = Integer.toHexString(result[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return  sb.toString();
    }

    /**
     * 解密
     * @return 解密后内容
     */
    public String decrypt () {
        //将16进制转为二进制
        if (content.length() < 1)
            return null;
        byte[] result = new byte[content.length()/2];
        for (int i = 0;i< content.length()/2; i++) {
            int high = Integer.parseInt(content.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(content.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }

        Key key = getKey();
        byte[] decrypt = null;
        try{
            //创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            //初始化为解密模式
            cipher.init(Cipher.DECRYPT_MODE,key);
            //解密
            decrypt = cipher.doFinal(result);
        } catch (Exception e) {
            log.info("aes解密出错："+e);
        }
        assert decrypt != null;
        return new String(decrypt);
    }

    /**
     * 根据私钥内容获得私钥
     */
    private Key getKey () {
        SecretKey key = null;
        try {
            //创建密钥生成器
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            //初始化密钥
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(secret.getBytes());
            generator.init(128,random);
            //生成密钥
            key = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return key;
    }

    public static void main(String[] args) {
        AESUtil aesUtil=new AESUtil("Hello");
        String ec=aesUtil.encrypt();
        System.out.println(ec);
        System.out.println(new AESUtil(ec).decrypt());
    }
}
