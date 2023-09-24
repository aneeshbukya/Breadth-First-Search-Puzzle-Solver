package puzzles.chess.model;

import puzzles.common.Coordinates;
import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *The main class for representing the Chess puzzle
 * @author Aneesh Bukya
 */
public class ChessConfig implements Configuration {
    /** stores the number of rows */
    protected int numRows;
    /** stores the number of columns */
    protected int numCols;
    /** stores the string for a BISHOP */
    public static final String BISHOP = "B";
    /** stores the string for a KING */
    public static final String KING = "K";
    /** stores the string for a KNIGHT */
    public static final String KNIGHT = "N";
    /** stores the string for a PAWN */
    public static final String PAWN = "P";
    /** stores the string for a QUEEN */
    public static final String QUEEN = "Q";
    /** stores the string for a ROOK */
    public static final String ROOK = "R";
    /** stores the string for a EMPTY */
    public static final String EMPTY = ".";
    /** a hashmap that stores the pieces on the chess board with a coordinate key system  */
    private HashMap<Coordinates, String> chessMap = new HashMap<>();
    /** a list of configs  */
    private ArrayList<Configuration> neighbourList;

    /**
     * the constructor that loads the info in the chessboard(Hashmap)
     * @param filename - name of the file
     * @throws IOException - if file not found
     */
    public ChessConfig(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            // first line is: rows cols
            String line = in.readLine();
            String[] fields = line.split("\\s+");
            numRows = Integer.parseInt(fields[0]);
            numCols = Integer.parseInt(fields[1]);
            for (int i = 0; i < numRows; i++) {
                line = in.readLine();
                fields = line.split("\\s+");
                for (int j = 0; j < numCols; j++) {
                    chessMap.put(new Coordinates(i, j), fields[j]);
                }
            }
        }
        neighbourList = new ArrayList<>();
    }

    /**
     * Copy constructor.  Takes a config, other, and makes a full "deep" copy
     * of its instance data.
     * @param other the config to copy
     */
    public ChessConfig(ChessConfig other) {
        this.numRows = other.numRows;
        this.numCols = other.numCols;
        this.chessMap = new HashMap<>();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                String word = other.chessMap.get(new Coordinates(i, j));
                String newWord = String.valueOf(word);
                this.chessMap.put(new Coordinates(i, j), newWord);
            }
        }
        neighbourList = new ArrayList<>();
    }

    /**
     * tells us if the config is a solution or not
     * @return true or false
     */
    @Override
    public boolean isSolution() {
        int numPieces = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (!chessMap.get(new Coordinates(i, j)).equals(EMPTY)) {
                    numPieces++;
                }
            }
        }
        return (numPieces == 1);
    }

    /**
     * tells us if the pair of coordinates exists in the current config
     * @param location - a pair of coordinates
     * @return true or false
     */
    public boolean hasCoordinates(Coordinates location) {
        return location.row() < numRows && location.row() >= 0 && location.col() < numCols && location.col() >= 0;
    }

    /**
     * gets the neighbours of the current configs
     * @return a list of neighbour configs
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (chessMap.get(new Coordinates(i, j)).equals(BISHOP)) {
                    int x = 1;
                    int y = 1;
                    while (hasCoordinates(new Coordinates(i - x, j + y))) {
                        if (!chessMap.get(new Coordinates(i - x, j + y)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i - x, j + y), BISHOP);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                        y++;
                    }
                    x = 1;
                    y = 1;
                    while (hasCoordinates(new Coordinates(i + x, j + y))) {
                        if (!chessMap.get(new Coordinates(i + x, j + y)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i + x, j + y), BISHOP);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                        y++;
                    }
                    x = 1;
                    y = 1;
                    while (hasCoordinates(new Coordinates(i - x, j - y))) {
                        if (!chessMap.get(new Coordinates(i - x, j - y)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i - x, j - y), BISHOP);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                        y++;
                    }
                    x = 1;
                    y = 1;
                    while (hasCoordinates(new Coordinates(i + x, j - y))) {
                        if (!chessMap.get(new Coordinates(i + x, j - y)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i + x, j - y), BISHOP);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                        y++;
                    }
                }
                else if (chessMap.get(new Coordinates(i, j)).equals(KNIGHT)) {
                    if (hasCoordinates(new Coordinates(i - 2, j - 1)) && !chessMap.get(new Coordinates(i - 2, j - 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i - 2, j - 1), KNIGHT);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i - 2, j + 1)) && !chessMap.get(new Coordinates(i - 2, j + 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i - 2, j + 1), KNIGHT);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i - 1, j + 2)) && !chessMap.get(new Coordinates(i - 1, j + 2)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i - 1, j + 2), KNIGHT);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i + 1, j + 2)) && !chessMap.get(new Coordinates(i + 1, j + 2)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i + 1, j + 2), KNIGHT);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i + 2, j + 1)) && !chessMap.get(new Coordinates(i + 2, j + 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i + 2, j + 1), KNIGHT);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i + 2, j - 1)) && !chessMap.get(new Coordinates(i + 2, j - 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i + 2, j - 1), KNIGHT);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i + 1, j - 2)) && !chessMap.get(new Coordinates(i + 1, j - 2)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i + 1, j - 2), KNIGHT);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i - 1, j - 2)) && !chessMap.get(new Coordinates(i - 1, j - 2)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i - 1, j - 2), KNIGHT);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                }
                else if (chessMap.get(new Coordinates(i, j)).equals(PAWN)) {
                    if (hasCoordinates(new Coordinates(i - 1, j + 1)) && !chessMap.get(new Coordinates(i - 1, j + 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i - 1, j + 1), PAWN);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i - 1, j - 1)) && !chessMap.get(new Coordinates(i - 1, j - 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig((this));
                        config.chessMap.put(new Coordinates(i - 1, j - 1), PAWN);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                }
                else if (chessMap.get(new Coordinates(i, j)).equals(QUEEN)) {
                    int x = 1;
                    int y = 1;
                    while (hasCoordinates(new Coordinates(i - x, j + y))) {
                        if (!chessMap.get(new Coordinates(i - x, j + y)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i - x, j + y), QUEEN);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                        y++;
                    }
                    x = 1;
                    y = 1;
                    while (hasCoordinates(new Coordinates(i + x, j + y))) {
                        if (!chessMap.get(new Coordinates(i + x, j + y)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i + x, j + y), QUEEN);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                        y++;
                    }
                    x = 1;
                    y = 1;
                    while (hasCoordinates(new Coordinates(i - x, j - y))) {
                        if (!chessMap.get(new Coordinates(i - x, j - y)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i - x, j - y), QUEEN);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                        y++;
                    }
                    x = 1;
                    y = 1;
                    while (hasCoordinates(new Coordinates(i + x, j - y))) {
                        if (!chessMap.get(new Coordinates(i + x, j - y)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i + x, j - y), QUEEN);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                        y++;
                    }
                    x = 1;
                    y = 1;
                    while (hasCoordinates(new Coordinates(i - x, j))) {
                        if (!chessMap.get(new Coordinates(i - x, j)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i - x, j), QUEEN);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                    }
                    x = 1;
                    while (hasCoordinates(new Coordinates(i + x, j))) {
                        if (!chessMap.get(new Coordinates(i + x, j)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i + x, j), QUEEN);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                    }
                    x = 1;
                    while (hasCoordinates(new Coordinates(i, j - x))) {
                        if (!chessMap.get(new Coordinates(i, j - x)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i, j - x), QUEEN);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                    }
                    x = 1;
                    while (hasCoordinates(new Coordinates(i, j + x))) {
                        if (!chessMap.get(new Coordinates(i, j + x)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i, j + x), QUEEN);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                    }
                }
                else if (chessMap.get(new Coordinates(i, j)).equals(ROOK)) {
                    int x = 1;
                    while (hasCoordinates(new Coordinates(i - x, j))) {
                        if (!chessMap.get(new Coordinates(i - x, j)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i - x, j), ROOK);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                    }
                    x = 1;
                    while (hasCoordinates(new Coordinates(i + x, j))) {
                        if (!chessMap.get(new Coordinates(i + x, j)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i + x, j), ROOK);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                    }
                    x = 1;
                    while (hasCoordinates(new Coordinates(i, j - x))) {
                        if (!chessMap.get(new Coordinates(i, j - x)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i, j - x), ROOK);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                    }
                    x = 1;
                    while (hasCoordinates(new Coordinates(i, j + x))) {
                        if (!chessMap.get(new Coordinates(i, j + x)).equals(EMPTY)) {
                            ChessConfig config = new ChessConfig((this));
                            config.chessMap.put(new Coordinates(i, j + x), ROOK);
                            config.chessMap.put(new Coordinates(i, j), EMPTY);
                            neighbourList.add(config);
                            break;
                        }
                        x++;
                    }
                }
                else if (chessMap.get(new Coordinates(i, j)).equals(KING)) {
                    if (hasCoordinates(new Coordinates(i - 1, j)) && !chessMap.get(new Coordinates(i - 1, j)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i - 1, j), KING);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i + 1, j)) && !chessMap.get(new Coordinates(i + 1, j)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i + 1, j), KING);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i, j - 1)) && !chessMap.get(new Coordinates(i, j - 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i, j - 1), KING);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i, j + 1)) && !chessMap.get(new Coordinates(i, j + 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i, j + 1), KING);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i - 1, j + 1)) && !chessMap.get(new Coordinates(i - 1, j + 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i - 1, j + 1), KING);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i + 1, j + 1)) && !chessMap.get(new Coordinates(i + 1, j + 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i + 1, j + 1), KING);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i - 1, j - 1)) && !chessMap.get(new Coordinates(i - 1, j - 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i - 1, j - 1), KING);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                    if (hasCoordinates(new Coordinates(i + 1, j - 1)) && !chessMap.get(new Coordinates(i + 1, j - 1)).equals(EMPTY)) {
                        ChessConfig config = new ChessConfig(this);
                        config.chessMap.put(new Coordinates(i + 1, j - 1), KING);
                        config.chessMap.put(new Coordinates(i, j), EMPTY);
                        neighbourList.add(config);
                    }
                }
            }
        }
        return neighbourList;
    }

    /**
     * displays the config as a chess board
     * @return the toString of a string builder object
     */
    @Override
    public String toString() {
        StringBuilder config = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                config.append(chessMap.get(new Coordinates(i, j)));
                config.append(" ");
            }
            config.append("\n");
        }
        return config.toString();
    }

    /**
     * a getter method that returns the hashmap representing the chess board
     * @return the chess hashmap
     */
    public HashMap<Coordinates,String> getChessMap(){
        return this.chessMap;
    }

    /**
     * produces a unique integer value of a specific ChessConfig object
     * @return an integer
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * checks if two configs are equal to each other
     * @param other - the other config
     * @return true or false
     */
    @Override
    public boolean equals(Object other){
        if (other instanceof ChessConfig) {
            Configuration config = (ChessConfig) other;
            return config.hashCode() == this.hashCode();
        }
        return false;
    }
}
