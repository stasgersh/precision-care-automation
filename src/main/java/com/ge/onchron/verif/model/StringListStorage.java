package com.ge.onchron.verif.model;

import java.util.ArrayList;
import java.util.List;

public class StringListStorage {
    private static List<String> stringList = new ArrayList<>();

    // Method to add a string to the list
    public static void addString(String value) {
        stringList.add(value);
    }

    // Method to get all stored strings
    public static List<String> getStrings() {
        return new ArrayList<>(stringList); // Return a copy to prevent modification
    }

    // Method to clear the list (if needed at the end of the test)
    public static void clearStrings() {
        stringList.clear();
    }
}
