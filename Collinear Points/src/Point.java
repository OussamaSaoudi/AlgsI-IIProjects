import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;
    private static final double DOUBLE_THRESHOLD = 0.00000001;

    // constructs the point (x, y)
    public Point(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that == null) throw new NullPointerException();
        if (this.y != that.y) {
            if (this.y - that.y > 0) {
                return 1;
            } else if (this.y - that.y < 0) {
                return -1;
            } else return 0;
        } else {
            if (this.x - that.x > 0) return 1;
            else if (this.x - that.x < 0) return -1;
            else return 0;
        }
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException();
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        else if (this.x == that.x) return Double.POSITIVE_INFINITY;
        else if (this.y == that.y) return 0.0;
        else return (1.0 * that.y - this.y) / (1.0 * that.x - this.x);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new SortBySlope();
    }

    private class SortBySlope implements Comparator<Point> {
        @Override
        public int compare(Point first, Point second) {
            if (first == null || second == null) throw new NullPointerException();
            double oneCompared = slopeTo(first);
            double twoCompared = slopeTo(second);
            if (Math.abs(oneCompared - twoCompared) < DOUBLE_THRESHOLD || oneCompared == twoCompared) {
                return 0;
            } else if (oneCompared > twoCompared) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    // Unit testing:
    public static void main(String[] args) {
        //unit testing
    }
}