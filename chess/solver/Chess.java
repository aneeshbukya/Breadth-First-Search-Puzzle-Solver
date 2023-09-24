package puzzles.chess.solver;

import puzzles.chess.model.ChessConfig;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.Collection;

/**
 * The Clock solver main program
 * @author Aneesh Bukya
 */
public class Chess {
    /**
     * the main method which reads the command arguments and carries all the functions
     * @param args - command line
     * @throws IOException - if file not found
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Chess filename");
        } else {
            System.out.println("File: "+args[0]);
            ChessConfig config = new ChessConfig(args[0]);
            System.out.print(config);
            Solver solver = new Solver();
            Collection<Configuration> solutions =  solver.solve(config);
            int numSteps = 0;
            System.out.println("Total configs: " + solver.getTotalConfigs());
            System.out.println("Unique configs: " + solver.getUniqueConfigs());
            if (solutions.size() != 0 ) {
                for (Configuration configuration : solutions) {
                    System.out.println("Step " + numSteps + ":");
                    System.out.println(configuration);
                    numSteps++;
                }
            }
            else{
                System.out.println("No solution");
            }
        }
    }
}
