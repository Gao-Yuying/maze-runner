package maze.models;

import java.util.Arrays;

public class Edge implements Comparable<Edge> {
    int[] start;
    int[] end;
    int weight;

    public Edge(int[] start, int[] end, int weight) {
        this.start = Arrays.copyOf(start, start.length);
        this.end = Arrays.copyOf(end, end.length);
        this.weight = weight;
    }

    public void resetWeight() { weight = 1; }

    public Integer getWeight() { return weight; }

    @Override
    public int compareTo(Edge e) { return this.getWeight().compareTo(e.getWeight()); }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (!(obj instanceof Edge)) { return false; }
        Edge e = (Edge) obj;
        return start[0] == e.start[0] && start[1] == e.start[1] &&
                end[0] == e.end[0] && end[1] == e.end[1];
    }
}