package com.example.forum.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author saysky
 * @date 2021/3/20
 */

public class RegexUtil {

    /**
     * 判断Email合法性
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null) {
            return false;
        }
        String rule = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
