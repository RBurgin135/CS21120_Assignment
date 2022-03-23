package uk.ac.aber.cs21120.trie.test;
import uk.ac.aber.cs21120.trie.interfaces.IDictionary;
import uk.ac.aber.cs21120.trie.solution.Trie;
import uk.ac.aber.cs21120.trie.solution.HashSetDictionary;

import java.math.BigDecimal;

public class Main {
    private final WordList wordList = new WordList();
    private final int noOfTests = 500;

    public static void main(String[] args) {
        Main m = new Main();
        m.runTests();
    }

    /**
     * Method
     */
    private void runTests(){
        long[] trieTime;
        long[] hashSetTime;
        IDictionary testTrie, testHashSet;

        for(int i=4; i<18; i++){
            trieTime = new long[noOfTests];
            hashSetTime = new long[noOfTests];
            for(int x=0; x<noOfTests; x++) {
                testTrie = new Trie();
                testHashSet = new HashSetDictionary();
                trieTime[x] = runTest(i, testTrie);
                hashSetTime[x] = runTest(i, testHashSet);
            }
            compare(i, trieTime, hashSetTime);
        }
    }

    private long runTest(int wordLength, IDictionary dataStruct){
        //get words
        Object[] words;
        words = wordList.getWordsOfLength(wordLength,1000).toArray();
        //find time
        long start = System.nanoTime();
        for (Object word: words) {
            dataStruct.add((String)word);
        }
        return System.nanoTime() - start;
    }

    private void compare(int wordsLength, long[] trieTime, long[] hashSetTime){
        BigDecimal averageTrieTime = average(trieTime);
        BigDecimal averageHashSetTime = average(hashSetTime);
        System.out.println("For words length: "+wordsLength+
                "\n\tTrie: "+averageTrieTime.toString()+" ns"+
                "\n\tHashSet: "+averageHashSetTime.toString()+" ns");
    }

    private BigDecimal average(long[] x){
        BigDecimal bigDec = new BigDecimal(0);
        for(int i=0; i<noOfTests; i++){
            bigDec = bigDec.add(BigDecimal.valueOf(x[i]));
        }
        bigDec = bigDec.divide(BigDecimal.valueOf(noOfTests));
        return bigDec;
    }
}
