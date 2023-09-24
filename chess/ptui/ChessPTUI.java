package puzzles.chess.ptui;

import puzzles.common.Observer;
import puzzles.chess.model.ChessModel;

import java.io.IOException;
import java.util.Scanner;

/**
 * A Plain-Text user interface for the Chess Game
 * @author Aneesh Bukya
 */
public class ChessPTUI implements Observer<ChessModel, String> {
    /** an instance of the ChessModel class which contains the info and rules of the game */
    private ChessModel model;

    /**
     * initializes all the variables of the class
     * @param filename - name of the file
     * @throws IOException - if file not found
     */
    public void init(String filename) throws IOException {
        this.model = new ChessModel(filename);
        this.model.addObserver(this);
        update(model,"Loaded: " + filename.substring(filename.indexOf("chess-")));
        displayHelp();
    }

    /**
     * updates the view once it receives from model
     * @param model the object that wishes to inform this object
     *                about something that has happened.
     * @param data optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(ChessModel model, String data) {
        // for demonstration purposes
        System.out.println(data);
        System.out.println(model);
    }

    /**
     * displays the instructions to the user
     */
    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    /**
     *  reads through the file and carries out commands by calling methods in ChessModel
     */
    public void run() {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            String line = in.nextLine();
            String[] words = line.split("\\s+");
            if (words.length > 0) {
                if (words[0].startsWith("q")) {
                    this.model.quit();
                } else if (words[0].startsWith("l")) {
                    this.model.load(words[1]);
                } else if (words[0].startsWith("s") && words.length == 3 && Character.isDigit(words[1].charAt(0)) && Character.isDigit(words[2].charAt(0))) {
                    if(words[1].length() == 0 || words[2].length() == 0 ||
                            this.model.getCell((Integer.parseInt(words[1])),(Integer.parseInt(words[2]))).equals("Invalid")) {
                        update(this.model,"Invalid Coordinates");
                    }else{
                        this.model.select(Integer.parseInt(words[1]),Integer.parseInt(words[2]));
                    }
                } else if (words[0].startsWith("h")) {
                    this.model.hint();
                } else if (words[0].startsWith("r")) {
                    this.model.reset();
                }
                else {
                    update(this.model, "Invalid command");
                    displayHelp();
                }
            } else {
                displayHelp();
            }
        }
    }

    /**
     * runs the PTUI by reading the command line
     * @param args - command line
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ChessPTUI filename");
        } else {
            try {
                ChessPTUI ptui = new ChessPTUI();
                ptui.init(args[0]);
                ptui.run();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}

