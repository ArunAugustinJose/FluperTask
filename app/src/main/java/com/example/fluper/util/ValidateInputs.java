package com.example.fluper.util;


import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * ValidateInputs class has different static methods, to validate different types of user Inputs
 **/

public class ValidateInputs {
    
    private static String blockCharacters = "[$&+~;=\\\\?@|/'<>^*()%!-]";
    private static String blockCharacters2 = "[~=\\\\?@/<>^*]";


    //*********** Validate Email Address ********//

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    
    //*********** Validate Number Input ********//

    public static boolean isValidNumber(String number) {

        String regExpn = "^[0-9]{1,24}$";
        CharSequence inputStr = number;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    
    

    //*********** Validate Any Input ********//

    public static boolean isValidInput(String input) {
        String regExpn = "(.*?)?((?:[a-z][a-z][0-9]+))";
        if (input.equalsIgnoreCase(""))
            return false;
        
        CharSequence inputStr = input;
        Pattern pattern = Pattern.compile(blockCharacters2, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return !pattern.matcher(inputStr).find();
    }


    //*********** Validate Search Query ********//

    public static boolean isValidSearchQuery(String query) {

        String regExpn = "^([a-zA-Z]{1,24})?([a-zA-Z][a-zA-Z0-9_]{1,24})$";
        CharSequence inputStr = query;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

}

