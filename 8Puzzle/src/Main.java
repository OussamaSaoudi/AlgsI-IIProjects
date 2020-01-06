import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(6 % 3);
        File file = new File("src/Test Files/puzzle3x3-31.txt");
        Scanner reader = new Scanner(file);
        int n = reader.nextInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = reader.nextInt();
        Board initial = new Board(tiles);
        System.out.println("Initial: " + initial);

        System.out.println(initial.hamming());



        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}
