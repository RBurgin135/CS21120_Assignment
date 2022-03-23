package uk.ac.aber.cs21120.trie.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A word list - it's an array list of strings that initialises itself
 * with words. Two possible constructors - the one with no arguments just
 * reads all the words, but if you provide an integer argument only words
 * of length less than that argument will be read.
 *
 */
public class WordList extends ArrayList<String> {
    public WordList() {
        super();
        String homeDirectory = System.getProperty("user.home");
        String file = homeDirectory+"\\word.list.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            try {
                while ((line = br.readLine()) != null) {
                    add(line);
                }
            } catch (IOException e){
                throw new RuntimeException("bad file?");
            }
        } catch(FileNotFoundException e){
            throw new RuntimeException("cannot find file "+file);
        }
    }

    /**
     * return a new ArrayList of strings, consisting of "count" words of the given length.
     * Will throw an exception if there aren't enough words in the list. The list
     * will be in a random order.
     * @param length
     * @param count
     * @return "count" words of "length" letters
     */
    public ArrayList<String> getWordsOfLength(int length, int count) {
        Collections.shuffle(this);
        ArrayList<String> res = new ArrayList<>();
        for(String s: this){
            if(s.length() == length){
                res.add(s);
                if(res.size() == count)
                    return res;
            }
        }
        throw new RuntimeException(
                String.format("Not enough words of length %d",length));
    }
}
