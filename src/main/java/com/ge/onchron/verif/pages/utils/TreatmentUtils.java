package com.ge.onchron.verif.pages.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreatmentUtils {


    private final static String dynamicClass = "ClpTooltipContent-module_gray";

    public static String extractStaticContent(String html) {
        Document doc = Jsoup.parse(html);
        doc.select("." + dynamicClass).remove(); // remove dynamic part
        return doc.text().replaceAll("\\s+", " ").trim();
    }

    public static Set<String> extractKeywords(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByClass(dynamicClass);
        Set<String> words = new HashSet<>();
        Pattern wordPattern = Pattern.compile("\\b\\w+\\b");

        for (var el : elements) {
            Matcher matcher = wordPattern.matcher(el.text().toLowerCase());
            while (matcher.find()) {
                words.add(matcher.group());
            }
        }
        return words;
    }



}
