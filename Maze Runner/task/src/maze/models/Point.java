package maze.models;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int[] point) {
        x = point[0];
        y = point[1];
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (!(obj instanceof Point)) { return false; }
        Point p = (Point) obj;
        return x == p.x && y == p.y;
    }
}
