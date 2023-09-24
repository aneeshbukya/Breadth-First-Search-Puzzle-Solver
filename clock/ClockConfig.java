package puzzles.clock;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;

public class ClockConfig implements Configuration {
    static int hours;
    private int start;
    static int end;

    public ClockConfig(int hours, int start, int end){
        this.hours = hours;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean isSolution() {
        return this.start == end;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> configs = new ArrayList<>();
        if (this.start - 1 == 0) {
            configs.add(new ClockConfig(hours, hours, end));
        } else {
            configs.add(new ClockConfig(hours, start - 1, end));
        }
        if (this.start + 1 > hours) {
            configs.add(new ClockConfig(hours, 1, end));
        } else {
            configs.add(new ClockConfig(hours, start + 1, end));
        }
        return configs;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ClockConfig) {
            ClockConfig o = (ClockConfig) other;
            return o.start == this.start;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.start;
    }

    @Override
    public String toString() {
        return String.valueOf(start);
    }
}
