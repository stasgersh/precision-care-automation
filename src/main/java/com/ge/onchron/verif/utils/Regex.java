package com.ge.onchron.verif.utils;

import com.google.common.collect.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    private static final String NO_MATCH_FOUND = "no-match-found";

    public static List<String> findKeywords(String input, List<String> keywords) {
        List<String> matches = Lists.newArrayList();

        String regex = keywords.stream()
                .map(Pattern::quote)
                .reduce((a, b) -> a + "|" + b)
                .orElse("");

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            matches.add(matcher.group());
        }

        return matches;
    }

    /**
     * Extract string from regex and fetch data by group id
     *
     * @param regex The regular expression pattern.
     * @param input The string to search in.
     * @param fetchGroupId The group to be fetched from string.
     *
     * @return data if a match is found, no matched data otherwise.
     */
    public static String fetchTextGroupByRegex(String regex, String input, int fetchGroupId) {

        Matcher matcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(input);

        if (matcher.find()) {
            try {
                String result = matcher.group(fetchGroupId);
                System.out.println(STR."Extracted group value : \{result}");
                return result;
            } catch (IndexOutOfBoundsException e) {
                System.err.println(STR."Invalid group ID: \{fetchGroupId}");
                return "INVALID_GROUP_ID";
            }
        } else {
            return NO_MATCH_FOUND;
        }
    }

    public static boolean validate(String regex, String input, List<String> expectedKeywords) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        Set<String> found = new HashSet<>();

        while (matcher.find()) {
            found.add(matcher.group().toLowerCase());
        }

        return expectedKeywords.stream()
                .map(String::toLowerCase)
                .allMatch(found::contains);
    }

    /**
     * Checks if the given regex matches any part of the input string.
     *
     * @param regex The regular expression pattern.
     * @param input The string to search in.
     * @return true if a match is found, false otherwise.
     */
    public static boolean isMatchFound(String regex, String input) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

}
