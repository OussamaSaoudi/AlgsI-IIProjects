/******************************************************************************
 *  Compilation:  javac KdTreeGenerator.java
 *  Execution:    java KdTreeGenerator n
 *  Dependencies: 
 *
 *  Creates n random points in the unit square and print to standard output.
 *
 *  % java KdTreeGenerator 5
 *  0.195080 0.938777
 *  0.351415 0.017802
 *  0.556719 0.841373
 *  0.183384 0.636701
 *  0.649952 0.237188
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;

public class KdTreeGenerator {

    public static void main(String[] args) {
        int n = 10000;
        PointSET tree = new PointSET();
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            System.out.println(x + "," + y);
            tree.insert(new Point2D(x,y));
        }
        System.out.println("/////////////////////");
        RectHV rect = new RectHV(0,0,1,1);
        int counter = 0;
        for(Point2D p : tree.range(rect)){
            counter++;
        }
        System.out.println(counter);
    }
}
