package com.example.myapplication.view;

import java.util.regex.Pattern;

public class RegexHelper {
    public static final String REGEX_EMAIL_ADDRESS = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String REGEX_MOBILE_NUMBER = "^(639|09)\\d{9}$";



    public static boolean isRegexValidate(String input, String regex){
        Pattern pattern = Pattern.compile(regex);
        boolean result =  pattern.matcher(input).matches();
        System.out.println(result);
        return result;
    }
}
