package com.deveek.common.support;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StringGenerator {
    public static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    
    public static final String DIGITS_CHARACTERS = "0123456789";
    
    public static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/`~";
    
    public static final Random RANDOM = new SecureRandom();
    
    public static String genVerifyCode() {
        return genRandomString(6, DIGITS_CHARACTERS);
    }
    
    public static String genRandomString(int length, String... charCategoriesArr) {
        List<String> charCategories = Arrays.stream(charCategoriesArr).toList();
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(RANDOM.nextInt(charCategories.size()));
            int position = RANDOM.nextInt(charCategory.length());
            result.append(charCategory.charAt(position));
        }

        return result.toString();
    }
}