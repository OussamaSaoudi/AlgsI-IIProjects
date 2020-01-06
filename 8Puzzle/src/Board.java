import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    private final int[][] tiles;
    //    private final int[][] goal;
    private final int size;
    private int blankRow;
    private int blankCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        size = tiles.length;
        this.tiles = new int[size][size];
//        this.goal = new int[size][size];
//        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int tileVal = tiles[i][j];
                this.tiles[i][j] = tileVal;
                if (tileVal == 0) {
                    blankCol = j;
                    blankRow = i;
                }
//                this.goal[i][j] = ++counter;
            }
        }
//        this.goal[size - 1][size - 1] = 0;
    }

    // string representation of this board
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                outputString.append(tiles[i][j] + "\t");
            }
            outputString.append("\n");
        }
        return outputString.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int outOfPlace = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int tilesPosition = tiles[i][j];
                if (tilesPosition != ((i) * size) + j + 1 && tilesPosition != 0) outOfPlace++;
            }
        }
        return outOfPlace;

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int tileValue = tiles[i][j];

                if (tileValue == 0 || ((i) * size) + j + 1 == tileValue) continue;
                int goalRow = tileValue / (size);
                if (tileValue % size == 0) goalRow = tileValue / (size + 1);
                int goalCol = Math.abs((tileValue - goalRow * size - 1));

                manhattan += Math.abs(goalRow - i) + Math.abs(goalCol - j);
                //System.out.println("current manhattan: " + manhattan);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int testValue = ((i) * size) + j + 1;
                if (testValue == size * size || testValue == tiles[i][j]) continue;
                else {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board input = (Board) y;
        if (input.dimension() != this.size) return false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != input.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<>();

        Board copyBoard = new Board(tiles);

        if (blankCol != 0) {
            copyBoard.exch(blankRow, blankCol - 1);
            stack.push(copyBoard);
            copyBoard = new Board(tiles);
        }
        if (blankCol != size - 1) {
            copyBoard.exch(blankRow, blankCol + 1);
            stack.push(copyBoard);
            copyBoard = new Board(tiles);
        }
        if (blankRow != 0) {
            copyBoard.exch(blankRow - 1, blankCol);
            stack.push(copyBoard);
            copyBoard = new Board(tiles);
        }
        if (blankRow != size - 1) {
            copyBoard.exch(blankRow + 1, blankCol);
            stack.push(copyBoard);

        }
        return stack;
    }

    private class Stack<Board> implements Iterable<Board> {
        private Node first;
        private int size = 0;

        private Stack() {
            first = new Node();
            first.next = null;

        }

        @Override
        public Iterator<Board> iterator() {
            return new StackIterator();
        }

        private void push(Board input) {
            Node in = new Node();
            in.board = input;
            in.next = first;
            first = in;
            size++;
        }

        private Board pop() {
            if (size != 0) {
                Board output = first.board;
                first = first.next;
                size--;
                return output;
            }
            return null;
        }

        private class Node {
            Node next;
            Board board;
        }

        private class StackIterator implements Iterator<Board> {

            @Override
            public boolean hasNext() {
                return size != 0;
            }

            @Override
            public Board next() {
                if (size == 0) throw new NoSuchElementException();
                return pop();
            }
        }

    }

    // exchanges two adjacent positions on the board, one of them a blank position
    private void exch(int toRow, int toCol) {
        if (Math.abs(blankCol - toCol) > 1 || Math.abs(blankRow - toRow) > 1) throw new IllegalArgumentException();
        int tempValue = tiles[toRow][toCol];
        tiles[toRow][toCol] = 0;
        tiles[blankRow][blankCol] = tempValue;
        blankCol = toCol;
        blankRow = toRow;

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] tilesCopy = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tilesCopy[i][j] = tiles[i][j];
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (tilesCopy[i][j] != 0 && tilesCopy[i][j - 1] != 0) {
                    int temp = tilesCopy[i][j];
                    tilesCopy[i][j] = tilesCopy[i][j - 1];
                    tilesCopy[i][j - 1] = temp;
                    return new Board(tilesCopy);
                }
            }
        }
        return new Board(tilesCopy);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] array = new int[3][3];
        // board test setup:
        array[0][0] = 5;
        array[0][1] = 4;
        array[0][2] = 6;
        array[1][0] = 3;
        array[1][1] = 0;
        array[1][2] = 7;
        array[2][0] = 1;
        array[2][1] = 2;
        array[2][2] = 8;

        // Board testing;

        Board boardTest = new Board(array);
        System.out.println(boardTest);
        System.out.println("manhattan : " + boardTest.manhattan());


    }

}