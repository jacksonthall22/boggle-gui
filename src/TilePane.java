/*
 * Jackson Hall
 * CS 110
 * 30 April 2019
 * Boggle: Phase III
 */

import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.geometry.Pos;


/**
 * Custom Pane to render Tiles.
 */
public class TilePane extends GridPane {

    private Tile tile;
    private static String SELECTED_STYLE =
            "-fx-background-color: #FCCA46;" +
            "-fx-border-width: 2;" +
            "-fx-border-color: #E6B840;" +
            "-fx-border-radius: 42;" +
            "-fx-background-radius: 8;";
    private static String UNSELECTED_STYLE =
            "-fx-background-color: #FCF0D2;" +
            "-fx-border-width: 2;" +
            "-fx-border-color: #EADFC2;" +
            "-fx-border-radius: 42;" +
            "-fx-background-radius: 8;";

    /**
     * Create a TilePane object representing given tile.
     * @param tile Tile object to display in the TilePane
     */
    public TilePane(Tile tile){
        this.tile = tile;
        this.setAlignment(Pos.CENTER);
        this.setPrefSize(100,100);
        setUnselected();
        Text tileText = new Text(tile.toString());
        tileText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        this.getChildren().add(tileText);
    }

    /**
     * Return true if this TilePane's Tile isSelected.
     * @return isSelected status
     */
    public boolean isSelected(){
        return tile.isSelected();
    }

    /**
     * Set the style for a selected TilePane.
     */
    public void setSelected() {
        tile.setSelected();
        setStyle(SELECTED_STYLE);
    }

    /**
     * Set the style for a deselected TilePane.
     */
    public void setUnselected(){
        tile.setUnselected();
        setStyle(UNSELECTED_STYLE);
    }

    /**
     * Set this TilePane as selected or unselected based on selected status.
     */
    public void toggleSelected() {
        if (isSelected())
            setUnselected();
        else
            setSelected();
    }

    /**
     * Return this TilePane's Tile object.
     */
    public Tile getTile(){
        return tile;
    }
}
