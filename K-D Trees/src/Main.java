import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        // write your code here
        File in = new File("circle10.txt");
        Scanner reader = new Scanner(in);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();



        while (reader.hasNext()) {
            double x = reader.nextDouble();
            double y = reader.nextDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        RectHV testRect = new RectHV(0.4990234375,0.0546875 ,0.6591796875, 0.595703125);
        for (Point2D p : kdtree.range(testRect)){
            System.out.println(p);
        }
//        kdtree.draw();
//
//        Point2D query = new Point2D(0.375, 0.625);
////        Point2D test = new Point2D(0.499, 0.208);
////        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
////         test.draw();
//        StdDraw.setPenColor(StdDraw.GREEN);
//
//        query.draw();
//        StdDraw.setPenColor(StdDraw.MAGENTA);
//
//        System.out.println(kdtree.nearest(query));

    }
}
