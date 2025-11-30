package ru.kaysiodl.services;

public class TextCounter {
    public int countCharacters(String text, boolean excludeSpaces) {
        if (text == null || text.isEmpty()) return 0;
        return excludeSpaces ? text.replaceAll("\\s", "").length() : text.length();
    }

    public int countWords(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        return text.trim().split("\\s+").length;
    }

    public int countLines(String text) {
        if (text == null || text.isEmpty()) return 0;
        return text.split("\r\n|\r|\n").length;
    }
}

