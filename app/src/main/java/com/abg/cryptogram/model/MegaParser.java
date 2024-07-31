package com.abg.cryptogram.model;

public class MegaParser {
    public static String insertSlashes(String input) {
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        int currentLength = 0;
        for (String word : words) {
            if (currentLength + word.length() >= 9) {
                result.append("/").append(word).append(" ");
                currentLength = word.length();
            } else {
                result.append(word).append(' ');
                currentLength += word.length() + 1;
            }
        }

        String resultStr = result.toString().trim();
        if (resultStr.charAt(0)=='/') {
           return resultStr.substring(1);
        }

        return resultStr;
    }
}
