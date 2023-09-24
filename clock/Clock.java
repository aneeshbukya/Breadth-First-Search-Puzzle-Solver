package puzzles.clock;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Collection;

public class Clock {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Clock hours stop end"));
        } else {
            System.out.println("Hours: " + args[0] + ", Start: " + args[1] + ", End: " + args[2]);

            ClockConfig start = new ClockConfig(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]));

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
