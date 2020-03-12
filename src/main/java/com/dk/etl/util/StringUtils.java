package com.dk.etl.util;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: HarlanW
 * @Date: 2019/4/4 0004
 * @Version: 1.0
 */
public class StringUtils {
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    private StringUtils() {
        // to do nothing
    }

    /**
     * 安全的进行字符串 format,如target为aa$sbcc,params为BB,则替换成aaBBbcc
     *
     * @param target 目标字符串
     * @param params format 参数
     * @return format 后的
     */
    public static String format(String target, Object... params) {
        if (target.contains("%s") && ArrayUtils.isNotEmpty(params)) {
            return String.format(target, params);
        }
        return target;
    }

    /**
     * 判断字符串是否为空,判断单个空字符串，不包括两个以上
     * @param cs
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串不为空
     * @param cs
     * @return
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 下划线转驼峰
     * @param content
     * @return
     */
    public static String lineToHump(String content){
        content = content.toLowerCase();
        Matcher matcher = linePattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String decodeBase64(String encode){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(encode.getBytes());
        return new String(decode);
    }
}
