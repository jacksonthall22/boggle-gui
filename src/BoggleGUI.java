/*
 * Jackson Hall
 * CS 110
 * 30 April 2019
 * Boggle: Phase III
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;


@SuppressWarnings("FieldCanBeLocal")
public class BoggleGUI extends Application implements EventHandler<MouseEvent> {

    private Game game;
    private String message;
    private Scene newGameScene, titleScene;
    private GridPane gameScenePane, gameBoardPane;
    private VBox titleScenePane, wordsPane, timePane, endGamePane, otherWordsPane;
    private HBox titlePane, messagePane, wordEntryPane, boardControlsPane;
    private Button beginGameButton, clearButton, submitButton, deleteButton, newGameButton, quitButton;
    private Label titleSceneTitle, titleSceneSubtitle, title, wordsTitle, scoreBody, time, endGameTitle,
            otherWordsTitle, otherWordsBody;
    private Text messageBody;
    private TextArea wordsBody;
    private TextField wordEntryField;
    private TilePane tp1, tp2, tp3, tp4, tp5, tp6, tp7, tp8, tp9, tp10, tp11, tp12, tp13, tp14, tp15, tp16;

    private final int[] DEFAULT_WINDOW_DIMENSIONS = {1040, 650};
    private final String DEFAULT_WORDS_BODY_TEXT = "No words yet!";
    private static final double DEFAULT_COUNTDOWN_TIME = 5;
    private static final double DEFAULT_GRACE_PERIOD = 0.5;


    /**
     * Start JavaFX Application.
     */
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Start the game's GUI.
     * @param primaryStage the primary stage for this BoggleGUI
     */
    @SuppressWarnings({"Duplicates", "DuplicateExpressions"})
    @Override
    public void start(Stage primaryStage) {

        // Make window not resizable
        primaryStage.setResizable(false);

        // Set up begin game button
        beginGameButton = new Button("Start Game");
        beginGameButton.setStyle(
                "-fx-font-size: 14;"
        );
        beginGameButton.setPadding(new Insets(8, 12, 8, 12));
        beginGameButton.setOnAction(event -> primaryStage.setScene(initNewGameScene()));

        // Set up new game button (displays after game finishes)
        newGameButton = new Button("Play Again");
        newGameButton.setStyle(
                "-fx-font-size: 14;"
        );
        newGameButton.setPadding(new Insets(8, 12, 8, 12));
        newGameButton.setOnAction(event -> primaryStage.setScene(initNewGameScene()));

        // Set up continue to end screen button
        quitButton = new Button("Quit \uD83E\uDC1A");
        quitButton.setStyle(
                "-fx-font-size: 14px;"
        );
        quitButton.setPadding(new Insets(8, 12, 8, 12));
        quitButton.setOnAction(event -> primaryStage.close());

        // Set up the title screen
        primaryStage.setScene(initTitleScene());
        primaryStage.setTitle("Jackson's Boggle");
        primaryStage.show();
    }

    /**
     * Initialize and return a title Scene.
     * @return title screen Scene
     */
    public Scene initTitleScene(){

        // Set up Title screen
        titleScenePane = new VBox();
        titleScenePane.setAlignment(Pos.CENTER);
        titleScenePane.setSpacing(20);
        titleSceneTitle = new Label("Welcome to Boggle!");
        titleSceneTitle.setStyle(
                "-fx-font-family: verdana;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 50px;"
        );
        titleSceneSubtitle = new Label("Created by Jackson Hall");
        titleSceneSubtitle.setStyle(
                "-fx-font-family: verdana;" +
                "-fx-font-size: 20px;"
        );
        titleScenePane.getChildren().addAll(titleSceneTitle, titleSceneSubtitle, beginGameButton);
        titleScene = new Scene(titleScenePane, DEFAULT_WINDOW_DIMENSIONS[0], DEFAULT_WINDOW_DIMENSIONS[1]);

        return titleScene;
    }

    /**
     * Initialize and return a new game scene.
     * @return Scene for a new Game
     */
    @SuppressWarnings("Duplicates")
    public Scene initNewGameScene(){

        /*
            >>>  spaghetticode.exe
            >>>
        */

        // Create new scene and main pane
        gameScenePane = new GridPane();
        newGameScene = new Scene(gameScenePane, DEFAULT_WINDOW_DIMENSIONS[0], DEFAULT_WINDOW_DIMENSIONS[1]);

        // Add CSS to main game scene
        // Adapted from:  https://stackoverflow.com/questions/33434588/eclipse-javafx-css-resource-not-found?rq=1
        String stylesheet = fileToStylesheetString(new File("css/styles.css"));
        // Effectively catch NPEs / other exceptions
        if (stylesheet != null)
            newGameScene.getStylesheets().add(stylesheet);

        // Initialize message shown to user as blank
        message = "Go!";

        // Create the Game
        game = new Game();

        // Creat all Labels
        title = new Label("Boggle!");
        title.setStyle(
                "-fx-font-family: verdana;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 40;" +
                "-fx-padding: 0 0 22 0;" +
                "-fx-text-fill: #F96900;"
        );
//        title.setPadding(new Insets(0, 0, 22, 0));
        wordsTitle = new Label("Guessed Words");
        wordsTitle.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 20;" +
                "-fx-font-weight: bold;"
        );
        wordsBody = new TextArea(DEFAULT_WORDS_BODY_TEXT);
        wordsBody.setStyle(
                "-fx-font-family: 'Courier New';" +
                "-fx-font-size: 14;" +
                "-fx-opacity: 1;"
        );
        wordsBody.setPrefSize(400, 420);
        wordsBody.setDisable(true);
        scoreBody = new Label(String.format("%-18s", "Total Points:")
                + String.format("%9s", String.format("%d point%s", game.getScore(), game.getScore() == 1 ? "\n" : "s\n")
        ));
        scoreBody.setStyle(
                "-fx-font-family: 'Courier New';" +
                "-fx-font-size: 14;"
        );
        time = new Label("Time: 90");
        time.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 20;" +
                "-fx-text-fill: #F96900;" +
                "-fx-font-weight: bold;"
        );
        messageBody = new Text(message);
        messageBody.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16px"
        );
        messageBody.setTextAlignment(TextAlignment.CENTER);

        // Set up all Buttons
        deleteButton = new Button("X");
        clearButton = new Button("Clear");
        submitButton = new Button("Try Word");

        // Style buttons
        clearButton.setStyle(
                "-fx-font-size: 14;"
        );
        submitButton.setStyle(
                "-fx-font-size: 14;"
        );

        // Set up word entry Pane and TextField
        wordEntryField = new TextField();
        wordEntryField.setPromptText("Enter a word..."); // Sets the hint text
        wordEntryField.setPrefWidth(400);
        wordEntryPane = new HBox();
        wordEntryPane.getChildren().addAll(wordEntryField, deleteButton);

        // Set up button actions
        clearButton.setOnAction(event -> {
            game.clearSelected();
            refreshBoard();
        });
        submitButton.setOnAction(event -> {
            // User hasn't selected anything -> return
            if (game.getSelectedTiles().size() == 0){
                message = "Select some dice first!";
                return;
            }

            // Game is over -> return
            if (game.isOver()){
                message = "The game is over!";
                return;
            }

            Word word = new Word(game.getSelectedTiles());

            // Make sure word is valid
            if (game.testSelected()){
                // Test if word has already been guessed
                for (Word w : game.getGuessedWords()){
                    if (w.toString().equalsIgnoreCase(word.toString())){
                        message = String.format("%s has already been guessed.", word.toString().toUpperCase());
                        wordEntryField.clear();
                        game.clearSelected();
                        refreshBoard();
                        return;
                    }
                }

                // Only make selection if word hasn't been guessed
                game.addToSelectedWords(word);
                game.addToScore(word.getPoints());
                message = String.format("You got %d point%s for %s!",
                        Word.getPoints(word.toString()),
                        Word.getPoints(word.toString()) == 1 ? "" : "s",
                        word.toString().toUpperCase());
            } else {
                message = word.toString().toUpperCase() + " is not a word.";
            }

            // Clear selection either way
            wordEntryField.clear();
            game.clearSelected();
            refreshBoard();
        });
        deleteButton.setOnAction(event -> {
            wordEntryField.clear();
            refreshBoard();
        });
        wordEntryField.setOnKeyPressed(keyEvent -> {
            // Game is over -> return
            if (game.isOver()){
                message = "The game is over!";
                return;
            }

            // Respond only to enter key pressed
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                String text = wordEntryField.getText();

                // User enters no text -> return
                if (text.length() == 0) {
                    message = "Enter a word first!";
                    return;
                }

                Word word = new Word(text);

                // Test if word has already been guessed
                for (Word w : game.getGuessedWords()){
                    if (w.toString().equalsIgnoreCase(word.toString())){
                        message = String.format("%s has already been guessed.", word.toString().toUpperCase());
                        wordEntryField.clear();
                        game.clearSelected();
                        refreshBoard();
                        return;
                    }
                }

                try {
                    if (game.getBoard().testWord(text)){
                        // Word is valid
                        if (game.getDictionary().isValidWord(word)){
                            // Word has not already been guessed
                            game.addToSelectedWords(word);
                            game.addToScore(word.getPoints());
                            message = String.format("You got %d point%s for %s!",
                                    Word.getPoints(text),
                                    Word.getPoints(text) == 1 ? "" : "s",
                                    text.toUpperCase());
                        } else {
                            message = text.toUpperCase() + " is not a word.";
                        }
                    } else {
                        message = text.toUpperCase() + " is not in the board.";
                    }
                } catch (NullPointerException npe){
                    message = "That isn't a valid selection.";
                }

                // Clear entry whether or not word was valid
                wordEntryField.clear();
                game.clearSelected();
                refreshBoard();
            }
        });

        // Set up gameScenePane and its column widths
        gameScenePane.setPrefSize(DEFAULT_WINDOW_DIMENSIONS[0], DEFAULT_WINDOW_DIMENSIONS[0]);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(300);
        col1.setMaxWidth(300);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(440);
        col2.setMaxWidth(440);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setMinWidth(300);
        col3.setMaxWidth(300);
        gameScenePane.setPadding(new Insets(10, 10, 10, 10));
        gameScenePane.setVgap(8);
        gameScenePane.setHgap(15);

        // Set up title Pane
        titlePane = new HBox();
        titlePane.getChildren().addAll(title);
        titlePane.setAlignment(Pos.CENTER);

        // Set up words Pane and add Buttons
        wordsPane = new VBox();
        wordsPane.getChildren().addAll(wordsTitle, wordsBody, scoreBody);
        wordsPane.setAlignment(Pos.TOP_CENTER);

        // Set up message pane
        messagePane = new HBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageBody);

        // Set up game board and add TilePanes
        gameBoardPane = new GridPane();
        refreshBoard();

        // Set game board style
        gameBoardPane.setStyle(
                "-fx-background-color: #15385E;" +
                "-fx-background-radius: 20;" +
                "-fx-padding: 30;" +
                "-fx-margin: auto;"
        );
        gameBoardPane.setHgap(5);
        gameBoardPane.setVgap(5);
        gameBoardPane.setMinSize(420,420);
        gameBoardPane.setMaxSize(420,420);

        // Set up board controls pane (Clear, Submit)
        boardControlsPane = new HBox();
        boardControlsPane.setPadding(new Insets(15, 12, 15, 12));
        boardControlsPane.setSpacing(30);
        boardControlsPane.getChildren().addAll(clearButton, submitButton);
        boardControlsPane.setAlignment(Pos.CENTER);

        // Set up time pane
        timePane = new VBox();
        timePane.getChildren().addAll(time);
        timePane.setAlignment(Pos.TOP_CENTER);
        timePane.setPrefSize(280, 420);
        timePane.setSpacing(40);
        timePane.setMinHeight(420);

        // Add all panes to the main GridPane
        gameScenePane.add(titlePane,1,1);
        gameScenePane.add(wordsPane,1,2);
        gameScenePane.add(messagePane, 1, 3);
        gameScenePane.add(wordEntryPane,2,1);
        gameScenePane.add(gameBoardPane,2,2);
        gameScenePane.add(boardControlsPane,2,3);
        gameScenePane.add(timePane,3,2);

        // Start the countdown
        doTime(DEFAULT_COUNTDOWN_TIME, DEFAULT_GRACE_PERIOD);

        return newGameScene;
    }

    /**
     * Creates a countdown timer running in parallel in the time Label from given number of seconds.
     * Adapted from: https://www.youtube.com/watch?v=t2Bv6hwELsU&ab_channel=PaschalGangmei
     * @param startTimeSeconds number of seconds to count down from
     * @param gracePeriod number of seconds to wait before counting down
     */
    @SuppressWarnings("SameParameterValue")
    private void doTime(Double startTimeSeconds, Double gracePeriod) {

        // Must be effectively final for anonymous inner class to have access
        final Double[] startTimeSecondsFinal = {startTimeSeconds};

        final double[] actualTimeFinal = {startTimeSeconds + gracePeriod};

        // Create a Timeline to run in parallel
        Timeline timeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(0.01), event -> {
            actualTimeFinal[0] -= 0.01;

            // Only set the text after the grace period
            if (actualTimeFinal[0] < startTimeSecondsFinal[0])
                time.setText(String.format("Time: %.2fs", actualTimeFinal[0]));

            if (actualTimeFinal[0] <= 0){
                time.setText("Time: 0s");
                timeline.stop();

                // Game is over
                game.setIsOver(true);

                // Add content to endGamePane
                endGameTitle = new Label(
                        String.format("You got %d word%s for\na score of %d point%s!",
                                game.getGuessedWords().size(),
                                game.getGuessedWords().size() == 1 ? "" : "s",
                                game.getScore(), game.getGuessedWords().size() == 1 ? "" : "s")
                );
                endGameTitle.setStyle(
                        "-fx-font-family: Verdana;" +
                                "-fx-font-size: 16px;"
                );
                // List of missed words
                otherWordsTitle = new Label();
                otherWordsTitle.setStyle(
                        "-fx-font-family: Verdana;" +
                                "-fx-font-size: 14;"
                );
                otherWordsTitle.setText("Here are some you missed:");
                otherWordsBody = new Label();
                otherWordsBody.setStyle(
                        "-fx-font-family: Verdana;" +
                                "-fx-font-size: 14;"
                );
                StringBuilder sb = new StringBuilder();
                ArrayList<String> missedWords = game.getPossibleWords();
                int listedWords = 0;
                int wordIndex = 0;
                if (missedWords.size() != 0) {
                    while (!missedWords.isEmpty() && listedWords < 10 && wordIndex < missedWords.size()) {
                        if (!game.getGuessedWords().contains(new Word(missedWords.get(wordIndex)))) {
                            Word missedWord = new Word(missedWords.get(wordIndex));
                            sb.append(" ").append(listedWords + 1).append(".  ")
                                    .append(missedWord).append(" (").append(missedWord.getPoints()).append("pts)\n");
                            listedWords++;
                        }
                        wordIndex++;
                    }
                }
                otherWordsBody.setText(sb.toString());
                otherWordsPane = new VBox();
                otherWordsPane.setAlignment(Pos.TOP_CENTER);
                otherWordsPane.getChildren().addAll(otherWordsTitle, otherWordsBody);

                endGamePane = new VBox();
                endGamePane.setSpacing(20);
                endGamePane.setAlignment(Pos.TOP_CENTER);
                endGamePane.getChildren().addAll(endGameTitle, otherWordsPane, newGameButton, quitButton);

                // Add this pane to timePane
                timePane.getChildren().add(endGamePane);

                // Set message and refresh board
                message = "Time's up!";
                refreshBoard();
            }
        });

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    /**
     * Initialize the gameBoardPane.
     */
    @SuppressWarnings("Duplicates")
    private void refreshBoard(){

        // Initialize all TilePanes
        tp1  = new TilePane(game.getBoard().get(0,0));
        tp2  = new TilePane(game.getBoard().get(0,1));
        tp3  = new TilePane(game.getBoard().get(0,2));
        tp4  = new TilePane(game.getBoard().get(0,3));
        tp5  = new TilePane(game.getBoard().get(1,0));
        tp6  = new TilePane(game.getBoard().get(1,1));
        tp7  = new TilePane(game.getBoard().get(1,2));
        tp8  = new TilePane(game.getBoard().get(1,3));
        tp9  = new TilePane(game.getBoard().get(2,0));
        tp10 = new TilePane(game.getBoard().get(2,1));
        tp11 = new TilePane(game.getBoard().get(2,2));
        tp12 = new TilePane(game.getBoard().get(2,3));
        tp13 = new TilePane(game.getBoard().get(3,0));
        tp14 = new TilePane(game.getBoard().get(3,1));
        tp15 = new TilePane(game.getBoard().get(3,2));
        tp16 = new TilePane(game.getBoard().get(3,3));

        // Set click events
        tp1.setOnMouseClicked(this);
        tp2.setOnMouseClicked(this);
        tp3.setOnMouseClicked(this);
        tp4.setOnMouseClicked(this);
        tp5.setOnMouseClicked(this);
        tp6.setOnMouseClicked(this);
        tp7.setOnMouseClicked(this);
        tp8.setOnMouseClicked(this);
        tp9.setOnMouseClicked(this);
        tp10.setOnMouseClicked(this);
        tp11.setOnMouseClicked(this);
        tp12.setOnMouseClicked(this);
        tp13.setOnMouseClicked(this);
        tp14.setOnMouseClicked(this);
        tp15.setOnMouseClicked(this);
        tp16.setOnMouseClicked(this);

        // Add all TilePanes to gameBoardPane
        GridPane.setConstraints(tp1, 0, 0);
        GridPane.setConstraints(tp2, 1, 0);
        GridPane.setConstraints(tp3, 2, 0);
        GridPane.setConstraints(tp4, 3, 0);
        GridPane.setConstraints(tp5, 0, 1);
        GridPane.setConstraints(tp6, 1, 1);
        GridPane.setConstraints(tp7, 2, 1);
        GridPane.setConstraints(tp8, 3, 1);
        GridPane.setConstraints(tp9, 0, 2);
        GridPane.setConstraints(tp10,1, 2);
        GridPane.setConstraints(tp11,2, 2);
        GridPane.setConstraints(tp12,3, 2);
        GridPane.setConstraints(tp13,0, 3);
        GridPane.setConstraints(tp14,1, 3);
        GridPane.setConstraints(tp15,2, 3);
        GridPane.setConstraints(tp16,3, 3);
        gameBoardPane.getChildren().addAll(
                tp1,  tp2,  tp3,  tp4,
                tp5,  tp6,  tp7,  tp8,
                tp9,  tp10, tp11, tp12,
                tp13, tp14, tp15, tp16
        );

        // Update wordsBody
        StringBuilder sb;
        if (game.getGuessedWords().size() == 0)
            wordsBody.setText(DEFAULT_WORDS_BODY_TEXT);
        else {
            sb = new StringBuilder();

            for (int i = 0; i < game.getGuessedWords().size(); i++){
                Word word = game.getGuessedWords().get(i);
                sb.append(String.format("%-18s", word.toString().toUpperCase()))
                  .append(String.format(
                          "%9s", String.format("%d point" + (word.getPoints() == 1 ? "\n" : "s\n"), word.getPoints()))
                  );
            }

            wordsBody.setText(sb.toString());
        }

        // Update score
        scoreBody.setText(String.format("%-18s", "TOTAL POINTS:")
                + String.format(
                        "%9s", String.format("%d point" + (game.getScore() == 1 ? "" : "s"), game.getScore())
                )
                + String.format("\n%-18s", "WORDS GUESSED:")
                + String.format(
                        "%9s", String.format(
                                "%d / %d", game.getGuessedWords().size(),
                                           game.getPossibleWords().size() - game.getGuessedWords().size()
                        )
                )
        );

        messageBody.setText(message);
    }

    /**
     * Handle MouseEvents, specifically for TilePane actions.
     * @param event the MouseEvent
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void handle(MouseEvent event){

        // Handle TilePane toggleSelected if it's a valid selection
        if (event.getSource() instanceof TilePane) {
            // event.getSource() is Object, must cast to TilePane
            TilePane sourceTilePane = (TilePane) event.getSource();

            // Short-circuit execution will prevent NullPointerException when getLastSelected() returns null
            if (game.getLastSelected() == null
                    || game.isValidSelection(sourceTilePane.getTile().getCoords())){
                game.addToSelected(sourceTilePane.getTile());
                sourceTilePane.toggleSelected();
            } else if (game.getLastSelected() == sourceTilePane.getTile()){
                game.removeLastFromSelected();
                sourceTilePane.toggleSelected();
            }
        }
    }

    /**
     * Creates String representation of File that can be parsed when adding a stylesheet.
     * Adapted from:  https://stackoverflow.com/questions/33434588/eclipse-javafx-css-resource-not-found?rq=1
     * @param stylesheetFile File object
     * @return String representing filepath
     */
    private String fileToStylesheetString(File stylesheetFile){
        try {
            return stylesheetFile.toURI().toURL().toString();
        } catch (MalformedURLException e){
            return null;
        }
    }

}
