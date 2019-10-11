/*
 * Jackson Hall
 * CS 110
 * 30 April 2019
 * Boggle: Phase III
 */

import java.util.ArrayList;


/**
 * Represents a Word entered by the player.
 */
public class Word {

    private String word;

    /**
     * Construct a Word object with the given collection of Tile objects.
     * @param tiles ArrayList\<Tile> representing squares the user has selectedTiles
     */
    public Word(ArrayList<Tile> tiles){
        StringBuilder sb = new StringBuilder();

        for (Tile t: tiles){
            sb.append(t.getLetter()); // GOTO FIX
        }

        word = sb.toString();
    }

    /**
     * Construct a Word object representing the given word.
     * @param word the word to store
     */
    public Word(String word){
        this.word = word;
    }

    /**
     * Return the number of points this word scores.
     * @return number of scoring points for this word
     */
    public int getPoints(){
        int score;
        int l = word.length();

        if (l < 3)
            score = 0;
        else if (l == 3 || l == 4)
            score = 1;
        else if (l == 5)
            score = 2;
        else if (l == 6)
            score = 3;
        else if (l == 7)
            score = 5;
        else
            score = 11;

        return score;
    }

    /**
     * Statically determine point value of a word.
     * @param word word to test
     * @return point value of the given word
     */
    public static int getPoints(String word){
        Word wordObj = new Word(word);

        return wordObj.getPoints();
    }

    /**
     * Return the contents of the Word as a String.
     * @return String representing the Word
     */
    @Override
    public String toString(){
        return word;
    }
}
