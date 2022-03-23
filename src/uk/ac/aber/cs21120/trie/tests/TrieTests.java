package uk.ac.aber.cs21120.trie.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.trie.interfaces.IDictionary;
import uk.ac.aber.cs21120.trie.solution.Trie;

import java.util.List;

public class TrieTests {
    /**
     * This is a very simple private method which made it much easier to cut and paste code from
     * the hash set uk.ac.aber.cs21120.trie.tests! JUnit makes it quite difficult to do anything more sensible with
     * inheritance.
     * @return a new trie
     */
    private IDictionary getTestObject(){
        return new Trie();
    }


    /**
     * Words used in the contents() uk.ac.aber.cs21120.trie.tests.
     */
    String[] contentsWords = {"DEAL", "DEN", "DO", "DOG", "DUG", "FAN", "FEN", "FOOT", "FUNK"};


    /**
     * Make sure that an empty Trie contains no data, not even an empty string.
     */
    @Test
    public void testBlank() {
        IDictionary d = getTestObject();
        Assertions.assertEquals(0,d.count());
        Assertions.assertFalse(d.contains(""));
        Assertions.assertFalse(d.contains("a"));
        Assertions.assertFalse(d.contains("aa"));
        Assertions.assertFalse(d.contains("ab"));
    }

    /**
     * Try adding an empty string - that should work (the root node should become a leaf)
     */
    @Test
    public void testAddEmpty(){
        IDictionary d = getTestObject();
        d.add("");
        Assertions.assertEquals(1,d.count());
        Assertions.assertTrue(d.contains(""));
        Assertions.assertFalse(d.contains("a"));
    }

    /**
     * Add a single character.
     */
    @Test
    public void testAddShort(){
        IDictionary d = getTestObject();
        d.add("a");
        Assertions.assertTrue(d.contains("a"));
        Assertions.assertFalse(d.contains("aa"));
        Assertions.assertFalse(d.contains("ab"));
    }

    /**
     * Now add two three two-character strings. This should result in the following structure:
     * root
     * |
     * A
     * |- A+
     * |- B+
     * |- C+
     * where nodes marked + are leaves.
     */
    @Test
    public void testTwo(){
        IDictionary d = getTestObject();
        d.add("aa");
        d.add("ab");
        d.add("ac");
        Assertions.assertTrue(d.contains("aa"));
        Assertions.assertTrue(d.contains("ab"));
        Assertions.assertTrue(d.contains("ac"));
        Assertions.assertFalse(d.contains("a"));
        Assertions.assertFalse(d.contains("ba"));
        Assertions.assertFalse(d.contains("b"));
    }

    /**
     * Adding the same word multiple times should have no effect.
     */
    @Test
    public void testRepeat(){
        IDictionary d = getTestObject();
        d.add("foo");
        d.add("bar");
        d.add("bar");
        d.add("bar");
        Assertions.assertEquals(2,d.count());
        Assertions.assertTrue(d.contains("foo"));
        Assertions.assertTrue(d.contains("bar"));
    }

    /**
     * Case dependence check - "WORD" is not the same as "word"
     */
    @Test
    public void testCases(){
        IDictionary d = getTestObject();
        d.add("A");
        d.add("ab");
        d.add("AC");
        Assertions.assertFalse(d.contains("a"));
        Assertions.assertFalse(d.contains("AB"));
        Assertions.assertTrue(d.contains("AC"));
        Assertions.assertTrue(d.contains("A"));
        Assertions.assertTrue(d.contains("ab"));
        Assertions.assertFalse(d.contains("Ac"));
    }

    /**
     * Here we test with the example data from the brief.
     * We can build uk.ac.aber.cs21120.trie.tests against that structure.
     */
    @Test
    public void testWords(){
        String[] words = {"DEAL", "DEN", "DO", "DOG", "DUG", "FAN", "FEN", "FOOT", "FUNK"};
        IDictionary d = getTestObject();
        for(String x:words){
            d.add(x);
        }
        // make sure we have as many words as are in the list.
        Assertions.assertEquals(words.length, d.count());
        // test each individual word - if a particular word fails, that will help you debug.
        for(String x:words){
            Assertions.assertTrue(d.contains(x));
        }
        // and check some words which should not be there
        Assertions.assertFalse(d.contains("FUN")); // the N should not be a leaf
        Assertions.assertFalse(d.contains("DEALT")); // there should be no T child of the L
        Assertions.assertFalse(d.contains(""));
    }

    /**
     * This is called for every test, making a new Trie filled with the word list above.
     * Naturally if the Trie is faulty this won't work.
     * @return filled trie
     */
    private IDictionary makeTrieWithWords(){
        IDictionary d = new Trie();
        for(String x:contentsWords){
            d.add(x);
        }
        return d;
    }


    /**
     * Check that the contents list has all the words that are in the trie.
     */
    @Test
    public void testContentsHasSameWords() {
        IDictionary d = makeTrieWithWords();
        // now get the contents as a list
        List<String> list = d.contents();
        // and check it contains the same items
        for(String x:contentsWords){
            Assertions.assertTrue(list.contains(x));
        }
    }

    /**
     * Check that the length of the contents list is the same as the number of stored strings
     * in the trie.
     */
    @Test
    public void testContentsLengthCorrect() {
        IDictionary d = makeTrieWithWords();
        // now get the contents as a list
        List<String> list = d.contents();
        // the list must be the same length as the original list
        Assertions.assertEquals(contentsWords.length, list.size());
    }

    /**
     * Check that the contents method returns a non-null list reference.
     */
    @Test
    public void testContentsListReturned(){
        IDictionary d = makeTrieWithWords();
        Assertions.assertNotNull(d.contents());

    }

    /**
     * Check that the contents list is of non-zero length
     */
    @Test
    public void testContentsNonZeroLength() {
        IDictionary d = makeTrieWithWords();
        List<String> list = d.contents();
        Assertions.assertNotEquals(0, list.size());
    }

    /**
     * Check that the contents list contains at least one word which is in the trie.
     */
    @Test
    public void testContentsHasAtLeastOneWord(){
        IDictionary d = makeTrieWithWords();
        List<String> list = d.contents();
        boolean ok=false;
        for(String w: contentsWords){
            if(list.contains(w))ok=true;
        }
        Assertions.assertTrue(ok);
    }


    /**
     * This is a "general purpose" deletion test: it is a "black box" test
     * that knows nothing about the underlying data and is used for both
     * Trie and HashSetDictionary.
     */

    @Test
    public void testDeleteBlackBox(){
        String[] words = {"aa","ab","ac","Hello","Help","Hell","hell","cake","canoe","fish","filehandler"};
        IDictionary d = new Trie();
        for(String x:words){
            d.add(x);
        }
        Assertions.assertEquals(words.length, d.count());

        d.delete("Hell");
        Assertions.assertEquals(words.length-1, d.count());
        Assertions.assertFalse(d.contains("Hell"));
        Assertions.assertTrue(d.contains("hell"));
        Assertions.assertTrue(d.contains("Hello"));
        d.delete("aa");
        Assertions.assertEquals(words.length-2, d.count());
        Assertions.assertFalse(d.contains("aa"));

        for(String x: words){
            if(!x.equals("aa") && !x.equals("Hell"))Assertions.assertTrue(d.contains(x));
        }

        d.delete("snark");
        Assertions.assertEquals(words.length-2, d.count());
        d.add("snark");
        Assertions.assertEquals(words.length-1, d.count());
        Assertions.assertTrue(d.contains("snark"));
        d.delete("snark");
        Assertions.assertEquals(words.length-2, d.count());
        Assertions.assertFalse(d.contains("snark"));
        for(String x: words){
            if(!x.equals("aa") && !x.equals("Hell"))Assertions.assertTrue(d.contains(x));
        }
    }

    /**
     * Test that deletion works - that it doesn't delete too much or too little.
     * This test was written to check that various failures of eager deletion
     * detected during random testing (i.e. throwing thousands of words into the
     * trie and deleting them) did not arise again.
     *
     * As such it is a "glass box" test that knows about the internals of
     * the code it is testing, and isn't used for HashSetDictionary.
     */
    @Test
    public void testDeleteGlassBox1() {
        String[] words = {"DEAL", "DEN", "DO", "DOG", "DUG", "FAN", "FEN", "FOOT", "FUNK",
                "SAC", "SIRI", "SALP"};
        IDictionary d = new Trie();
        for (String x : words) {
            d.add(x);
        }
        d.delete("DO");
        Assertions.assertFalse(d.contains("DO"));
        Assertions.assertTrue(d.contains("DOG"));
        Assertions.assertEquals(words.length-1,d.count());
        d.delete("SAC");
        Assertions.assertFalse(d.contains("SAC"));
        Assertions.assertTrue(d.contains("SALP"));
        Assertions.assertEquals(words.length-2,d.count());
    }

    /**
     * Starting with the same strings, try deleting a string that isn't there.
     */
    @Test
    public void testDeleteGlassBox2(){
        IDictionary d = new Trie();
        String[] words = {"DEAL", "DEN", "DO", "DOG", "DUG", "FAN", "FEN", "FOOT", "FUNK",
                "SAC", "SIRI", "SALP"};
        for (String x : words) {
            d.add(x);
        }
        // start with a complex case - delete a word which has a node, but that node is not a leaf.
        d.delete("DEA");
        Assertions.assertEquals(words.length, d.count());
        // make sure the leaf node which hangs off the DEA- tree is still there.
        Assertions.assertTrue(d.contains("DEAL"));

        // now a simple case, delete a word which doesn't even have a node from the root.
        d.delete("snark");
        Assertions.assertEquals(words.length, d.count());
        // add that word
        d.add("snark");
        Assertions.assertEquals(words.length+1, d.count());
        Assertions.assertTrue(d.contains("snark"));
        // delete it again.
        d.delete("snark");
        Assertions.assertEquals(words.length, d.count());
        Assertions.assertFalse(d.contains("snark"));
    }


}
