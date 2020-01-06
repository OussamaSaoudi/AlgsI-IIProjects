import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF finder;
    private int openSites;
    private final int size;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();

        size = n;
        openSites = 0;
        grid = new int[n][n];
        finder = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n; i++) {
            // initiallizes top virtual node
            finder.union(n * n, finder.find(i));
            // initializes bottom virtual node
            finder.union(n * n + 1, finder.find(n * n - i - 1));
            // initializes a completely blocked grid.
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row - 1 < 0 || col - 1 < 0 || row - 1 > size || col - 1 > size) throw new IllegalArgumentException();
        if (isOpen(row, col)) return;
        openSites++;

        int[] bothSides = new int[2];
        bothSides[0] = -1;
        bothSides[1] = 1;
        for (int positioner : bothSides) {
            if (col + positioner > 0 && col + positioner <= size && isOpen(row, col + positioner)) {
                finder.union(((row - 1) * size + col + positioner - 1), (row - 1) * size + col - 1);
            }
            if (row + positioner > 0 && row + positioner <= size && isOpen(row + positioner, col)) {
                finder.union((row - 1) * size + positioner * size + col - 1, (row - 1) * size + col - 1);
            }

        }
        grid[col - 1][row - 1] = 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row - 1 < 0 || col - 1 < 0 || row - 1 > size || col - 1 > size) throw new IllegalArgumentException();
        if (0 < col && col <= size && 0 < row && row <= size) {
            return grid[col - 1][row - 1] == 1;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row - 1 < 0 || col - 1 < 0 || row - 1 > size || col - 1 > size) throw new IllegalArgumentException();

        return finder.connected((row - 1) * size + col - 1, size * size) && (isOpen(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (size == 1) return false;
            return finder.connected(size * size, size *size + 1 );
    }
}
// test client (optional)}