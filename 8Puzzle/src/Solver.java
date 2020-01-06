import edu.princeton.cs.algs4.MinPQ;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {
    private SearchNode goal;
    private boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();


        SearchNode initialAdjacent = new SearchNode(initial.twin(), null, 0);
        MinPQ<SearchNode> adjacentPriorityQueue = new MinPQ<>(1);
        adjacentPriorityQueue.insert(initialAdjacent);


        SearchNode initialNode = new SearchNode(initial, null, 0);
        MinPQ<SearchNode> priorityQueue = new MinPQ<>(1);
        priorityQueue.insert(initialNode);

        if (initial.isGoal()) {
            goal = initialNode;
            isSolvable = true;
            return;
        } else if (initialAdjacent.board.isGoal()) {
            isSolvable = false;
        }


        SearchNode currentAdjacent = initialAdjacent;
        SearchNode currentBoard = initialNode;

        while (!currentAdjacent.board.isGoal() && !currentBoard.board.isGoal()) {

            currentAdjacent = adjacentPriorityQueue.delMin();
            currentBoard = priorityQueue.delMin();

            for (Board adjacentNeighbor : currentAdjacent.board.neighbors()) {
                SearchNode toBeInserted = new SearchNode(adjacentNeighbor, currentAdjacent, currentAdjacent.previousMoves + 1);
                if (currentAdjacent.previousSearchNode == null || !toBeInserted.board.equals(currentAdjacent.previousSearchNode.board)) {
                    adjacentPriorityQueue.insert(toBeInserted);
                }
            }


            for (Board neighbor : currentBoard.board.neighbors()) {
                SearchNode toBeInserted = new SearchNode(neighbor, currentBoard, currentBoard.previousMoves + 1);
                if (currentBoard.previousSearchNode == null || !toBeInserted.board.equals(currentBoard.previousSearchNode.board)) {

                    priorityQueue.insert(toBeInserted);
                }
            }
        }
        if (currentAdjacent.board.isGoal()) {
            isSolvable = false;
            goal = initialNode;
        } else {
            goal = currentBoard;
            isSolvable = true;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (!isSolvable) return -1;
        return goal.previousMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable) return null;
        return new NodeQueue();
    }

    private class NodeQueue implements Iterable<Board> {
        private Board[] boardArray = new Board[goal.previousMoves + 1];
        private int last = goal.previousMoves;

        public NodeQueue() {
            SearchNode current = goal;
            boardArray[0] = goal.board;
            for (int i = 1; i <= last; i++) {
                boardArray[i] = current.previousSearchNode.board;
                current = current.previousSearchNode;
            }
        }


        @Override
        public Iterator<Board> iterator() {
            return new SearchNodeIterator();
        }

        private class SearchNodeIterator implements Iterator<Board> {
            ;

            @Override
            public boolean hasNext() {
                return last >= 0;
            }

            @Override
            public Board next() {
                if (last < 0) throw new NoSuchElementException();
                return boardArray[last--];


            }
        }

    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private int manhattan;
        //private int hamming;
        private final SearchNode previousSearchNode;
        private final int previousMoves;


        private SearchNode(Board board, SearchNode previousNode, int previousMoves) {
            this.board = board;
            this.previousMoves = previousMoves;
            this.previousSearchNode = previousNode;
            if (board != null) {
                //  this.hamming = board.hamming();
                this.manhattan = board.manhattan();
            }
        }

        public boolean equals(Object otherObject) {
            if (otherObject == null) return false;
            if (otherObject.getClass() != this.getClass()) return false;
            SearchNode otherNode = (SearchNode) otherObject;
            if (!otherNode.board.equals(board)) return false;
            if (!otherNode.previousSearchNode.equals(previousSearchNode)) return false;
            if (otherNode.previousMoves != previousMoves) return false;
            return true;
        }

        public int hashCode() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(SearchNode otherNode) {
            return manhattan + previousMoves - otherNode.previousMoves - otherNode.manhattan;
        }


    }

    // test client (see below)
    public static void main(String[] args) {
// Use for unit testing
    }


}