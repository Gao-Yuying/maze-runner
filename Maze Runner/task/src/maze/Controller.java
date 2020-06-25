package maze;

import maze.models.Maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {

    private final Scanner scanner = new Scanner(System.in);
    private Maze maze = null;

    public void execute() {
        while (true) {
            displayMenu(maze != null);
            switch (cmd()) {
                case "0":
                    exit();
                    return;
                case "1":
                    genMaze();
                    break;
                case "2":
                    load();
                    break;
                case "3":
                    save();
                    break;
                case "4":
                    displayMaze();
                    break;
                case "5":
                    displayPath();
                    break;
                default:
                    alertInvalidOption();
            }
            System.out.println();
        }
    }

    private void displayMenu(Boolean hasMaze) {
        System.out.println("=== Menu ===\n" +
                "1. Generate a new maze\n" +
                "2. Load a maze\n" +
                (hasMaze ? "3. Save the maze\n" +
                        "4. Display the maze\n" +
                        "5. Find the escape\n" :
                        "") +
                "0. Exit");
    }

    private void exit() {
        System.out.println("Bye!");
    }

    private void genMaze() {
        System.out.println("Enter the size of a new maze");
        setMaze(Integer.parseInt(cmd()));
        displayMaze();
    }

    private void load() {
        System.out.println("Enter file name(.txt):");
        String loadPath = cmd();
        if (!".txt".equalsIgnoreCase(loadPath.substring(loadPath.length() - 4))) {
            System.out.println("Cannot load the maze. It has an invalid format");
            return;
        }
        try (Scanner reader = new Scanner(new File(loadPath))) {
            List<String> data = new ArrayList<>();
            while(reader.hasNext()) { data.add(reader.nextLine()); }
            setMaze(data);
            System.out.println("Success!");
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s does not exist.\n", loadPath);
        }
    }

    private void save() {
        if (null == maze) {
            alertInvalidOption();
            return;
        }
        System.out.println("Enter file name(.txt):");
        System.out.println(maze.save(cmd()) ? "Success!" : "Failed to save maze.");
    }

    private void displayMaze() {
        if (null == maze) {
            alertInvalidOption();
            return;
        }
        maze.print();
    }

    private void displayPath() {
        if (null == maze) {
            alertInvalidOption();
            return;
        }
        maze.printPath();
    }

    private void setMaze(int n) {
        maze = new Maze(n, n);
    }

    private void setMaze(List<String> data) {
        maze = new Maze(data);
    }

    private void alertInvalidOption() { System.out.println("Incorrect option. Please try again."); }

    private String cmd() { return scanner.nextLine().trim(); }
}
