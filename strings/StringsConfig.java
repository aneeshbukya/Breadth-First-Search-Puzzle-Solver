package puzzles.strings;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;

public class StringsConfig implements Configuration {
    private String start;
    static String end;

    public StringsConfig(String start, String end){
        this.start = start;
        this.end = end;
    }
    @Override
    public boolean isSolution() {
        return this.start.equals(end);
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        for (int j = 0; j < end.length(); j++) {
            if (!Character.isLetter(end.charAt(j))) {
                return new ArrayList<>();
            }
        }
        Collection<Configuration> configs = new ArrayList<>();
        for (int i = 0; i < this.start.length(); i++) {
            StringBuilder left = new StringBuilder(this.start);
            StringBuilder right = new StringBuilder(this.start);
            if (this.start.charAt(i) == 'A') {
                left.setCharAt(i, 'B');
                right.setCharAt(i, 'Z');
                configs.add(new StringsConfig(left.toString(), end));
                configs.add(new StringsConfig(right.toString(), end));
            } else if (this.start.charAt(i) == 'Z') {
                left.setCharAt(i, 'A');
                right.setCharAt(i, 'Y');
                configs.add(new StringsConfig(left.toString(), end));
                configs.add(new StringsConfig(right.toString(), end));
            } else {
                left.setCharAt(i, (char)(this.start.charAt(i) + 1));
                right.setCharAt(i, (char)(this.start.charAt(i) - 1));
                configs.add(new StringsConfig(left.toString(), end));
                configs.add(new StringsConfig(right.toString(), end));
            }
        }
        return configs;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StringsConfig) {
            StringsConfig o = (StringsConfig) other;
            return o.start.equals(this.start);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.start.hashCode() + end.hashCode();
    }

    @Override
    public String toString() {
        return this.start;
    }
}
