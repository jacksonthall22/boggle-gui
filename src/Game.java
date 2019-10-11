/*
 * Jackson Hall
 * CS 110
 * 30 April 2019
 * Boggle: Phase III
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringJoiner;


/**
 * Create and store data for a single Game of Boggle.
 */
public class Game {

    private Board board;
    private Dictionary dictionary;
    private ArrayList<Tile> selectedTiles;
    private ArrayList<Word> guessedWords;
    private ArrayList<String> possibleWords;
    private int score;
    private boolean isOver;


    /**
     * Default constructor for Game objects. Creates a 4x4 Board.
     */
    public Game(){
        board = new Board();
        dictionary = new Dictionary("dictionary.txt");
        score = 0;
        selectedTiles = new ArrayList<>(16);
        guessedWords = new ArrayList<>();
        setPossibleWords();
        setIsOver(false);
    }

    /**
     * Return String representation of Game object.
     * @return String representation of Game object
     */
    @Override
    public String toString(){
        // Create StringBuilder to be returned
        StringBuilder sb = new StringBuilder();

        // Display the game board
        sb.append(board).append('\n');

        // Add Tiles from selectedTiles
        StringJoiner sj = new StringJoiner(", ");
        for (Tile selectedTile : getSelectedTiles())
            sj.add(selectedTile.toString());
        sb.append("Selected: [").append(sj.toString()).append("]\n");

        // Add guessed guessedWords
        sj = new StringJoiner(", ");
        for (Word word : guessedWords)
            sj.add(word.toString());
        sb.append("guessedWords: [").append(sj.toString()).append("]\n");

        // Add score
        sb.append("score: ").append(getScore()).append(" point").append(getScore() == 1 ? "" : "s");

        return sb.toString();
    }

    public Dictionary getDictionary(){
        return dictionary;
    }



    /**
     * Return true if this (row,col) Tile is not already selected and is adjacent to the previously-selected Tile.
     * @param row row index
     * @param col column index
     * @return true if Tile at given space is not already selected and is adjacent to the previously-selected Tile
     */
    public boolean isValidSelection(int row, int col){
        boolean valid = false;

        try {
            // Try to get the last tile that was in selectedTiles, if there is one
            if (areAdjacent(board.get(row, col), getLastSelected())
                    && !getSelectedTiles().contains(board.get(row,col))){
                valid = true;
            }
        } catch (NullPointerException e){
            // Catch errors when selectedTiles is empty (none selected yet -> anything is valid)
            valid = getSelectedTiles().size() == 0;

            // ...except if inputted coordinates are out of range of board's dimensions
            if (row < 0 || row > board.getHeight()
                    || col < 0 || col > board.getWidth()) {
                valid = false;
            }
        }

        return valid;
    }

    /**
     * Overloads isValidSelection - takes an int[] holding (row, col).
     * @param coords int[] holding (row, col)
     * @return true if Tile at given space is not already selected and is adjacent to the previously-selected Tile
     */
    public boolean isValidSelection(int[] coords){
        return isValidSelection(coords[0], coords[1]);
    }

    /**
     * Add the Tile at the specified location in the Board to selectedTiles.
     * @param row row in the board
     * @param col column in the board
     */
    public void addToSelected(int row, int col){
        selectedTiles.add(board.get(row, col));
    }

    /**
     * Add the given Tile to selectedTiles.
     * @param t Tile to add
     */
    public void addToSelected(Tile t){
        addToSelected(t.getRow(), t.getCol());
    }

    /**
     * Remove the most recently selected Tile from selectedTiles.
     */
    public void removeLastFromSelected(){
        try {
            getSelectedTiles().remove(getSelectedTiles().size()-1);
        } catch (IndexOutOfBoundsException e){}
    }

    /**
     * Remove all Tiles from selectedTiles.
     */
    public void clearSelected(){
        selectedTiles = new ArrayList<>(16);
    }

    /**
     * Return the Board object for this Game.
     * @return the Board object for this Game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Return the ArrayList selectedTiles.
     * @return selectedTiles
     */
    public ArrayList<Tile> getSelectedTiles(){
        return selectedTiles;
    }

    /**
     * Return the Tile object that was most recently selected.
     * @return most recently selected Tile
     */
    public Tile getLastSelected(){
        if (getSelectedTiles().size() == 0){
            return null;
        } else {
            return getSelectedTiles().get(getSelectedTiles().size() - 1);
        }
    }

    /**
     * Add given Word to list of guessed guessedWords.
     * @param w Word to add
     */
    public void addToSelectedWords(Word w){
        guessedWords.add(w);
    }

    /**
     * Return true iff the selected tiles make up a valid word.
     * @return true iff the selected tiles make up a valid word
     */
    public boolean testSelected(){
        boolean valid = false;

        // If word is valid, add it to guessedWords and increment score
        if (dictionary.isValidWord(getSelectedTiles())){
            valid = true;
        }

        return valid;
    }

    /**
     * Return the Tile at the specified location in the board.
     * @param row row in the board
     * @param col column in the board
     * @return the Tile object at given location
     */
    public Tile getTile(int row, int col){
        return board.get(row, col);
    }

    /**
     * Return the valid Words that have been guessed so far.
     * @return ArrayList\<Word> guessedWords
     */
    public ArrayList<Word> getGuessedWords(){
        return guessedWords;
    }

    /**
     * Return the total accumulated score.
     * @return total accumulated score
     */
    public int getScore() {
        return score;
    }

    /**
     * Add the given number of points to the score.
     * @param num number of points to add
     */
    public void addToScore(int num){
        score += num;
    }

    /**
     * Set the status of the isOver flag.
     * @param b boolean used to set isOver
     */
    public void setIsOver(boolean b){
        isOver = b;
    }

    /**
     * Get the status of the isOver flag.
     * @return true iff the game is over
     */
    public boolean isOver(){
        return isOver;
    }

    /**
     * Determine if two coordinates are adjacent orthogonally or vertically.
     * @param t1 first Tile object
     * @param t2 second Tile object
     * @return true iff indices are not the same and |row1 - row2| <= 1 && |col1 - col2| <= 1
     */
    private static boolean areAdjacent(Tile t1, Tile t2){
        // Get and unpack coords
        int[] coord1 = t1.getCoords();
        int[] coord2 = t2.getCoords();
        int row1 = coord1[0];
        int col1 = coord1[1];
        int row2 = coord2[0];
        int col2 = coord2[1];

        // Not adjacent when indices are the same
        if (row1 == row2 && col1 == col2)
            return false;

        // If indices are not equal, check they are at most 1 square away horizontally & vertically
        return Math.abs(row1 - row2) <= 1 && Math.abs(col1 - col2) <= 1;
    }

    /**
     * Set possibleWords to an ArrayList of all guessedWords that can be found for this Game's Board and Dictionary.
     */
    public void setPossibleWords(){

        // Words that will be returned
        ArrayList<String> words = new ArrayList<>(16);

        // Loop through all positions on the board
        for (int row = 0; row < board.getHeight(); row++)
            for (int col = 0; col < board.getWidth(); col++){
                // Send array with the Tile at this location. getWordsFromStem() will do the rest
                ArrayList<Tile> tileToSend = new ArrayList<>(1);
                tileToSend.add(board.get(row, col));
                words.addAll(getWordsFromStem(tileToSend));
            }

        words.sort(Comparator.comparing(String::length).reversed());
        possibleWords = words;
    }

    /**
     * Recursively find all guessedWords in the Board that can be made starting with the letter(s) of the given Tile(s).
     * @param possibleWord ArrayList<Tile> that holds a consecutive chain of unique Tiles that is valid so far
     * @return all valid guessedWords that have the prefix possibleWords
     */
    private ArrayList<String> getWordsFromStem(ArrayList<Tile> possibleWord){

        // Initialize ArrayList of valid guessedWords to return
        ArrayList<String> validWords = new ArrayList<>();

        // Reference for the Dictionary's Trie
        Trie trie = getDictionary().getTrie();

        // Get the most recently used Tile
        Tile lastUsed = possibleWord.get(possibleWord.size() - 1);

        // Get neighbors of lastUsed, without anything already in this chain
        ArrayList<Tile> currentNeighbors = board.getNeighborsNoDuplicates(lastUsed.getCoords(), possibleWord);

        // Loop through these neighbors
        for (Tile neighbor : currentNeighbors){
            // Add this neighbor to the current chain for now
            possibleWord.add(neighbor);

            // If the already-selected Tiles and this neighbor don't combine to make a valid prefix,
            // this definitely can't be a valid word
            if (trie.containsPrefix(Tile.stringFromList(possibleWord))) {
                // If this is a word itself, add it to the list of valid guessedWords if it's not already there
                if (trie.contains(Tile.stringFromList(possibleWord))) {
                    String validWord = Tile.stringFromList(possibleWord);

                    if (!validWords.contains(validWord))
                        validWords.add(validWord);

                    // Add any other valid guessedWords from this stem (i.e. "trie" -> "tries") if they
                    // haven't been added already
                    for (String otherValidWord : getWordsFromStem(possibleWord)){
                        if (!validWords.contains(otherValidWord))
                            validWords.add(otherValidWord);
                    }
                } else {
                    // If this is a valid prefix and not a word itself, must go deeper to check
                    // for future valid guessedWords
                    for (String otherValidWord : getWordsFromStem(possibleWord))
                        if (!validWords.contains(otherValidWord))
                            validWords.addAll(getWordsFromStem(possibleWord));
                }
            }

            // Testing complete, this neighbor can be removed from the current chain
            possibleWord.remove(neighbor);
        }

        return validWords;
    }

    /**
     * Return the ArrayList of all possible words that can be found in this Game.
     * @return ArrayList<String> of all possible words in this Game
     */
    public ArrayList<String> getPossibleWords(){
        return possibleWords;
    }
}


