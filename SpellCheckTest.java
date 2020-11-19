import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SpellCheckTest<Value> {

    public static void spellCheck(In string_array, TST data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("mydoc-checked.txt"));
        while (string_array.hasNextLine()) {
            try {
                String word = string_array.readString();
                if (!data.contains(word)) {
                    System.out.println(word + " did you mean:");
                    Queue<String> suggestions = new Queue<>();

                    for (Object thingy : data.keysWithPrefix(word.substring(0, 2))) {
                        String s = (String) thingy;
                        if (s.length() > word.length() + 2) continue;
                        suggestions.enqueue(s);
                    }
                    String[] strSuggestions = new String[suggestions.size()];
                    for (int i = 0; i < strSuggestions.length; i++) {
                        strSuggestions[i] = suggestions.dequeue();
                    }
                    int i = 1;
                    for (String s : strSuggestions) {
                        System.out.println(i++ + ". " + s);
                    }
                    System.out.println("0. Something else:");
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
                else {
                    writer.write(word + " ");
                }
            }
            catch (java.util.NoSuchElementException e) {
                break;
            }
        }
        writer.close();
    }

    public static void main(String[] args) {

        TST<Integer> st = new TST<Integer>();

        In words = new In(args[0]);     // words.txt
        In userInput = new In(args[1]); // document.txt

        for (int i = 0; words.hasNextLine(); i++) {
            String key = words.readString();
            st.put(key, i);
        }
        try {
            spellCheck(userInput, st);
        }
        catch (Exception e) {
            StdOut.println("Suck my ass\n");
        }
    }
}


