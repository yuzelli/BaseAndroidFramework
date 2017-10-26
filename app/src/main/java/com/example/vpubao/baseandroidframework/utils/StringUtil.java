package com.example.vpubao.baseandroidframework.utils;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TREE on 2016/3/3.
 * 此类存放一些关于String的操作
 */
public class StringUtil {

    public static String MD5(String str) {
        MessageDigest md5 = null;
        byte[] byteArray;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byteArray = str.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static boolean isMobile(String mobile) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobile);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}

