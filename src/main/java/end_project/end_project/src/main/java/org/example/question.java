package org.example;

import java.util.List;

public class question {
    public String text;
    public List<String> options;
    public int correctIndex;

    public question(String text, List<String> options, int correctIndex) {
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
    }
}
