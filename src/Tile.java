/*
 * Jackson Hall
 * CS 110
 * 30 April 2019
 * Boggle: Phase III
 */

import java.util.ArrayList;


/**
 * Represents one Tile (letter) on the game board.
 */
public class Tile {

    private char letter;
    private int row, col;
    private boolean isSelected;

    /**
     * Construct Tile with given letter and coords, defaults isSelected to false.
     * @param letter letter showing on the tile
     * @param col x coordinate on the board (0-indexed)
     * @param row y coordinate on the board (0-indexed)
     */
    public Tile(char letter, int row, int col){
        this(letter, row, col, false);
    }

    /**
     * Construct a copy of another Tile object.
     * @param t the other Tile to copy
     */
    public Tile(Tile t){
        this(t.getLetter(), t.getRow(), t.getCol(), t.isSelected());
    }

    /**
     * Construct Tile with given letter (takes first char of given String, i.e.
     * "Qu" becomes "Q"), coords, and isSelected status.
     * @param letter letter showing on the tile
     * @param col x coordinate on the board (0-indexed)
     * @param row y coordinate on the board (0-indexed)
     * @param isSelected boolean indicating whether the tile is currently selectedTiles
     */
    public Tile(String letter, int row, int col, boolean isSelected){
        this(letter.charAt(0), row, col, isSelected);
    }

    /**
     * Construct Tile with given letter (takes first char of given String, i.e.
     * "Qu" becomes "Q"), coords, and default isSelected status to false.
     * @param letter letter showing on the tile
     * @param col x coordinate on the board (0-indexed)
     * @param row y coordinate on the board (0-indexed)
     */
    public Tile(String letter, int row, int col){
        this(letter, row, col, false);
    }

    /**
     * Construct Tile with given letter, coords, and isSelected status.
     * @param letter letter showing on the Tile
     * @param col x coordinate on the board (0-indexed)
     * @param row y coordinate on the board (0-indexed)
     * @param isSelected boolean indicating whether the tile is currently selectedTiles
     */
    public Tile(char letter, int row, int col, boolean isSelected){
        this.letter = Character.toUpperCase(letter);
        this.row = row;
        this.col = col;
        this.isSelected = isSelected;
    }

    /**
     * Get the letter showing on the Tile.
     * @return letter showing on the Tile.
     */
    public char getLetter() {
        return letter;
    }

    /**
     * Get the y coordinate of the Tile (0-indexed)
     * @return y coordinate of the Tile
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the x coordinate of the Tile (0-indexed)
     * @return x coordinate of the Tile
     */
    public int getCol() {
        return col;
    }

    /**
     * Return (x, y) coordinates of the Tile in a int[] array.
     * @return (x, y) coordinates in a int[] array
     */
    public int[] getCoords(){
        return new int[]{getRow(), getCol()};
    }

    /**
     * Return isSelected flag indicating whether Tile is currently selectedTiles.
     * @return isSelected boolean flag
     */
    public boolean isSelected(){
        return isSelected;
    }

    /**
     * Set isSelected status to true for this Tile.
     */
    public void setSelected(){
        this.isSelected = true;
    }

    /**
     * Set isSelected status to false for this Tile.
     */
    public void setUnselected(){
        this.isSelected = false;
    }

    /**
     * Return char showing on the Tile.
     * @return String representing Tile
     */
    @Override
    public String toString(){
        if (getLetter() == 'Q')
            return "Qu";

        return Character.toString(getLetter());
    }

    /**
     * Determine if other Tile is the same object.
     * @param other Tile object to compare to this
     * @return true iff other == this
     */
    @Override
    public boolean equals(Object other){
        if (other == null || other.getClass() != this.getClass())
            return false;

        return other == this;
    }

    /**
     * Return an ArrayList\<String> representation of the given String.
     * @return ArrayList\<String> representation of the given String
     */
    public static ArrayList<String> listFromString(String str){
        ArrayList<String> letters = new ArrayList<>(str.length());

        for (int i = 0; i < str.length(); i++){
            if ((str.charAt(i) + "").equalsIgnoreCase("q")){
                letters.add("qu");
                i++;
            } else {
                letters.add(str.charAt(i) + "");
            }
        }

        return letters;
    }

    /**
     * Return the String representation of the given Tile ArrayList.
     * @param letters ArrayList<Tile> of letters to add to the String
     * @return String representation of the given Tile ArrayList
     */
    public static String stringFromList(ArrayList<Tile> letters){
        StringBuilder sb = new StringBuilder();

        for (Tile letter : letters){
            sb.append(letter.toString().toUpperCase());
        }

        return sb.toString();
    }
}
