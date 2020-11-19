import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.TST;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SpellCheckTest<Value> {

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("DM_DEFAULT_ENCODING")
    public static void spellCheck(In string_array, TST data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("mydoc-checked.txt"));
        while (string_array.hasNextLine()) {
            try {
                String word = string_array.readString();
                if (data.get(word) == null) {
                    System.out.println(word + " did you mean:");
                    String[] data1 = new String[data.size()];
                    int i = 0;
                    for (Object s : data.keysWithPrefix(word.substring(0, 2))) {

                        try {
                            if (s.toString().length() > word.length()) break;
                            data1[i] = s.toString();
                            i++;
                        }
                        catch (java.lang.ArrayIndexOutOfBoundsException e) {
                            break;
                        }
                    }
                    for (int j = 0; j < data1.length; j++) {
                        if (data1[j] == null) break;
                        System.out.println((j + 1) + ". " + data1[j]);
                    }
                    System.out.println("0. Something else:");
                    Scanner input = new Scanner(System.in);
                    int chosenValue = input.nextInt();
                    if (chosenValue == 1) {
                        writer.write(data1[0] + "\n");
                    }
                    else if (chosenValue == 2) {
                        writer.write(data1[1] + "\n");
                    }
                    else if (chosenValue == 3) {
                        writer.write(data1[2] + "\n");
                    }
                    else {
                        System.out.println("What word did you want?" + "\n");
                        String userInput = input.next();
                        writer.write(userInput + "\n");
                    }
                }
                else {
                    writer.write(word + "\n");
                }
            }
            catch (java.util.NoSuchElementException e) {
                break;
            }
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {

        TST<Integer> st = new TST<Integer>();

        In words = new In(args[0]);
        In userInput = new In(args[1]);

        for (int i = 0; words.hasNextLine(); i++) {
            String key = words.readString();
            st.put(key, i);
        }
        spellCheck(userInput, st);

    }
}


