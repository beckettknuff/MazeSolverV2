/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Should be from start to end cells
        // Remember to reverse the stack in order
        Stack<MazeCell> stack = new Stack<>();
        // The actual path in the maze
        ArrayList<MazeCell> solution = new ArrayList<>();
        MazeCell current = this.maze.getEndCell();
        while (current != null) {
            // push each cell on the stack
            stack.push(current);
            // once on top move to the parent cell
            current = current.getParent();
        }
        // Now pop each cell I added to the stack to reverse the order (add to the winning path)
        while (!stack.isEmpty()) {
            solution.add(stack.pop());
        }
        return solution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Stack<MazeCell> stack = new Stack<>();
        ArrayList<MazeCell> solution = new ArrayList<>();
        stack.push(this.maze.getStartCell());
        // while the stack has some cells in it, identify if it has been explored
        while (!stack.isEmpty()) {
            MazeCell current = stack.pop();
            if (!current.isExplored()) {
                current.setExplored(true);
                if (current == this.maze.getEndCell()) {
                    return this.getSolution();
                }

                // never eat soggy waffles
                int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                // for each direction find the value to see if the cell or direction is a valid path/option
                for (int[] c : directions) {
                    int newRow = current.getRow() + c[0];
                    int newCol = current.getCol() + c[0];
                    if (this.maze.isValidCell(newRow, newCol)) {
                        MazeCell next = this.maze.getCell(newRow, newCol);
                        // set the new parent cell to the now MazeCell
                        next.setParent(current);
                        // if so, push new cell onto stack
                        stack.push(next);
                    }
                }
            }
        }
        return solution; // if no path
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Queue<MazeCell> queue = new LinkedList<>();
        queue.add(this.maze.getStartCell());

        while (!queue.isEmpty()) {
            MazeCell current = queue.poll();
            if (!current.isExplored()) {
                current.setExplored(true);
                if (current == this.maze.getEndCell()) {
                    return this.getSolution();
                }

                // similar to solving DFS just with queue so add each new cell as presented (first in first out)
                int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                for (int[] c : directions) {
                    int newRow = current.getRow() + c[0];
                    int newCol = current.getCol() + c[0];
                    if (this.maze.isValidCell(newRow, newCol)) {
                        MazeCell next = this.maze.getCell(newRow, newCol);
                        next.setParent(current);
                        queue.add(next);
                    }
                }
            }
        }
        return new ArrayList<>(); // if no path works
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
