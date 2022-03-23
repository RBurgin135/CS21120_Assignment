package uk.ac.aber.cs21120.trie.solution;

class Node {
    /**
     * An array of references to the child nodes of this node
     * , one for each ASCII character.
     */
    Node[] children = new Node[256];

    /**
     * If true, this node marks the end of a string
     * stored in the Trie
     */
    boolean isleaf = false;
}
