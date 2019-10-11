/*
 * Jackson Hall
 * CS 110
 * 30 April 2019
 * Boggle: Phase III
 */

import java.util.*;


/**
 * Represents the Boggle board as an object.
 */
public class Board {

    private static final Random RAND = new Random();

    private ArrayList<ArrayList<Tile>> board;

    private int width, height;

    private static final char[] DIE0 =  {'R','I','F','O','B','X'};
    private static final char[] DIE1 =  {'I','F','E','H','E','Y'};
    private static final char[] DIE2 =  {'D','E','N','O','W','S'};
    private static final char[] DIE3 =  {'U','T','O','K','N','D'};
    private static final char[] DIE4 =  {'H','M','S','R','A','O'};
    private static final char[] DIE5 =  {'L','U','P','E','T','S'};
    private static final char[] DIE6 =  {'A','C','I','T','O','A'};
    private static final char[] DIE7 =  {'Y','L','G','K','U','E'};
    private static final char[] DIE8 =  {'Q','B','M','J','O','A'};
    private static final char[] DIE9 =  {'E','H','I','S','P','N'};
    private static final char[] DIE10 = {'V','E','T','I','G','N'};
    private static final char[] DIE11 = {'B','A','L','I','Y','T'};
    private static final char[] DIE12 = {'E','Z','A','V','N','D'};
    private static final char[] DIE13 = {'R','A','L','E','S','C'};
    private static final char[] DIE14 = {'U','W','I','L','R','G'};
    private static final char[] DIE15 = {'P','A','C','E','M','D'};

    private static final char[][] DICE = {DIE0, DIE1, DIE2, DIE3, DIE4, DIE5,
            DIE6, DIE7, DIE8, DIE9, DIE10, DIE11, DIE12, DIE13, DIE14, DIE15};
    /**
     * Alternate constructor to create Board with given dimensions.
     * @param width width of the board
     * @param height height of the board
     */
    private Board(int width, int height){
        this.width = width;
        this.height = height;

        // Create a copy of DICE, shuffled
        char[][] shuffledDice = shuffleDice();

        // Store index of shuffledDice to choose letter from
        int dieNum = 0;

        // Initialize board
        board = new ArrayList<>(height);

        // Initialize ArrayLists in rows
        for (int row = 0; row < height; row++){
            board.add(new ArrayList<>(width));

            // Initialize Tiles in columns
            for (int col = 0; col < width; col++){
                // Get random letter from next die in shuffled array
                char letter = shuffledDice[dieNum++][RAND.nextInt(6)];

                // Create Tile here
                board.get(row).add(new Tile(letter, row, col));
            }
        }
    }

    /**
     * Constructor to create a default (4x4) game board.
     */
    public Board(){
        this(4, 4);
    }

    /**
     * Return the Tile object at the given row, col coordinates.
     * @return Tile object at given coords
     */
    public Tile get(int row, int col){
        return board.get(row).get(col);
    }

    /**
     * Return the Tile object at the given (row, col) coordinates as an int[].
     * @param coords int[] holding [row, col]
     * @return Tile at the given location
     */
    public Tile get(int[] coords){
        return get(coords[0], coords[1]);
    }

    /**
     * Create String representation of Board objects.
     * @return String representing state of the Board
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        // Build top row
        sb.append("┌───");
        for (int col = 1; col < width; col++){
            sb.append("┬───");
        }
        sb.append("┐\n");

        // Build body
        for (int row = 0; row < height; row++){
            // Print row before all but first row
            if (row != 0){
                sb.append("├───");
                for (int col = 1; col < width; col++){
                    sb.append("┼───");
                }
                sb.append("┤\n");
            }

            for (int col = 0; col < width; col++){
                // Leading divider
                sb.append("│ ");

                // Letter in this square
                String letter = get(row, col).toString();
                sb.append(letter);

                // Only add padding space for non-"Qu" chars
                if (letter.length() != 2){
                    sb.append(' ');
                }
            }

            // Trailing divider (right edge of board)
            sb.append("│\n");
        }

        // Build bottom row
        sb.append("└───");
        for (int row = 1; row < width; row++){
            sb.append("┴───");
        }
        sb.append('┘');

        return sb.toString();
    }

    /**
     * Creates and returns a (shallowly) shuffled (deep) copy of DICE.
     * @return a shuffled copy of DICE
     */
    private char[][] shuffleDice(){
        // Initialize newDice
        char[][] newDice = new char[DICE.length][];

        // Initialize all arrays in newDice
        for (int i = 0; i < newDice.length; i++){
            newDice[i] = new char[6];
        }

        // Copy dice
        for (int die = 0; die < DICE.length; die++){
            System.arraycopy(DICE[die], 0, newDice[die], 0, newDice[die].length);
        }

        // Shuffle order of the dice
        for (int i1 = 0; i1 < DICE.length; i1++){
            int i2 = RAND.nextInt(DICE.length);
            swap(newDice, i1, i2);
        }

        return newDice;
    }

    /**
     * Used in shuffleDice when shuffling order of dice to choose from.
     * @param list the char[] list in which to swap items
     * @param index1 index of the first item
     * @param index2 index of the second item
     */
    private void swap(char[][] list, int index1, int index2){
        char[] temp = list[index1];
        list[index1] = list[index2];
        list[index2] = temp;
    }

    /**
     * Getter method for width.
     * @return width of the board
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter method for height.
     * @return height of the board
     */
    public int getHeight() {
        return height;
    }

    /**
     * Return true if the given word can be created in the board.
     * @param word the word to test
     * @return true if the given word can be created in the board
     */
    public boolean testWord(String word){

        boolean valid;

        // Letters in the word ("qu" stored as one element)
        ArrayList<String> wordChars = Tile.listFromString(word);

        // Tiles that have been used so far
        ArrayList<Tile> usedTiles = new ArrayList<>(16);

        // Any String of length 0 is found in the board (but this never happens)
        if (word.length() == 0)
            return true;

        // Loop through every position in the Board...
        for (int row = 0; row < getHeight(); row++){
            for (int col = 0; col < getWidth(); col++){

                // And if the toString()s match and this Tile isn't in usedTiles,
                if (get(row, col).toString().equalsIgnoreCase(wordChars.get(0))
                        && !usedTiles.contains(get(row, col))){

                    // add this Tile to usedTiles,
                    usedTiles.add(get(row, col));

                    // and see if the rest of the word is valid without using this Tile again.
                    valid = testWord(wordChars.subList(1, wordChars.size()), usedTiles);

                    if (valid){
                        // If it matches, the word is valid.
                        for (Tile t : usedTiles){
                            t.setSelected();
                        }
                        return true;
                    } else {
                        // Otherwise remove the Tile that was added and continue searching the board.
                        usedTiles.remove(get(row, col));
                    }
                }

            }
        }

        return false;
    }

    /**
     * Return true if given partial word can be created in the board without using any usedTiles.
     * @param wordChars the partial word to test
     * @param usedTiles the Tiles that have already been used
     * @return true if given partial word can be created in the board without using any usedTiles
     */
    public boolean testWord(List<String> wordChars, ArrayList<Tile> usedTiles){

        boolean valid = false;

        Tile lastUsed = usedTiles.get(usedTiles.size() - 1);

        if (wordChars.size() == 0){
            return true;
        } else {
            ArrayList<Tile> matchingNeighbors = getMatchingNeighbors(lastUsed.getCoords(), wordChars.get(0));

            for (int i = 0; i < matchingNeighbors.size(); i++){
                // If the first element of wordChars matches the toString of the neighbor and isn't yet in usedTiles,
                if (matchingNeighbors.get(i).toString().equalsIgnoreCase(wordChars.get(0))
                        && !usedTiles.contains(matchingNeighbors.get(i))){
                    // add this Tile to usedTiles,
                    usedTiles.add(matchingNeighbors.get(i));

                    // and the word is valid if the rest of the letters can be found in this chain
                    // without using duplicate Tiles.
                    valid = testWord(wordChars.subList(1, wordChars.size()), usedTiles);
                }

                if (valid){
                    // If it matches, the word is valid.
                    return true;
                } else {
                    // Otherwise remove this Tile from usedTiles and continue searching other neighbors.
                    usedTiles.remove(matchingNeighbors.get(i));
                }

            }
        }

        return false;
    }

    /**
     * Return an ArrayList with all Tiles neighboring the given coordinates.
     * @param row row of the Tile
     * @param col col of the Tile
     * @return ArrayList<Tile> with neighboring Tiles
     */
    public ArrayList<Tile> getNeighbors(int row, int col){
        ArrayList<Tile> neighbors = new ArrayList<>(16);

        // Try looping through all neighbors
        for (int dy = -1; dy <= 1; dy++)
            for (int dx = -1; dx <= 1; dx++){
                // Don't test the cell itself
                if (dx == 0 && dy == 0)
                    continue;

                try {
                    neighbors.add(get(row + dy, col + dx));
                } catch (IndexOutOfBoundsException e){
                    // Catch when this tries to get an element off the board
                }
            }

        return neighbors;
    }

    /**
     * Return an ArrayList with all Tiles neighboring the given coordinates.
     * @param coords int[] containing (row, col) of the Tile
     * @return ArrayList<Tile> with neighboring Tiles
     */
    public ArrayList<Tile> getNeighbors(int[] coords){
        return getNeighbors(coords[0], coords[1]);
    }

    /**
     * Get all neighboring Tiles of the cell at the given coordinates, unless the Tile is in usedTiles.
     * @param row row of the Tile
     * @param col column of the Tile
     * @param usedTiles Tiles that are considered used not included in the output
     * @return ArrayList\<Tile> with all neighbors, excluding those that are in usedTiles
     */
    public ArrayList<Tile> getNeighborsNoDuplicates(int row, int col, ArrayList<Tile> usedTiles){

        // Create 2 ArrayLists to avoid any errors with removing elements while looping through them
        ArrayList<Tile> neighbors = getNeighbors(row, col);
        ArrayList<Tile> neighborsTester = getNeighbors(row, col);

        for (Tile neighbor : neighborsTester)
            if (usedTiles.contains(neighbor))
                neighbors.remove(neighbor);

        return neighbors;
    }

    /**
     * Get all neighboring Tiles of the cell at the given coordinates, unless the Tile is in usedTiles.
     * @param coords (row, col) coordinates of the Tile
     * @param usedTiles Tiles that are considered used not included in the output
     * @return ArrayList\<Tile> with all neighbors, excluding those that are in usedTiles
     */
    public ArrayList<Tile> getNeighborsNoDuplicates(int[] coords, ArrayList<Tile> usedTiles){
        return getNeighborsNoDuplicates(coords[0], coords[1], usedTiles);
    }

    /**
     * Return a list of neighboring Tiles whose toString()s match the given String.
     * @param coords coordinates of the tile to get the neighbors of
     * @param valToMatch String that the neighboring Tiles' toString()s must match
     * @return ArrayList\<Tile> of neighboring Tiles whose toString()s match valToMatch
     */
    private ArrayList<Tile> getMatchingNeighbors(int[] coords, String valToMatch){
        ArrayList<Tile> neighbors = getNeighbors(coords);
        ArrayList<Tile> matches = new ArrayList<>(8);

        // Loop through all neighbors, add the neighbor when it's a match
        for (Tile neighbor : neighbors){
            if (get(neighbor.getCoords()).toString().equalsIgnoreCase(valToMatch))
                matches.add(neighbor);
        }

        return matches;
    }
}









