
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // write your code here
StdDraw.setPenRadius(0.005);
        // read the n points from a file
        File file = new File("input8000.txt");
        System.out.println(file.exists());
        Scanner reader = new Scanner(file);
        int n = reader.nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = reader.nextInt();
            int y = reader.nextInt();
            points[i] = new Point(x, y);
        }


        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
//        System.out.println("Brute: ");
//        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//
//        }
        FastCollinearPoints collinearTwo = new FastCollinearPoints(points);
        System.out.println("Fast: ");
        for(LineSegment segment : collinearTwo.segments()){
            System.out.println(segment);
            segment.draw();


        }

        //System.out.println("Segments: " + collinear.numberOfSegments());
        StdDraw.show();
        System.out.println("Segments: " + collinearTwo.numberOfSegments());
//        for(LineSegment segment : findDifferences(collinearTwo.segments(),collinear.segments())){
//            System.out.println(segment);
//        }

    }

    public static boolean checkArray(Point[] array, Point point, int hi) {
        for (int i = 0; i < hi; i++) {
            if (array[i].compareTo(point) == 0) return false;
        }
        return true;
    }
    public static LineSegment[] findDifferences(LineSegment[] fast, LineSegment[] brute){
        LineSegment[] array = new LineSegment[Math.max(brute.length,fast.length)];
        int arrayCounter = 0;
        for(int i = 0; i < brute.length; i++){
            if(!contains(fast,brute[i])){
                array[arrayCounter++] = brute[i];
            }
        }
        return array;
    }
    public static boolean contains(LineSegment[] input, LineSegment looking){
        for(int i = 0; i < input.length; i++){
            if(input[i].toString().equals(looking.toString())){
                return true;
            }
        }
        return false;
    }
}
