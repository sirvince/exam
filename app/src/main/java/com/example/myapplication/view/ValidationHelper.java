package com.example.myapplication.view;

import com.google.android.material.textfield.TextInputLayout;

public class ValidationHelper {

    public static final int REQUIRED_AGE = 18;
    public static boolean isFieldEmpty(TextInputLayout textInputLayout) {
        String inputText = textInputLayout.getEditText().getText().toString().trim();
        if (inputText.isEmpty()) {
            textInputLayout.setError("This field is required!");
            return true;
        } else {
            textInputLayout.setError(null);
            return false;
        }
    }

    public static boolean isFieldValidate(TextInputLayout textInputLayout,String regex,String errorMessage) {
        String inputText = textInputLayout.getEditText().getText().toString().trim();

        if(!RegexHelper.isRegexValidate(inputText,regex)){
            textInputLayout.setError(errorMessage);
            return true;
        }else {
            textInputLayout.setError(null);
            return false;
        }
    }

    public static boolean isAgeValidate(int age) {
        return age < REQUIRED_AGE;
    }
}
