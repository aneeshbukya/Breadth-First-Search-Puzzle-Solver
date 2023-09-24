package puzzles.strings;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Collection;

public class Strings {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Strings start finish"));
        } else {
            System.out.println("Start: " + args[0] + ", End: " + args[1]);

            StringsConfig start = new StringsConfig(args[0], args[1]);

            Solver solver = new Solver();
            Collection<Configuration> solutions =  solver.solve(start);
            // solve will print the configs
            int i = 0;
            if (solutions.size() != 0) {
                for(Configuration config : solutions){
                    System.out.println("Step " + i + ": " + config.toString());
                    i++;
                }
            } else {
                System.out.println("No solution");
            }
        }
    }
}
