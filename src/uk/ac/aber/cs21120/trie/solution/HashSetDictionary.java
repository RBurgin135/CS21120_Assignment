package uk.ac.aber.cs21120.trie.solution;

import uk.ac.aber.cs21120.trie.interfaces.IDictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashSetDictionary implements IDictionary {
    private final Set<String> data;

    /**
     * Basic constructor, instantiates a new hashset for holding the .
     */
    public HashSetDictionary(){
        data = new HashSet<>();
    }

    @Override
    public void add(String s) {
        data.add(s);
    }

    @Override
    public boolean contains(String s) {
        return data.contains(s);
    }

    @Override
    public void delete(String s) {
        data.remove(s);
    }

    @Override
    public int count() {
        return data.size();
    }

    @Override
    public List<String> contents() {
        return new ArrayList<>(data);
    }
}
