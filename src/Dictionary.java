/*
 * Jackson Hall
 * CS 110
 * 30 April 2019
 * Boggle: Phase III
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Represents a Dictionary of valid words that can be played in the game.
 */
public class Dictionary {

    private ArrayList<String> getWordList = new ArrayList<>();
    private Trie trie;

    /**
     * Construct a Dictionary with words in the given file.
     * @param filename filename containing valid words, delimited by '\n's
     */
    public Dictionary(String filename) {
        File file;
        Scanner fileScanner = null;

        // Try reading words into this Dictionary
        try {
            file = new File(filename);
            fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                getWordList.add(fileScanner.nextLine().trim().toLowerCase());
            }

            // If above code works, set the Trie for this Dictionary
            setTrie();
        } catch (FileNotFoundException e){
            System.out.println("ERROR: File '" + filename + "' could not be loaded.");
        } finally {
            if (fileScanner != null)
                fileScanner.close();
        }
    }

    /**
     * Return the ArrayList of words in this Dictionary.
     * @return ArrayList\<String> of words in this Dictionary
     */
    public ArrayList<String> getWordList() {
        return getWordList;
    }

    /**
     * Determine if given word (as String) is in this Dictionary.
     * @param word the word to search
     * @return true iff word is in this Dictionary
     */
    public boolean isValidWord(String word){
        return getWordList.contains(word.toLowerCase());
    }

    /**
     * Determine if given Word object is in this Dictionary.
     * @param word the Word to search
     * @return true iff word is in this Dictionary
     */
    public boolean isValidWord(Word word){
        return isValidWord(word.toString());
    }

    /**
     * Determine if given word (as Tile[]) is in this Dictionary.
     * @param letters the letters of the Word to search
     * @return true iff word is in this Dictionary
     */
    public boolean isValidWord(ArrayList<Tile> letters){
        // Create temporary object out of ArrayList
        Word wordObj = new Word(letters);

        return isValidWord(wordObj);
    }

    /**
     * Generates a Trie from the words in this Dictionary.
     */
    public void setTrie(){
        trie = new Trie(getWordList());
    }

    /**
     * Return the Trie for this Dictionary.
     */
    public Trie getTrie(){
        return trie;
    }
}
