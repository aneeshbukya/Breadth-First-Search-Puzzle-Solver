package puzzles.chess.model;

import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.*;

/**
 * A class that has the representation, logic and rules of the game.
 * @author Aneesh Bukya
 */
public class ChessModel {
    /** the collection of observers of this model */
    private final List<Observer<ChessModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private ChessConfig currentConfig;
    /** the list containing the neighbours */
    private ArrayList<Configuration> neighbourList = new ArrayList<>();
    /** a set of coordinates used in the select method  */
    private Coordinates coordinates;
    /** stores the name file being read */
    private String nameFile;



    /**
     * The view calls this to add itself as an observer.
     * @param observer the view
     */
    public void addObserver(Observer<ChessModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    /**
     * a getter that returns a cell in the current config
     * @param r - the row in the config
     * @param c - the column in the config
     * @return the chess piece at the location
     */
    public String getCell(int r, int c){
        if (currentConfig.hasCoordinates(new Coordinates(r,c))) {
            return this.currentConfig.getChessMap().get(new Coordinates(r, c));
        }
        return "Invalid";
    }

    /**
     * A method used by GUI and PTUI where the user can quit from and end the program.
     */
    public void quit(){
        alertObservers("");
        System.exit(0);
    }

    /**
     * A method used by GUI and PTUI where it resets the game
     * The previously loaded file should be reloaded, causing the puzzle to return to its initial state.
     * An indication of the reset should be informed to the user.
     */
    public void reset(){
        load(nameFile);
        alertObservers("Puzzle reset!");
    }

    /**
     * A method used by GUI and PTUI which hints the next step to the user when requested
     * When hinting, if the current state of the puzzle is solvable, the puzzle should advance to the next step in
     * the solution with an indication that it was successful. Otherwise, the puzzle should remain in the same state and
     * indicate there is no solution.
     */
    public void hint(){
        Solver solver = new Solver();
        ArrayList<Configuration> solutionList = new ArrayList<>(solver.solve(this.currentConfig));
        if (solutionList.size()==1){
            alertObservers("Already Solved!");
        } else if (solutionList.size()==0) {
            alertObservers("No Solution!");
        } else if (solutionList.get(1) instanceof ChessConfig){
            Configuration config = solutionList.get(1);
            this.currentConfig = (ChessConfig) config;
            alertObservers("> Next step!");
        }
    }

    /**
     *For the first selection, the user should be able to select a cell on the board with the intention of selecting the
     *piece at that location. If there is a piece there, there should be an indication and selection should advance to
     *the second part. Otherwise, if there is no piece there an error message should be displayed and selection has ended.
     * For the second selection, the user should be able to select another cell on the board with the intention of moving
     * the previously selected piece to this location. If the move is valid, it should be made and the board should be
     * updated and with an appropriate indication. If the move is invalid, and error message should be displayed.
     * @param row - the row of the coordinates
     * @param col - the column of the coordinates
     */
    public void select(int row, int col){
        Coordinates coordinates1;
        if (neighbourList.isEmpty()){
            coordinates = new Coordinates(row,col);
            if(!this.currentConfig.getChessMap().get(coordinates).equals(".")){
                neighbourList = new ArrayList<>(this.currentConfig.getNeighbors());
                alertObservers("> Selected "+coordinates.toString());
            }
            else{
                alertObservers("> Invalid selection "+coordinates);
            }
        }
        else{
            coordinates1 = new Coordinates(row,col);
            if (coordinates.equals(coordinates1)){
                alertObservers("> Invalid selection "+coordinates);
                return;
            }
            if(!this.currentConfig.getChessMap().get(coordinates1).equals(".")){
                ChessConfig compareConfig = new ChessConfig(this.currentConfig);
                compareConfig.getChessMap().put(coordinates1,this.currentConfig.getChessMap().get(coordinates));
                compareConfig.getChessMap().put(coordinates,".");
                for (Configuration config :neighbourList){
                    ChessConfig newConfig = (ChessConfig) config;
                    if (newConfig.equals(compareConfig)){
                        this.currentConfig = compareConfig;
                        neighbourList = new ArrayList<>();
                        alertObservers("> Captured from "+coordinates+"  to "+coordinates1);
                        return;
                    }
                }
                alertObservers("> Can't capture from "+coordinates+"  to "+ coordinates1);
                neighbourList = new ArrayList<>();
            }
            else{
                alertObservers("> Can't capture from "+coordinates+"  to "+ coordinates1);
                neighbourList = new ArrayList<>();
            }
        }
    }

    /**
     * When loading, the user will provide the path and name of a puzzle file for the game to load. If the file is
     * readable it is guaranteed to be a valid puzzle file and the new puzzle file should be loaded and displayed,
     * along with an indication of success. If the file cannot be read, an error message should be displayed and the
     * previous puzzle file should remain loaded.
     * @param filename - name of the file
     */
    public void load(String filename){
        try {
            this.nameFile = filename;
            this.currentConfig = new ChessConfig(filename);
            String[] fileSplit = filename.split("/");
            alertObservers("> Loaded: " + fileSplit[fileSplit.length -1]);

        }
        catch (IOException ie){
            System.out.println("> Failed to load: "+filename);
        }
    }

    /**
     * displays the config as a chess board
     * @return the toString of a string builder object
     */
    public String toString(){
        int row =  this.currentConfig.numRows;
        int col = this.currentConfig.numCols;
        StringBuilder config = new StringBuilder();
        System.out.print("   ");
        for (int j = 0; j < col; j++) {
            System.out.print(j+" ");
        }
        System.out.println();
        System.out.print("  ");
        for (int j = 0; j < col; j++) {
            System.out.print("--");
        }
        System.out.println();
        for (int i = 0; i < row; i++) {
            config.append(i).append("| ");
            for (int j = 0; j < col; j++) {
                config.append(this.currentConfig.getChessMap().get(new Coordinates(i, j)));
                config.append(" ");
            }
            config.append("\n");
        }
        return config.toString();
    }

    /**
     * returns the number of rows of the current config
     * @return number of rows
     */
    public int getRows(){
        return this.currentConfig.numRows;
    }
    /**
     * returns the number of columns of the current config
     * @return number of columns
     */
    public int getCols(){
        return this.currentConfig.numCols;
    }
    /**
     * the constructor that loads the info in the chessboard(Hashmap)
     * @param filename - name of the file
     * @throws IOException - if file not found
     */
    public ChessModel(String filename) throws IOException {
        try {
            this.currentConfig = new ChessConfig(filename);
            this.nameFile = filename;
        }
        catch (IOException ie){
            System.err.println("Cannot read word file.");
            System.exit(1);
        }
    }

}
