package com.gpmall.pay.biz.payment.channel.wechatpay;


import java.util.Base64;


public class Base64Util {


    public static byte[] decode(String encodedText){
        final Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(encodedText);
    }

    public static String encode(byte[] data){
        final Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            final Base64.Encoder encoder = Base64.getEncoder();
            final String text = "字串文字";
            final byte[] textByte = text.getBytes("UTF-8");
            //编码
            final String encodedText = encoder.encodeToString(textByte);
            System.out.println(encodedText);
            //解码
            System.out.println(new String(decoder.decode(encodedText), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}