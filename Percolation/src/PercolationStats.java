import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final static double CONFIDENCE_95 = 1.96;
    private final int[] percolationProb;
    private final int size;
    private double mean;
    private double stdDev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        // initialize mean;
        mean = -1;
        stdDev = -1;

        size = n;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        percolationProb = new int[trials];
        for (int i = 0; i < trials; i++) {
            Percolation sample = new Percolation(n);
            while (!sample.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                while (sample.isOpen(row + 1, col + 1)) {
                    row = StdRandom.uniform(n);
                    col = StdRandom.uniform(n);
                }
                sample.open(row + 1, col + 1);

            }
            percolationProb[i] = sample.numberOfOpenSites();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (mean +1 < 0.0002) {
            mean = StdStats.mean(percolationProb) / (size * size);
        }
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (percolationProb.length > 0) {
            if (stdDev + 1 < 0.0002) {
                stdDev = StdStats.stddev(percolationProb) / (size * size);
            }
            return stdDev;
        }
        return Double.NaN;

    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (CONFIDENCE_95 * stdDev) / Math.sqrt(percolationProb.length);

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (CONFIDENCE_95 * stdDev) / Math.sqrt(percolationProb.length);
    }


    // test client (see below)
    public static void main(String[] args) {
// used for testing
    }
}