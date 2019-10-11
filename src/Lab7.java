/*
 * Jackson Hall
 * CS 110
 * 16 April 2019
 * Boggle: Phase II
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


public class Lab7 extends Application
{
    public void start(Stage stage)
    {
        Tile t1 = new Tile("Qu",0,0);
        Tile t2 = new Tile('a',0,1);
        Tile t3 = new Tile('x',1,0);
        Tile t4 = new Tile('e',1,1);

        TilePane tp1 = new TilePane(t1);
        TilePane tp2 = new TilePane(t2);
        tp2.setSelected();
        TilePane tp3 = new TilePane(t3);
        TilePane tp4 = new TilePane(t4);
        tp4.setSelected();

        GridPane gp = new GridPane();
        //remember, add takes col, row
        gp.add(tp1,0,0);

        gp.add(tp2,1,0);
        gp.add(tp3,0,1);
        gp.add(tp4,1,1);
        Scene scene = new Scene(gp);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String [] args)
    {
        launch(args);
    }
}