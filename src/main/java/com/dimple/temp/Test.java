package com.dimple.temp;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class Test {
    public static String getAlgorithm() {
        return "HmacSHA256";
    }

    public static String encode(String data, String key,boolean hex,boolean urlSafe) {
        try {
            byte [] byteKey = string2byte(key, hex, urlSafe);
            SecretKey secretKey = new SecretKeySpec(byteKey, getAlgorithm());
            Mac mac = Mac.getInstance(getAlgorithm());
            mac.init(secretKey);
            byte[] bytes = mac.doFinal(data.getBytes());
            return byte2string(bytes, hex, urlSafe);
        }catch (Exception ex){
            return data;
        }
    }

    public static String encode(String data, String key){
        return encode(data,key,false,true);
    }

    public static String byte2string(byte[] data,boolean hex,boolean urlSafe){
        return urlSafe? Base64.encodeBase64URLSafeString(data):Base64.encodeBase64String(data);
    }

    public static byte [] string2byte(String data,boolean hex,boolean urlSafe){
        return Base64.decodeBase64(data);
    }

    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        String appkey = "152999073894506066";
        String appSecret = "BoplpsF94k7ZVYpP09viXX6-fzWzUcJA6bBKNeR_jnc";
        URI requestURI = URI.create(
                "http://www-dev.ctyun.cn/gw/worksheet/ctyun/ListProduct");
        String now = String.valueOf(System.currentTimeMillis());
        //String now = "1566542201080";
        String uri = requestURI.getRawPath();
        String queryString = requestURI.getRawQuery();
        if (queryString!=null&&queryString.length()!=0) {
            uri += "?" + queryString;
        }

        StringBuffer toSign = new StringBuffer();
        toSign.append(appkey).append("\n");
        toSign.append(now).append("\n");
        toSign.append(uri);

        String signed = encode(toSign.toString(), appSecret);
        System.out.println("签名文本" + toSign + "生成的签名是" + signed);

        // 打开连接
        URL realurl;
        realurl = new URL(
                "http://www-dev.ctyun.cn/gw/worksheet/ctyun/ListProduct");
        URLConnection connection = realurl.openConnection();


        connection.setRequestProperty("x-alogic-now", now);
        connection.setRequestProperty("x-alogic-app", appkey);
        connection.setRequestProperty("x-alogic-ac", "app");
        connection.setRequestProperty("x-alogic-signature", signed);

        connection.setConnectTimeout(6000);
        connection.setReadTimeout(6000);

        connection.connect();
        String result = "";
        String line = "";
        StringBuffer sb = new StringBuffer();
        BufferedReader in = null;
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        result = sb.toString();

        System.out.println("result is   " + result);
    }
}
