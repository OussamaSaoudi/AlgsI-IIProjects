import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private final Set<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();

    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!pointSet.contains(p)) pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        PointStack stack = new PointStack();
        for (Point2D p : pointSet) {
            if (p.x() <= rect.xmax() && p.x() >= rect.xmin() && p.y() <= rect.ymax() && p.y() >= rect.ymin()) {
                stack.push(p);
            }
        }
        return stack;
    }

    private class PointStack implements Iterable<Point2D> {
        Node first = null;

        private class Node {
            Node next;
            Point2D point;

            public Node(Point2D point) {
                this.point = point;
            }
        }

        public void push(Point2D point) {
            Node inputNode = new Node(point);
            inputNode.next = first;
            first = inputNode;
        }

        public Point2D pop() {
            Point2D output = first.point;
            first = first.next;
            return output;
        }

        @Override
        public Iterator<Point2D> iterator() {
            return new StackIterator();
        }

        private class StackIterator implements Iterator<Point2D> {
            @Override
            public boolean hasNext() {

                return first != null;
            }

            @Override
            public Point2D next() {
                return pop();
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        if (p == null) throw new IllegalArgumentException();
        Point2D nearest = null;
        double nearestDistance = Double.NaN;
        for (Point2D point : pointSet) {
            if (nearest == null) {
                nearest = point;
                nearestDistance = nearest.distanceSquaredTo(p);
            }
            if (point.distanceSquaredTo(p) < nearestDistance) {
                nearest = point;
                nearestDistance = nearest.distanceSquaredTo(p);
            }

        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET set = new PointSET();
        System.out.println(set.size());
        set.insert(new Point2D(1, 0));
        System.out.println(set.size());
        set.insert(new Point2D(0, 0));
        System.out.println(set.nearest(new Point2D(1, 1)));
        System.out.println(set.nearest(new Point2D(1, 1)));
        set.insert(new Point2D(1, 1));
        System.out.println(set.isEmpty());
        for (Point2D p : set.range(new RectHV(0, 0, 1, 0))) {
            System.out.println(p);
        }


    }

}