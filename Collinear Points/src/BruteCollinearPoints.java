
public class BruteCollinearPoints {
    private int segmentsCounter;
    private LineSegment[] segments;
    private static final double DOUBLE_THRESHOLD = 0.00000001;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] input) {
        if (input == null) throw new IllegalArgumentException();
        Point[] points = new Point[input.length];
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null) throw new IllegalArgumentException();
            points[i] = input[i];
        }


        segments = new LineSegment[points.length];
        segmentsCounter = 0;
        quickComparatorSort(points, 0, points.length - 1);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("Must have one of each point");
            }
        }
        for (int i = 0; i < points.length - 3; i++) {
            SecondLoop:
            for (int j = i + 1; j < points.length - 2; j++) {

                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) break;
                double slopeOne = points[i].slopeTo(points[j]);


                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[i].slopeTo(points[k]) == Double.NEGATIVE_INFINITY || points[j].slopeTo(points[k]) == Double.NEGATIVE_INFINITY)
                        break;
                    double slopeTwo = points[i].slopeTo(points[k]);


                    for (int r = points.length - 1; r >= k + 1; r--) {
                        if (points[i].slopeTo(points[r]) == Double.NEGATIVE_INFINITY || points[j].slopeTo(points[r]) == Double.NEGATIVE_INFINITY || points[k].slopeTo(points[r]) == Double.NEGATIVE_INFINITY) {
                            break;
                        }

                        double slopeThree = points[i].slopeTo(points[r]);
                        if ((Math.abs(slopeOne - slopeTwo) < DOUBLE_THRESHOLD && Math.abs(slopeOne - slopeThree) < DOUBLE_THRESHOLD) || (slopeOne == Double.POSITIVE_INFINITY && slopeTwo == Double.POSITIVE_INFINITY && slopeThree == Double.POSITIVE_INFINITY)) {
                            LineSegment inputSegment = new LineSegment(points[i], points[r]);
                            segments[segmentsCounter++] = inputSegment;
                            continue SecondLoop;
                        }
                    }
                }
            }
        }
        LineSegment[] adjustedSize = new LineSegment[segmentsCounter];
        for (int i = 0; i < segmentsCounter; i++) {
            adjustedSize[i] = segments[i];
        }

        segments = adjustedSize;

    }


    // the number of line segments
    public int numberOfSegments() {
        return segmentsCounter;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] outputSegments = new LineSegment[segmentsCounter];
        for (int i = 0; i < segmentsCounter; i++) {
            outputSegments[i] = segments[i];
        }
        return outputSegments;
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


    public static void main(String[] args) {
        System.out.println(Double.NEGATIVE_INFINITY < 0);
    }
}
