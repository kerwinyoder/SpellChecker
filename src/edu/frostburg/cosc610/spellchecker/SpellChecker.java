package edu.frostburg.cosc610.spellchecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Spell Checker that checks for four types of common errors:
 * <p>
 * 1. Swapping letters (e.g. bxo --&gt; box)
 * <p>
 * 2. Inserting wrong letters (e.g. bokx --&gt; box)
 * <p>
 * 3. Missing characters (e.g. bx --&gt; box)
 * <p>
 * 4. Wrong characters (e.g. bux --&gt; box)
 * <p>
 * Note: The Spell Checker is not case sensitive.
 *
 * @author Kerwin Yoder
 * @version 2016.03.19
 */
public class SpellChecker {

    private HashSet<String> dictionary;
    private HashSet<String> suggestions;

    /**
     * Creates a new SpellChecker using the file with the given name to create
     * the dictionary.
     *
     * @param file the name of the file containing the list of words
     */
    public SpellChecker(String file) {
        suggestions = new HashSet<>(30);
        try {
            initializeDictionary(file);
        } catch (FileNotFoundException ex) {
            System.out.printf("The file, %s, could not be found.", file);
            System.exit(1);
        }
    }

    /**
     * Checks if the given word is spelled correctly. See the class description
     * for details about which types of misspellings are detected and corrected.
     *
     * @param word the word to check for correct spelling
     * @return a list of suggested words if the word is spelled incorrectly or
     * the word itself if it is spelled correctly
     */
    public String[] checkWord(String word) {
        if (word == null) {
            throw new NullPointerException("The word argument in checkWordis null");
        }
        word = word.toLowerCase();
        if (dictionary.contains(word)) {
            return new String[]{word};
        }
        suggestions.clear();
        swappedLetters(word);
        insertedLetters(word);
        missingLetters(word);
        wrongLetters(word);
        String[] temp = suggestions.toArray(String.class);
        Arrays.sort(temp);
        return temp;
    }

    /*
     * Checks for swapped letters (e.g. bxo --> box) and adds any suggestions to
     * the suggestions list.
     *
     * @param word the word to check for swapped letters
     */
    private void swappedLetters(String word) {
        if (word == null) {
            throw new NullPointerException("The word argument in swappedLetters is null");
        }
        StringBuilder builder = new StringBuilder();
        int length = word.length();
        String temp;
        for (int i = 1; i < length; ++i) {
            builder.setLength(0);
            builder.append(word.substring(0, i - 1));
            builder.append(word.charAt(i));
            builder.append(word.charAt(i - 1));
            builder.append(word.substring(i + 1));
            temp = builder.toString();
            if (dictionary.contains(temp)) {
                suggestions.add(temp);
            }
        }
    }

    /*
     * Checks for inserted wrong letters (e.g. bokx --> box)
     *
     * @param word the word to check for inserted wrong letters
     */
    private void insertedLetters(String word) {
        if (word == null) {
            throw new NullPointerException("The word argument is missingLetters is null");
        }
        StringBuilder builder = new StringBuilder();
        int length = word.length();
        String temp;
        for (int i = 0; i < length; ++i) {
            builder.setLength(0);
            builder.append(word.substring(0, i));
            builder.append(word.substring(i + 1));
            temp = builder.toString();
            if (dictionary.contains(temp)) {
                suggestions.add(temp);
            }
        }
    }

    /*
     * Checks for missing letters (e.g. bx --> box)
     *
     * @param word the word to check for missing letters
     */
    private void missingLetters(String word) {
        if (word == null) {
            throw new NullPointerException("The word argument in insertedLetters is null");
        }
        StringBuilder builder = new StringBuilder();
        int length = word.length();
        String temp;
        for (int i = 0; i <= length; ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                builder.setLength(0);
                builder.append(word.substring(0, i));
                builder.append(c);
                builder.append(word.substring(i));
                temp = builder.toString();
                if (dictionary.contains(temp)) {
                    suggestions.add(temp);
                }
            }
        }
    }

    /*
     * Checks for wrong letters (e.g. bux --> box)
     *
     * @param word the word to check for wrong letters
     */
    private void wrongLetters(String word) {
        if (word == null) {
            throw new NullPointerException("The word argument is wrongLetters is null");
        }
        StringBuilder builder = new StringBuilder();
        int length = word.length();
        String temp;
        for (int i = 0; i < length; ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                builder.setLength(0);
                builder.append(word.substring(0, i));
                builder.append(c);
                builder.append(word.substring(i + 1));
                temp = builder.toString();
                if (dictionary.contains(temp)) {
                    suggestions.add(temp);
                }
            }

        }
    }

    /*
     * Initializes the dictionary using words from the file with the given name. The file is assumed to be in the working directory of the project.
     * @param file the file containing the list of words to use in the dictionary; each word should be on a separate line.
     * @throws FileNotFoundException if the file is not found
     */
    private void initializeDictionary(String file) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>(50000);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            String word = reader.readLine();
            while (word != null) {
                list.add(word.toLowerCase());
                word = reader.readLine();
            }
            dictionary = new HashSet<>(list.size());
            for (String temp : list) {
                dictionary.add(temp);
            }
        } catch (IOException ex) {
            System.out.printf("There was a problem while reading %s.", file);
            System.exit(1);
        }
    }
}
