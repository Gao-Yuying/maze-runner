package maze.models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Maze {
    private final int[][] maze;
    private Point entrance;
    private Point exit;

    public Maze(int rows, int cols) {
        maze = new int[rows][cols];
        IntStream.range(0, maze.length)
                .forEach(i -> IntStream.range(0, maze[0].length)
                        .forEach(j -> maze[i][j] = 1));
        makeMaze(rows, cols);
    }

    public Maze(List<String> data) {
        maze = new int[data.size()][data.get(0).length()];
        IntStream.range(0, maze.length)
                .forEach(row -> maze[row] =
                        Arrays.stream(data.get(row).split("\\s")).mapToInt(Integer::parseInt).toArray());
        entrance = new Point(IntStream.range(0, maze.length).filter(i -> maze[i][0] == 0).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find the entrance!")),
                0);
        exit = new Point(IntStream.range(0, maze.length).filter(i -> maze[i][maze[0].length - 1] == 0).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find the exit!")),
                maze[0].length - 1);
    }

    public void print() {
        IntStream.range(0, maze.length)
                .forEach(i -> {
                    IntStream.range(0, maze[0].length)
                            .forEach(j -> System.out.print(maze[i][j] == 0 ? "  " : "\u2588\u2588"));
                    System.out.println();
                });
    }

    public boolean save(String path) {
        File file = new File(path);
        try (FileWriter writer = new FileWriter(file)) {
            for (int[] row : maze) {
                for (int i : row) { writer.write(i + " "); }
                writer.write('\n');
            }
        } catch (IOException e) { return false; }
        return true;
    }

    public void printPath() {
        List<Node> path = traverse();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(path.contains(new Node(i, j)) ? "//" : maze[i][j] == 0 ? "  " : "\u2588\u2588");
            }
            System.out.println();
        }
    }

    private void makeMaze(int rows, int cols) {
        List<Edge> edges = makeMST(initializeWeights((rows - 2) / 2 + 1, (cols - 2) / 2 + 1));
        drawPathFromEdges(edges, rows, cols);
        setEntrance(rows);
        setExit(cols);
    }

    private void drawPathFromEdges(List<Edge> edges, int rows, int cols) {
        for (Edge edge : edges) {
            int[][] path = getPathFromEdge(edge);
            for (int[] p : path) {
                if (p[0] < rows - 1 && p[1] < cols - 1) { maze[p[0]][p[1]] = 0; }
            }
            // avoid unreachable areas
            int startX = path[0][0];
            int startY = path[0][1];
            if (startX == rows - 3) {
                if (edges.contains(new Edge(edge.end, new int[] {edge.end[0], edge.end[1] + 1}, edge.weight)) &&
                        startY < cols - 2) { maze[startX][startY + 1] = 0; }
                if (edges.contains(new Edge(new int[] {edge.end[0], edge.end[1] - 1}, edge.end, edge.weight)) &&
                        startY > 2) { maze[startX][startY - 1] = 0; }
            }
            if (startY == cols - 3) {
                if (edges.contains(new Edge(edge.end, new int[] {edge.end[0] + 1, edge.end[1]}, edge.weight)) &&
                        startX < rows - 2) { maze[startX + 1][startY] = 0; }
                if (edges.contains(new Edge(new int[] {edge.end[0] - 1, edge.end[1]}, edge.end, edge.weight)) &&
                        startX > 2) { maze[startX - 1][startY] = 0; }
            }
        }
    }

    private int[][] getPathFromEdge(Edge edge) {
        int x1 = edge.start[0] * 2 + 1;
        int y1 = edge.start[1] * 2 + 1;
        int x2 = edge.end[0] * 2 + 1;
        int y2 = edge.end[1] * 2 + 1;
        int x3 = (x2 > x1 ? x2 - 1 : x2);
        int y3 = (y2 > y1 ? y2 - 1 : y2);
        return new int[][] { {x1, y1}, {x2, y2}, {x3, y3} };
    }

    private void setEntrance(int rows) {
        Random random = new Random();
        int xEntrance = random.nextInt(rows - 2) + 1;
        maze[xEntrance][0] = 0;
        maze[xEntrance][1] = 0;
        entrance = new Point(xEntrance, 0);
    }


    private void setExit(int cols) {
        Random random = new Random();
        int xExit = random.nextInt(cols - 2) + 1;
        maze[xExit][cols - 1] = 0;
        maze[xExit][cols - 2] = 0;
        exit = new Point(xExit, cols - 1 );
    }

    private List<Node> traverse() {
        Deque<Node> stack = new ArrayDeque<>();
        List<Node> visited = new ArrayList<>();
        List<Node> path = new ArrayList<>();
        Node root = new Node(entrance.x, entrance.y, null, null);
        Node next = null;
        stack.offerLast(root);
        visited.add(new Node(root.x, root.y));
        while (stack.size() > 0) {
            next = stack.pollLast();
            visited.add(new Node(next.x, next.y));
            if (next.x == exit.x && next.y == exit.y) {
                break;
            }
            next.children = getChildren(next, visited);
            for (Node child : next.children) {
                stack.offerLast(child);
                visited.add(new Node(child.x, child.y));
            }
        }
        while (next != null) {
            path.add(next);
            next = next.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private List<Node> getChildren(Node p, List<Node> visited) {
        List<Node> children = new ArrayList<>();
        Node down = new Node(p.x + 1, p.y, p, null);
        Node left = new Node(p.x, p.y - 1, p, null);
        Node up = new Node(p.x - 1, p.y, p, null);
        Node right = new Node(p.x, p.y + 1, p, null);
        // down
        if (p.x < maze.length - 1 && maze[p.x + 1][p.y] == 0 &&
                !(visited.contains(new Node(down.x, down.y)))) {
            children.add(down);
        }
        // left
        if (p.y > 0 && maze[p.x][p.y - 1] == 0 &&
                !(visited.contains(new Node(left.x, left.y)))) {
            children.add(left);
        }
        // up
        if (p.x > 0 && maze[p.x - 1][p.y] == 0 &&
                !(visited.contains(new Node(up.x, up.y)))) {
            children.add(up);
        }
        // right
        if (p.y < maze[0].length - 1 && maze[p.x][p.y + 1] == 0 &&
                !(visited.contains(new Node(right.x, right.y)))) {
            children.add(right);
        }
        return children;
    }


    private int[][][] initializeWeights(int rows, int cols) {
        int[][][] weights = new int[rows][cols][2];
        Random random = new Random();
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j][0] = random.nextInt(rows);  // right neighbor
                weights[i][j][1] = random.nextInt(cols);  // down neighbor
                if (j == weights[0].length - 1) { weights[i][j][0] = 999; }
                if (i == weights.length - 1) { weights[i][j][1] = 999; }
            }
        }
        return weights;
    }

    private List<Edge> makeMST(int[][][] weights) {
        Random random = new Random();
        List<Point> points = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        List<Edge> availableEdges = new ArrayList<>();
        Point nextPoint = new Point(random.nextInt(weights.length), random.nextInt(weights[0].length));
        points.add(nextPoint);

        while (points.size() < weights.length * weights[0].length) {
            availableEdges.addAll(getEdgesByPoint(points, nextPoint, availableEdges, edges, weights));
            Collections.sort(availableEdges);   // sort by weight
            while (points.contains(new Point(availableEdges.get(0).start)) &&
                    points.contains(new Point(availableEdges.get(0).end))) {
                availableEdges.remove(0);
            }
            Edge nextEdge = availableEdges.get(0);
            nextEdge.resetWeight();
            availableEdges.remove(0);
            edges.add(nextEdge);
            if (!points.contains(new Point(nextEdge.start))) {
                Point point = new Point(nextEdge.start);
                points.add(point);
                nextPoint = point;
            } else if (!points.contains(new Point(nextEdge.end))) {
                Point point = new Point(nextEdge.end);
                points.add(point);
                nextPoint = point;
            }
        }
        return edges;
    }

    private List<Edge> getEdgesByPoint(List<Point> points, Point point, List<Edge> availableEdges,
                                       List<Edge> edges, int[][][] weights) {
        List<Edge> newEdges = new ArrayList<>();
        int x = point.x;
        int y = point.y;
        Edge edge;
        // rule: x2 > x1; if equal then y2 > y1
        // right
        if (y < weights[0].length - 1) {
            edge = new Edge(new int[]{x, y}, new int[]{x, y + 1}, weights[x][y][0]);
            if (edgeIsAvailable(edge, availableEdges, edges, points)) { newEdges.add(edge); }
        }
        // down
        if (x < weights.length - 1) {
            edge = new Edge(new int[]{x, y}, new int[]{x + 1, y}, weights[x][y][1]);
            if (edgeIsAvailable(edge, availableEdges, edges, points)) { newEdges.add(edge); }
        }
        // left
        if (y > 0) {
            edge = new Edge(new int[]{x, y - 1}, new int[]{x, y}, weights[x][y - 1][0]);
            if (edgeIsAvailable(edge, availableEdges, edges, points)) { newEdges.add(edge); }
        }
        // up
        if (x > 0) {
            edge = new Edge(new int[]{x - 1, y}, new int[]{x, y}, weights[x - 1][y][1]);
            if (edgeIsAvailable(edge, availableEdges, edges, points)) { newEdges.add(edge); }
        }
        return newEdges;
    }

    private boolean edgeIsAvailable(Edge edge, List<Edge> availableEdges, List<Edge> edges, List<Point> points) {
        return !(points.contains(new Point(edge.start)) && (points.contains(new Point(edge.end)))) &&
                !availableEdges.contains(edge) && !edges.contains(edge);
    }
}