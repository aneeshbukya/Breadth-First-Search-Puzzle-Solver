package puzzles.common.solver;

import java.util.*;
/**
 * The solver class which employs the BFS (breadth first search) algorithm in order to find the least number of moves it
 * takes to reach the solution.
 * @author Aneesh Bukya
 * @author Alaina Mupparthi
 */
public class Solver {
    private int totalConfigs = 0;
    private int uniqueConfigs = 0;

    /**
     * a method that uses BFS to get the shortest path from start config to end config
     * @param startConfig - start config
     * @return a list with the shortest path
     */
    public Collection<Configuration> solve(Configuration startConfig){

        List<Configuration> queue = new LinkedList<>();
        Map<Configuration, Configuration> predecessors = new HashMap<>();

        queue.add(startConfig);

        predecessors.put(startConfig, startConfig);

        while (!queue.isEmpty()){
            Configuration current = queue.remove(0);
            // doesn't know ending configuration
            if(current.isSolution()){
                this.totalConfigs++;
                this.uniqueConfigs++;
                break;
            }
            for(Configuration nbr : current.getNeighbors()) {
                totalConfigs++;
                if (!predecessors.containsKey(nbr)) {
                    predecessors.put(nbr, current);
                    queue.add(nbr);
                    this.uniqueConfigs++;
                }
            }
        }
        // prints in console
        Collection<Configuration> path = constructPath(predecessors, startConfig);
        if (path.isEmpty()){
            this.totalConfigs = 1 ;
            this.uniqueConfigs = 1;
        }
        return path;
    }

    public int getTotalConfigs(){
        return this.totalConfigs;
    }
    public int getUniqueConfigs(){
        return this.uniqueConfigs;
    }

    /**
     * Method to return a path from the starting to finishing config.
     *
     * @param predecessors Map used to reconstruct the path
     * @param start starting config
     * @return a list containing the sequence of config comprising the path.
     * An empty list if no path exists.
     */
    public List<Configuration> constructPath(Map<Configuration,Configuration> predecessors, Configuration start){
        List<Configuration> path = new LinkedList<>();
        Configuration current = null;
        // set solution to current
        for (Map.Entry<Configuration,Configuration> entry : predecessors.entrySet()) {
            if (entry.getKey().isSolution()) {
                current = entry.getKey();
            }
        }
        if (current == null) {
            return path;
        }
        // reverse direction from solution to start (but path is from start -> solution)
        while (!current.equals(start)) {
            path.add( 0, current );
            current = predecessors.get(current);
        }
        // add start to the beginning
        path.add(0, start);

        return path;
    }
}