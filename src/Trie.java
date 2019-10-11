/*
 * Jackson Hall
 * CS 110
 * 30 April 2019
 * Boggle: Phase III
 *
 * NOTE: This file was adapted from:  https://www.programcreek.com/2014/05/leetcode-implement-trie-prefix-tree-java/
*/

import java.util.ArrayList;


/**
 * Create a Trie data structure representing a set of words.
 */
public class Trie {

    private TrieNode root;


    /**
     * Default constructor for Trie takes an ArrayList of words and adds them all the root TreeNode.
     * @param words ArrayList\<String> of words to add
     */
    public Trie(ArrayList<String> words) {
        root = new TrieNode();

        // Insert all words
        for (String word: words)
            insert(word.toLowerCase());
    }

    /**
     * Inserts a word into the Trie.
     * @param word word to insert
     */
    public void insert(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            int index = c - 'a';
            if (p.arr[index] == null){
                TrieNode temp = new TrieNode();
                p.arr[index] = temp;
                p = temp;
            } else {
                p = p.arr[index];
            }
        }
        p.isEnd = true;
    }

    /**
     * Return true iff the Trie contains the given word.
     * @param word the word to search
     * @return boolean indicating whether or not this Trie contains word
     */
    public boolean contains(String word){
        TrieNode p = searchNode(word.toLowerCase());

        if (p == null)
            return false;
        else
            return p.isEnd;
    }

    /**
     * Returns true if there is a word in the Trie that starts with the given prefix.
     * @param prefix prefix to search for
     * @return boolean indicating if a word exists in the Trie with the given prefix
     */
    public boolean containsPrefix(String prefix) {
        TrieNode p = searchNode(prefix.toLowerCase());

        return p != null;
    }

    /**
     * Search for a TrieNode that starts with the given string - if found return the TrieNode, else null.
     * @param s the string to search
     * @return TrieNode object that starts with the given string if found, else null
     */
    public TrieNode searchNode(String s){
        TrieNode p = root;

        // Loop through characters in the String
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);

            // Gets number of letter in the alphabet, 0-indexed
            int index = c - 'a';


            if (p.arr[index] != null)
                p = p.arr[index];
            else
                return null;
        }

        if (p == root)
            return null;

        return p;
    }
}

/**
 * Auxiliary class for use with Trie class - creates nodes in the Trie.
 */
class TrieNode {
    TrieNode[] arr;
    boolean isEnd;

    /**
     * Default constructor for TrieNode creates an int[26] for the 26 letters in the alphabet.
     */
    public TrieNode() {
        this.arr = new TrieNode[26];
    }

}
