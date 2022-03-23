package uk.ac.aber.cs21120.trie.interfaces;

import java.util.List;

public interface IDictionary {
    /**
     * Add a string to the dictionary
     * @param s string to add
     */
    void add(String s);

    /**
     * Return true if a string is in the dictionary
     * @param s string to check
     * @return true if in dictionary
     */
    boolean contains(String s);

    /**
     * Delete a string from the dictionary
     * @param s string to delete
     */
    void delete(String s);

    /**
     * Return the number of strings in the dictionary
     * @return count of strings in dictionary
     */
    int count();

    /**
     * Return a list of the strings contained in the dictionary;
     * not guaranteed to be sorted but may be sorted in some implementations.
     * @return list of strings in dictionary
     */
    List<String> contents();
}
