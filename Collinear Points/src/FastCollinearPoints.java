
import java.util.Comparator;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private Point[] auxiliary;
    private int numberOfSegments;
    private static final double DOUBLE_THRESHOLD = 0.00000001;


    public FastCollinearPoints(Point[] input) {
        if (input == null) throw new IllegalArgumentException();
        Point[] points = new Point[input.length];
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null) throw new IllegalArgumentException();
            points[i] = input[i];
        }


        numberOfSegments = 0;
        segments = new LineSegment[points.length + 1];
        //quickComparatorSort(points, 0, points.length - 1);
        //quickComparatorSort(points, 0, points.length - 1);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("Must have one of each point");
            }

        }
        int pointsCounter = 0;
        for (Point point : input) {
            pointsCounter++;
            mergeSortBU(points, point.slopeOrder());

            int first = 0;
            int pointCounter = 0;

            for (int i = 1; i < points.length; i++) {
                for (int j = 0; j < pointsCounter; j++) {
                    if (points[i] == input[j]) continue;
                }
                if (point.slopeTo(points[i]) == Double.NEGATIVE_INFINITY) continue;
                if (point.slopeTo(points[i]) != point.slopeTo(points[first])) {
                    pointCounter = 0;
                    first = i;
                }

                if (first != i && ((Math.abs(point.slopeTo(points[i]) - point.slopeTo(points[first])) < DOUBLE_THRESHOLD) || point.slopeTo(points[i]) == point.slopeTo(points[first]))) {
                    pointCounter++;
                }

                if (((i < points.length - 1 && point.slopeTo(points[i + 1]) != point.slopeTo(points[i])) || i == points.length - 1) && pointCounter >= 2 && ((Math.abs(point.slopeTo(points[i - 1]) - point.slopeTo(points[first])) < DOUBLE_THRESHOLD) || (point.slopeTo(points[i - 1]) == point.slopeTo(points[first])))) {
                    boolean segmentAdded = addPoint(points, first, i, point);
                    first = i;
                    if (segmentAdded) {
                        pointCounter = 0;
                    }
                }
//                else if (pointCounter >= 2 && i == points.length - 1) {
//                    segments[numberOfSegments++] = new LineSegment(points[first], points[i]);
//                    first = -1;
//                    pointCounter = 0;
//                    numberOfSegments++;
//                }
            }
        }
    }

    private boolean addPoint(Point[] points, int first, int i, Point point) {
        Point minPoint = point;
        Point maxPoint = point;
        for (int j = 0; j < i - first + 1; j++) {
            if (points[first + j].compareTo(minPoint) < 0) {
                minPoint = points[first + j];
            }
            if (points[first + j].compareTo(maxPoint) > 0) {
                maxPoint = points[first + j];
            }
        }
        if (point.compareTo(maxPoint) == 0) {
            LineSegment inputSegment = new LineSegment(minPoint, maxPoint);
            if (segments.length < numberOfSegments + 2) resizeArray();
            segments[numberOfSegments++] = inputSegment;
            return true;
//woo~
        }
        return false;
//        LineSegment inputSegment = new LineSegment(minPoint, maxPoint);
//        if (numberOfSegments == 0) {
//            segments[numberOfSegments++] = inputSegment;
//            return true;
//        }
//        for (int r = 0; r < numberOfSegments; r++) {
//            if (segments[r].toString().equals(inputSegment.toString())) {
//                return false;
//
//            }
//        }
//        segments[numberOfSegments++] = inputSegment;
//        return true;
    }

    private void resizeArray() {
        LineSegment[] newArray = new LineSegment[segments.length * 2];
        for (int i = 0; i < numberOfSegments; i++) {
            newArray[i] = segments[i];
        }
        segments = newArray;
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }
//
//    private LineSegment returnLongestSegment(Point[] points, int lo, int hi) throws IllegalArgumentException {
//        double longestLength = 0;
//        LineSegment largestSegment = null;
//        for (int i = lo; i <= hi; i++) {
//            for (int j = i; j < hi; j++) {
//                double length = length(points[i], points[j]);
//                if (length > longestLength) {
//                    longestLength = length;
//                    largestSegment = new LineSegment(points[i], points[j]);
//                }
//            }
//        }
//        return largestSegment;
//
//    }
//    private double length(Point a, Point b) {
//        return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
//
//    }


    private void mergeSortBU(Point[] points, Comparator<Point> comp) {
        int n = points.length;
        auxiliary = new Point[n];
        for (int sz = 1; sz < n; sz = sz + sz) {
            for (int lo = 0; lo < n - sz; lo += sz + sz) {
                merge(points, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, n - 1), comp);
            }
        }
    }

    private void merge(Point[] points, int lo, int mid, int hi, Comparator<Point> comp) {
        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            auxiliary[k] = points[k];
        }
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                points[k] = auxiliary[j++];
            } else if (j > hi) {
                points[k] = auxiliary[i++];
            } else if (comp.compare(auxiliary[j], auxiliary[i]) < 0) {
                points[k] = auxiliary[j++];
            } else {
                points[k] = auxiliary[i++];
            }
        }
    }


    private void quickComparatorSort(Point[] points, int lo, int hi) {
        if (hi <= lo) return;

        int partitionPoint = partition(points, lo, hi);
        quickComparatorSort(points, lo, partitionPoint);
        quickComparatorSort(points, partitionPoint + 1, hi);
    }


    private int partition(Point[] points, int lo, int hi) {
        int i = lo;
        int j = hi + 1;

        Point compared = points[lo];
        while (true) {
            while (compared.compareTo(points[--j]) <= 0) {
                if (lo == j) {
                    break;
                }
            }
            while (points[++i].compareTo(compared) < 0) {
                if (i == hi) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exch(points, i, j);
        }
        exch(points, lo, j);
        return j;
    }

    private void exch(Point[] p, int switchOne, int switchTwo) {
        Point temp = p[switchOne];
        p[switchOne] = p[switchTwo];
        p[switchTwo] = temp;

    }

    public LineSegment[] segments() {
        LineSegment[] outputSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            outputSegments[i] = segments[i];
        }
        return outputSegments;
    }

    public static void main(String[] args) {

        // read the n points from a file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//
//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
    }
}
