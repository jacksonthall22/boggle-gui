import java.util.ArrayList;
import java.util.Scanner;
//import java.io.IOException;


public class BoggleText {
    public static void main(String[] args) /* throws IOException */ {
        // create game
        Game g = new Game();

        // create scanner for user input
        Scanner keyboard = new Scanner(System.in);

        // variables for user input
        int row, col;
        String input;

        // create flag to end game
        boolean stop = false;

        // play game while the user hasn't stopped the game
        while (!stop) {
            // print board
            System.out.println(g);

            // prompt user for choices
            System.out.print("(s)elect, (d)eselect, (l)ist selectedTiles, (c)lear selectedTiles, (t)est selectedTiles, (e)nd: ");

            // get choice
            input = keyboard.nextLine();

            // select
            if (input.equalsIgnoreCase("s")) {
                // prompt for row & column
                System.out.print("row / column [r c]: ");

                // get row, col from input
                row = keyboard.nextInt();
                col = keyboard.nextInt();
                keyboard.nextLine(); // clear new line left in buffer

                // test if the r/c combination is a valid move
                if (g.isValidSelection(row, col)){
                    // add tile to selectedTiles tiles
                    if (g.getSelectedTiles().contains(g.getTile(row,col)))
                        System.out.println("Tile is already selectedTiles");
                    else
                        g.addToSelected(row, col);
                }
                else {
                    System.out.println("Invalid selection! Please select a letter adjacent to the previous selectedTiles letter.");
                }
            }

            // deselect
            else if (input.equalsIgnoreCase("d")) {
                // prompt for row & column
                System.out.print("row / column [r c]: ");

                // get row, col from input
                row = keyboard.nextInt();
                col = keyboard.nextInt();
                keyboard.nextLine(); // clear new line left in buffer

                // remove tile from selectedTiles tiles
                g.removeLastFromSelected();
            }

            // list currently selectedTiles tiles
            else if (input.equalsIgnoreCase("l")) {
                ArrayList<Tile> selected = g.getSelectedTiles();
                System.out.println(selected);
            }

            // clear currently selectedTiles tiles
            else if (input.equalsIgnoreCase("c")) {
                g.clearSelected();
            }

            else if (input.equalsIgnoreCase("t")) {
                g.testSelected();
            }

            // end game
            else if (input.equalsIgnoreCase("e")) {
                stop = true;
            }
        }

    }
}