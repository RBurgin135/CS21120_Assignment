package uk.ac.aber.cs21120.trie.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.trie.interfaces.IDictionary;
import uk.ac.aber.cs21120.trie.solution.HashSetDictionary;

import java.util.List;

public class HashSetTests {

    private IDictionary getTestObject(){
        return new HashSetDictionary();
    }


    /**
     * Make sure that an empty HSD contains no data, not even an empty string.
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
     * Test some two-character strings - this test is actually
     * more important for tries, but we duplicate it here.
     */
    @Test
    public void testTwo(){
        IDictionary d = getTestObject();
        d.add("aa");
        d.add("ab");
        d.add("ac");
        Assertions.assertFalse(d.contains("a"));
        Assertions.assertTrue(d.contains("aa"));
        Assertions.assertTrue(d.contains("ab"));
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
     * Here we test with the example data from the brief - again, with
     * a trie we can test against this known structure, but it's unlikely
     * this is the only test which will fail in a HashSetDictionary unless
     * things have gone very strange.
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
        Assertions.assertFalse(d.contains("FUN"));
        Assertions.assertFalse(d.contains("DEALT"));
        Assertions.assertFalse(d.contains(""));

    }

    /**
     * Test that the contents() method works
     */
    @Test
    public void testContents(){
        String[] words = {"DEAL", "DEN", "DO", "DOG", "DUG", "FAN", "FEN", "FOOT", "FUNK"};
        // build and populate the HSD
        IDictionary d = getTestObject();
        for(String x:words){
            d.add(x);
        }
        List<String> list = d.contents(); // get the contents list

        // make sure the list is the right length and contains every word.
        Assertions.assertEquals(words.length, list.size());
        for(String x:words) {
            Assertions.assertTrue(list.contains(x));
        }
    }

    /**
     * This is a "general purpose" deletion test; we don't use the special
     * test designed for tries in this class.
     */

    @Test
    public void testDelete(){
        String[] words = {"aa","ab","ac","Hello","Help","Hell","hell","cake","canoe","fish","filehandler"};
        IDictionary d = getTestObject();
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
}
