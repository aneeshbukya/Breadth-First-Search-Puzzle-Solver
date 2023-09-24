package puzzles.chess.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.chess.model.ChessConfig;
import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.chess.model.ChessModel;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * The graphical user interface to the Chess game model in ChessModel
 * @author Aneesh Bukya
 */

public class ChessGUI extends Application implements Observer<ChessModel, String> {
    /** the chess model */
    private ChessModel model;

    /** The size of all icons, in square dimension */
    private final static int ICON_SIZE = 75;
    /** the font size for labels and buttons */
    private final static int FONT_SIZE = 12;
    /** the stage used to display GUI */
    private Stage stage;
    /** the number of rows */
    private int numRows;
    /** the number of columns */
    private int numCols;
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";
    /** the bishop image */
    private Image bishop = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"bishop.png"));
    /** the rook image */
    private Image rook = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"rook.png"));
    /** the king image */
    private Image king = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"king.png"));
    /** the knight image */
    private Image knight = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"knight.png"));
    /** the pawn image */
    private Image pawn = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"pawn.png"));
    /** the queen image */
    private Image queen = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"queen.png"));
    /** the blue image */
    private Image empty = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"blue.png"));
    /** the label that alerts observers when update is called */
    private Label label ;
    /** the name of the file */
    private String nameFile;
    /** the background color instance which is set to white */
    private Background backgroundColor = LIGHT;
    /** a hashmap that stores buttons that use coordinates as keys */
    private HashMap<Coordinates,Button> buttonHashMap;
    /** the gridpane in the center of the borderpane which displays the chess board */
    private GridPane centerGrid;
    /** a definition of light and dark and for the button backgrounds */
    private static final Background LIGHT =
            new Background( new BackgroundFill(Color.WHITE, null, null));
    /** a definition of light and dark and for the button backgrounds */
    private static final Background DARK =
            new Background( new BackgroundFill(Color.MIDNIGHTBLUE, null, null));

    /**
     * initializes all the variables of the class
     */
    @Override
    public void init()  {
        // get the file name from the command line
        String filename = getParameters().getRaw().get(0);
        nameFile = filename;
        centerGrid = new GridPane();
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            this.model = new ChessModel(filename);
            this.buttonHashMap = new HashMap<>();
            model.addObserver(this::update);
            // first line is: rows cols
            String line = in.readLine();
            String[] fields = line.split("\\s+");
            numRows = Integer.parseInt(fields[0]);
            numCols = Integer.parseInt(fields[1]);
            try {
                label = new Label("Loaded: " + filename.substring(filename.indexOf("chess-")));
            }
            catch (NullPointerException exception){
                System.out.println(exception.getMessage());
            }
            label.setStyle("-fx-font: 14px Menlo");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Sets up the GUI.
     * Creates all the components needed to display the chess board properly
     * @param stage - the stage
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        // the main borderpane
        BorderPane mainPane = new BorderPane();
        //bottom of borderpane
        Button loadButton = new Button("LOAD");
        Button resetButton = new Button("RESET");
        Button hintButton = new Button("HINT");
        hintButton.setOnAction(event -> this.model.hint());
        resetButton.setOnAction(event -> this.model.reset());
        loadButton.setOnAction((event) -> {
            FileChooser chooser = new FileChooser();
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            currentPath += File.separator + "data" + File.separator + "chess";  // or "hoppers"
            chooser.setInitialDirectory(new File(currentPath));
            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                this.model.load(file.toString());
            }else{
                update(this.model,"No file chosen");
            }
        });
        loadButton.setStyle("-fx-font: 14px Menlo");
        resetButton.setStyle("-fx-font: 14px Menlo");
        hintButton.setStyle("-fx-font: 14px Menlo");
        HBox box = new HBox();
        box.getChildren().addAll(loadButton, resetButton, hintButton);
        box.setAlignment(Pos.CENTER);
        mainPane.setBottom(box);
        GridPane centerGrid = new GridPane();
        //center of borderpane
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if ((row + col) % 2 == 0) {
                    backgroundColor = LIGHT;
                } else {
                    backgroundColor = DARK;
                }
                if (this.model.getCell(row,col).equals(ChessConfig.ROOK)) {
                    Button button = new Button();
                    button.setMinSize(ICON_SIZE, ICON_SIZE);
                    button.setMaxSize(ICON_SIZE, ICON_SIZE);
                    button.setBackground(backgroundColor);
                    ImageView imageView = new ImageView(rook);
                    button.setGraphic(imageView);
                    int finalRow = row;
                    int finalCol = col;
                    button.setOnAction(event -> this.model.select(finalRow, finalCol));
                    centerGrid.add(button, col, row);
                    buttonHashMap.put(new Coordinates(row, col),button);
                } else if (this.model.getCell(row,col).equals(ChessConfig.KING)) {
                    Button button = new Button();
                    button.setMinSize(ICON_SIZE, ICON_SIZE);
                    button.setMaxSize(ICON_SIZE, ICON_SIZE);
                    button.setBackground(backgroundColor);
                    ImageView imageView = new ImageView(king);
                    button.setGraphic(imageView);
                    int finalRow = row;
                    int finalCol = col;
                    button.setOnAction(event -> this.model.select(finalRow, finalCol));
                    centerGrid.add(button, col, row);
                    buttonHashMap.put(new Coordinates(row, col),button);
                } else if (this.model.getCell(row,col).equals(ChessConfig.KNIGHT)) {
                    Button button = new Button();
                    button.setBackground(backgroundColor);
                    button.setMinSize(ICON_SIZE, ICON_SIZE);
                    button.setMaxSize(ICON_SIZE, ICON_SIZE);
                    ImageView imageView = new ImageView(knight);
                    button.setGraphic(imageView);
                    int finalRow = row;
                    int finalCol = col;
                    button.setOnAction(event -> this.model.select(finalRow, finalCol));
                    centerGrid.add(button, col, row);
                    buttonHashMap.put(new Coordinates(row, col),button);
                } else if (this.model.getCell(row,col).equals(ChessConfig.BISHOP)) {
                    Button button = new Button();
                    button.setBackground(backgroundColor);
                    button.setMinSize(ICON_SIZE, ICON_SIZE);
                    button.setMaxSize(ICON_SIZE, ICON_SIZE);
                    ImageView imageView = new ImageView(bishop);
                    button.setGraphic(imageView);
                    int finalRow = row;
                    int finalCol = col;
                    button.setOnAction(event -> this.model.select(finalRow, finalCol));
                    centerGrid.add(button, col, row);
                    buttonHashMap.put(new Coordinates(row, col),button);
                } else if (this.model.getCell(row,col).equals(ChessConfig.QUEEN)) {
                    Button button = new Button();
                    button.setBackground(backgroundColor);
                    button.setMinSize(ICON_SIZE, ICON_SIZE);
                    button.setMaxSize(ICON_SIZE, ICON_SIZE);
                    ImageView imageView = new ImageView(queen);
                    button.setGraphic(imageView);
                    int finalRow = row;
                    int finalCol = col;
                    button.setOnAction(event -> this.model.select(finalRow, finalCol));
                    centerGrid.add(button, col, row);
                    buttonHashMap.put(new Coordinates(row, col),button);
                } else if (this.model.getCell(row,col).equals(ChessConfig.PAWN)) {
                    Button button = new Button();
                    button.setBackground(backgroundColor);
                    button.setMinSize(ICON_SIZE, ICON_SIZE);
                    button.setMaxSize(ICON_SIZE, ICON_SIZE);
                    ImageView imageView = new ImageView(pawn);
                    button.setGraphic(imageView);
                    int finalRow = row;
                    int finalCol = col;
                    button.setOnAction(event -> this.model.select(finalRow, finalCol));
                    centerGrid.add(button, col, row);
                    buttonHashMap.put(new Coordinates(row, col),button);
                } else if (this.model.getCell(row,col).equals(ChessConfig.EMPTY)) {
                    Button button = new Button();
                    button.setMinSize(ICON_SIZE, ICON_SIZE);
                    button.setMaxSize(ICON_SIZE, ICON_SIZE);
                    button.setBackground(backgroundColor);
                    int finalRow = row;
                    int finalCol = col;
                    button.setOnAction(event -> this.model.select(finalRow, finalCol));
                    centerGrid.add(button, col, row);
                    buttonHashMap.put(new Coordinates(row, col),button);
                }
            }
        }
        centerGrid.setAlignment(Pos.CENTER);
        mainPane.setCenter(centerGrid);
        //top of borderpane
        HBox headingBox = new HBox();
        headingBox.getChildren().add(label);
        headingBox.setAlignment(Pos.CENTER);
        mainPane.setTop(headingBox);
        stage.setTitle("Chess GUI");

        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * updates the view once it receives from model
     * @param chessModel the object that wishes to inform this object
     *                about something that has happened.
     * @param msg optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(ChessModel chessModel, String msg) {
        this.stage.sizeToScene();  // when a different sized puzzle is loaded
        label.setText(msg);
        numRows = this.model.getRows();
        numCols = this.model.getCols();
        try {
            if (msg.contains("Loaded")) {
                start(this.stage);
            }
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    if ((i + j) % 2 == 0) {
                        backgroundColor = LIGHT;
                    } else {
                        backgroundColor = DARK;
                    }
                    if (this.model.getCell(i,j).equals(ChessConfig.ROOK)) {
                        Button button = this.buttonHashMap.get(new Coordinates(i, j));
                        button.setBackground(backgroundColor);
                        button.setMinSize(ICON_SIZE, ICON_SIZE);
                        button.setMaxSize(ICON_SIZE, ICON_SIZE);
                        ImageView imageView = new ImageView(rook);
                        button.setGraphic(imageView);
                    } else if (this.model.getCell(i,j).equals(ChessConfig.PAWN)) {
                        Button button = this.buttonHashMap.get(new Coordinates(i, j));
                        button.setBackground(backgroundColor);
                        button.setMinSize(ICON_SIZE, ICON_SIZE);
                        button.setMaxSize(ICON_SIZE, ICON_SIZE);
                        ImageView imageView = new ImageView(pawn);
                        button.setGraphic(imageView);

                    } else if (this.model.getCell(i,j).equals(ChessConfig.BISHOP)) {
                        Button button = this.buttonHashMap.get(new Coordinates(i, j));
                        button.setBackground(backgroundColor);
                        button.setMinSize(ICON_SIZE, ICON_SIZE);
                        button.setMaxSize(ICON_SIZE, ICON_SIZE);
                        ImageView imageView = new ImageView(bishop);
                        button.setGraphic(imageView);

                    } else if (this.model.getCell(i,j).equals(ChessConfig.KING)) {
                        Button button = this.buttonHashMap.get(new Coordinates(i, j));
                        button.setBackground(backgroundColor);
                        button.setMinSize(ICON_SIZE, ICON_SIZE);
                        button.setMaxSize(ICON_SIZE, ICON_SIZE);
                        ImageView imageView = new ImageView(king);
                        button.setGraphic(imageView);

                    } else if (this.model.getCell(i,j).equals(ChessConfig.QUEEN)) {
                        Button button = this.buttonHashMap.get(new Coordinates(i, j));
                        button.setBackground(backgroundColor);
                        button.setMinSize(ICON_SIZE, ICON_SIZE);
                        button.setMaxSize(ICON_SIZE, ICON_SIZE);
                        ImageView imageView = new ImageView(queen);
                        button.setGraphic(imageView);

                    } else if (this.model.getCell(i,j).equals(ChessConfig.KNIGHT)) {
                        Button button = this.buttonHashMap.get(new Coordinates(i, j));
                        button.setBackground(backgroundColor);
                        button.setMinSize(ICON_SIZE, ICON_SIZE);
                        button.setMaxSize(ICON_SIZE, ICON_SIZE);
                        ImageView imageView = new ImageView(knight);
                        button.setGraphic(imageView);

                    } else if (this.model.getCell(i,j).equals(ChessConfig.EMPTY)) {
                        Button button = this.buttonHashMap.get(new Coordinates(i, j));
                        button.setMinSize(ICON_SIZE, ICON_SIZE);
                        button.setMaxSize(ICON_SIZE, ICON_SIZE);
                        button.setBackground(backgroundColor);
                        button.setGraphic(null);
                    }
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * runs the application
     * @param args - command line
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
