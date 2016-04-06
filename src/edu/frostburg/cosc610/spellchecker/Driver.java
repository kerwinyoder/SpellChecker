package edu.frostburg.cosc610.spellchecker;

import java.util.Scanner;

/**
 * The driver for the Spell Checker project
 *
 * @author Kerwin Yoder
 * @version 2016.03.20
 */
public class Driver {

    /**
     * the main method used to start the program
     *
     * @param args command line arguments; exactly one argument is required<p>
     * The argument should be the name of the file containing the list of words
     * for the dictionary. Each word in the file must be on a separate line.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide the file name of the dictionary file as a command line argument.");
        }
        SpellChecker checker = new SpellChecker(args[0]);
        Scanner scanner = new Scanner(System.in);
        String[] suggestions;
        String word;
        System.out.println("Enter a word to check its spelling or type x to close the program.");
        word = scanner.nextLine();
        while (!word.equalsIgnoreCase("x")) {
            suggestions = checker.checkWord(word);
            int length = suggestions.length;
            if (length == 1 && suggestions[0].equalsIgnoreCase(word)) {
                System.out.println("Your spelling is correct.\r\n");
            } else if (length == 0) {
                System.out.printf("%s is not in the dictionary and no suggestions were found.%n%n", word);
            } else {
                System.out.println("\r\nSuggestions:");
                for (String suggestion : suggestions) {
                    System.out.println(suggestion);
                }
                System.out.println();
            }
            System.out.println("Enter a word to check its spelling or type x to close the program.");
            word = scanner.nextLine();
        }
    }
}
