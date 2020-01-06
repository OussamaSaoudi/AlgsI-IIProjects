import edu.princeton.cs.algs4.StdRandom;
public class Main {

    public static void main(String[] args) {
	// write your code here
    PercolationStats stats = new PercolationStats(200,100);
        System.out.println(stats.mean());
        System.out.println(stats.stddev());
        System.out.println(stats.confidenceLo() + " , "  + stats.confidenceHi());

    }
//    public static void printCurrent(Percolation test){
//        System.out.println("Finder: ");
//        test.printFinder();
//        System.out.println(" ");
//        System.out.println("Position");
//        test.printPositions();
//    }
}
