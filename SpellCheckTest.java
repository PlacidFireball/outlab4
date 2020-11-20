/*
Code written by: Jared Weiss and Jacob Clostio
NetID's:         t95g284 and z32t832
Completed: 11/19/2020 @ 5:00 P.M.
    This code was written for Outlab 4 of CSCI 232 at
    Montana State University. It implements a Ternary Search
    Tree to store the contents of an input file (words.txt).
    It then checks all the words in another input file (mydoc.txt)
    and (if a word is misspelt) allows the user to correct their mistake(s).
    It is not a completely robust spell checker, it checks for a misspelt
    character at every position in the word, and also checks if shortening the
    word by one character would produce a correct word.
*/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class SpellCheckTest<Value> {

    public static void spellCheck(In stringArray, TST data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("mydoc-checked.txt"));
        String[] words = stringArray.readAllStrings();
        for (String word : words) {     // for each word in the file
            if (!data.contains(word)) {     // check to see if it's in the TST
                System.out.println(word + " did you mean:");
                LinkedList<String> suggestions
                        = new LinkedList<>();    // linked list for storing suggestions
                String[] filters = new String[word.length()];           // array of filters
                for (int i = 0; i < word.length();
                     i++) {               // make all filters that would change one letter
                    filters[i] = word.substring(0, i) + "." +           // example: doog
                            word.substring(i + 1,
                                           word.length());       // filters: .oog, d.og, do.g doo. <- where '.' is the wildcard
                }
                // check all the words that we could get by deleting the last character
                for (Object thingy : data
                        .keysWithPrefix(word.substring(0, word.length() - 1))) {
                    String s = (String) thingy;
                    if (s.length() > word.length()) continue;
                    if (!suggestions.contains(s)) suggestions.addLast(s);
                }
                // check all the words we would get by changing one letter
                for (String filter : filters) {
                    for (Object thingy : data
                            .keysThatMatch(filter)) {
                        String s = (String) thingy;
                        if (s.length() > word.length()) continue;
                        if (!suggestions.contains(s)) suggestions.addLast(s);
                    }
                }
                // check all the words we would get by making the word one character shorter at the end
                for (Object thingy : data
                        .keysThatMatch(word.substring(0, word.length() - 2) + ".")) {
                    String s = (String) thingy;
                    if (s.length() > word.length()) continue;
                    if (!suggestions.contains(s)) suggestions.addLast(s);
                }

                // convert suggestions to a String[]
                String[] strSuggestions = (String[]) suggestions.toArray();
                // print off all of the suggestions we found
                int i = 1;
                for (String s : strSuggestions) {
                    System.out.println(i++ + ". " + s);
                }
                System.out.println("0. Something else:"); // offer another option
                // grab user input
                Scanner input = new Scanner(System.in);
                int chosenValue = input.nextInt();
                if (chosenValue > 0 && chosenValue < strSuggestions.length)
                    writer.write(strSuggestions[chosenValue - 1] + " ");
                else {
                    System.out.println("What word did you want?" + "\n");
                    String userInput = input.next();
                    writer.write(userInput + " ");
                }
            }
            // if the word was already in the dictionary, just write it to the file
            else {
                writer.write(word + " ");
            }
        }
        writer.close();
    }

    public static void main(String[] args) {
        // Initial Setup
        TST<Integer> st = new TST<Integer>();
        In words = new In(args[0]);     // words.txt
        In document = new In(args[1]);  // document.txt

        // Read in the words
        String[] strWords = words.readAllStrings(); // read in all the words
        for (String word : strWords) {
            st.put(word, 0);    // and add them to the TST
        }
        // Spellcheck the document
        try {
            spellCheck(document, st);  // attempt to run spell check
        }
        catch (Exception e) {   // handle FileWriter having an aneurism
            StdOut.println("This exception is stupid :)\n");
        }
        // close the files
        words.close();
        document.close();
    }
}


