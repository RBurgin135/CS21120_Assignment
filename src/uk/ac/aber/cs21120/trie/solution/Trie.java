package uk.ac.aber.cs21120.trie.solution;

import uk.ac.aber.cs21120.trie.interfaces.IDictionary;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Trie implements IDictionary {
    private final Node rootNode;
    private int size;

    /**
     * Basic constructor.
     */
    public Trie(){
        rootNode = new Node();
        size = 0;
    }

    /**
     * Adds a specified word to the Trie.
     * @param s word to add
     */
    @Override
    public void add(String s) {
        if(s.equals("")) {rootNode.isleaf = true; size++;}
        //convert s into bytes
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        Node currentNode = rootNode;
        //trace through the structure
        for(byte b : bytes){
            if(currentNode.children[b] == null){
                currentNode.children[b] = new Node();
            }
            currentNode = currentNode.children[b];
        }
        //set to whether leaf
        if(!currentNode.isleaf){
            currentNode.isleaf = true;
            size++;
        }
    }

    /**
     * Checks if a word is contained within the Trie.
     * @param s word to check
     * @return true if the word is stored in the trie, otherwise false
     */
    @Override
    public boolean contains(String s) {
        if(s.equals("")) return rootNode.isleaf;
        //convert s into bytes
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        Node currentNode = rootNode;
        //trace through the structure
        for(byte b : bytes){
            if(currentNode.children[b] == null){
                return false;
            }
            currentNode = currentNode.children[b];
        }
        //return is leaf
        return currentNode.isleaf;
    }


    /*
    @Override
    public void delete(String s) { //lazy deletion
        if(s.equals("") && rootNode.isleaf) {size--; rootNode.isleaf = false;}
        //converts into bytes
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        Node currentNode = rootNode;
        //trace through the structure
        for(byte b: bytes){
            if(currentNode.children[b] == null) return;
            currentNode = currentNode.children[b];
        }
        //if word is found
        if(currentNode.isleaf) {
            size--;
            currentNode.isleaf = false;
        }
    }*/

    /**
     * Eager deletion of a word within the Trie.
     * The redundant nodes are removed by a call to helper function repairTrie.
     * @param s the word to be removed from the Trie
     */
    @Override
    public void delete(String s) { //eager deletion
        if(s.equals("") && rootNode.isleaf) {size--; rootNode.isleaf = false;}
        Stack<Node> traceNodes = new Stack<>();
        Stack<Byte> traceChildren = new Stack<>();
        //converts into bytes
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        Node currentNode = rootNode;
        //trace through the structure
        for (byte b : bytes) {
            if (currentNode.children[b] == null) return;
            traceNodes.add(currentNode);
            traceChildren.add(b);
            currentNode = currentNode.children[b];

        }
        //if word is found
        if(currentNode.isleaf) {
            size--;
            currentNode.isleaf = false;
            repairTrie(traceNodes, traceChildren);
        }
    }

    /**
     * Helper function to repair the Trie and remove any redundant nodes once the delete method
     * has been run. Example of eager deletion.
     * @param traceNodes a stack referencing the nodes traversed to reach the node which was deleted
     * @param traceChildren a stack referencing the indexes of the nodes traversed, for use when reverting
     *                      the children to null
     */
    private void repairTrie(Stack<Node> traceNodes, Stack<Byte> traceChildren){
        //traces back through the steps that were used to find the deleted node
        //then removes any redundancies
        Node x;
        byte b;
        while(true){
            //retrieve from stack
            if(traceNodes.isEmpty() || traceChildren.isEmpty()) return;
            x = traceNodes.pop();
            b = traceChildren.pop();

            //if the node has children then it can't be removed because the children are using them.
            for (int i=0; i<255; i++){
                if(i==b) continue;
                if(x.children[i] != null)return;
            }
            //leave if used else delete
            if(x.children[b].isleaf) return;
            x.children[b] = null;
        }
    }

    /**
     * Used to find how many words are in the Trie.
     * @return the number of words currently held in the Trie
     */
    @Override
    public int count() {
        return size;
    }

    /**
     * Initiates a call to a recursive helper function which compiles all of the words stored in the Trie into
     * a List.
     * @return a List containing strings of all of the words stored in the Trie
     */
    @Override
    public List<String> contents() {
        List<String> list = new ArrayList<>();
        if(rootNode.isleaf) list.add("");
        addContents(list, rootNode, "");
        return list;
    }

    /**
     * Recursive helper function used to compile all of the words stored in the Trie into a List.
     * @param list the list the word strings will be appended to
     * @param currentNode the current node accessed in the traversal
     * @param wordSoFar the current incomplete word string being constructed
     */
    private void addContents(List<String> list, Node currentNode, String wordSoFar){
        String nextWord;
        for(int i=0; i<255; i++){
            if(currentNode.children[i]!=null){
                nextWord = wordSoFar + (char)i;
                if(currentNode.children[i].isleaf) list.add(nextWord);
                addContents(list, currentNode.children[i], nextWord);
            }
        }
    }
}
