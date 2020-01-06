import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;

public class KdTree {
    private static final boolean VERT = true;
    private static final boolean HOR = false;

    private Node root;

    private class Node {
        private Node left, right;
        private Point2D point;
        private boolean orientation;
        private int size;
        private RectHV rect;

        public Node(Point2D p, int size, boolean orientation, RectHV rect) {
            this.size = size;
            this.point = p;
            this.point = p;
            this.rect = rect;
            setPoint(p, orientation);
        }

        public String toString() {
            if (orientation) {
                return point.toString() + " , Orientation: " + "Vertical";
            } else {
                return point.toString() + " , Orientation: " + "Horizontal";

            }
        }

        public void setPoint(Point2D p, boolean orientation) {
            this.point = p;
            this.orientation = orientation;
        }
    }


    // Insert a new point to the KdTree
    public void insert(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        root = insert(root, point, VERT, 0, 1, 0, 1);
    }

    private Node insert(Node node, Point2D point, boolean orientation, double minX, double maxX, double minY, double maxY) {
        if (node == null) {

            return new Node(point, 1, orientation, new RectHV(minX, minY, maxX, maxY));
        }
        int cmp;

        if (orientation) {

            cmp = compareX(point, node.point);


        } else {
            cmp = compareY(point, node.point);

        }
        if (cmp < 0) {
            if (orientation) {
                node.left = insert(node.left, point, !node.orientation, minX, node.point.x(), minY, maxY);
            } else {
                node.left = insert(node.left, point, !node.orientation, minX, maxX, minY, node.point.y());
            }
        } else if (cmp > 0 || orientation && compareY(point, node.point) != 0 || !orientation && compareX(point, node.point) != 0) {
            if (orientation) {
                node.right = insert(node.right, point, !node.orientation, node.point.x(), maxX, minY, maxY);
            } else {
                node.right = insert(node.right, point, !node.orientation, minX, maxX, node.point.y(), maxY);
            }
        } else {

            node.setPoint(point, orientation);
        }
        node.size = 1 + size(node.right) + size(node.left);
        return node;

    }

    // checks if kdtree is empty
    public boolean isEmpty() {
        return root == null;
    }

    // draws all the points and lines
    public void draw() {
        draw(root, 1, 0, 1, 0);
    }

    private void draw(Node node, double maxX, double minX, double maxY, double minY) {
        if (node == null) return;
        if (node.orientation) {
            double xCoord = node.point.x();

            StdDraw.setPenRadius(0.01);
            // draws vertical line
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(xCoord, minY, xCoord, maxY);

            // draws points left and right of the line
            draw(node.left, xCoord, minX, maxY, minY);
            draw(node.right, maxX, xCoord, maxY, minY);

            // draws point
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.BLACK);
            node.point.draw();

        } else {

            double yCoord = node.point.y();

            // draws horizontal line
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(minX, yCoord, maxX, yCoord);

            // draws lines above and below line
            draw(node.left, maxX, minX, yCoord, minY);
            draw(node.right, maxX, minX, maxY, yCoord);

            // draws point
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.BLACK);
            node.point.draw();

        }
    }

    // searches for points inside the given rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        PointStack stack = new PointStack();
        range(root, rect, stack);
        return stack;
    }

    private void range(Node node, RectHV rect, PointStack stack) {
        if (node == null) return;

        if (node.point.x() <= rect.xmax() && node.point.x() >= rect.xmin() && node.point.y() <= rect.ymax() && node.point.y() >= rect.ymin()) {
            stack.push(node.point);
        }
        if (node.right != null && rect.intersects(node.right.rect)) {
            range(node.right, rect, stack);
        }
        if (node.left != null && rect.intersects(node.left.rect)) {
            range(node.left, rect, stack);
        }
        return;
    }

    // Stack to store points in given range
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

    // Finds the closest point in the KdTree to the given point
    public Point2D nearest(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        if (root == null) return null;
        Point2D nearest = null;
        return nearestPoint(root, point, nearest);
    }

    private Point2D nearestPoint(Node node, Point2D query, Point2D nearestPoint) {
        // point returned is the nearest point found
        StdDraw.setPenRadius(0.01);

        if (nearestPoint == null) nearestPoint = node.point;
        if (node == null) return nearestPoint;
        if (node.point.distanceSquaredTo(query) < nearestPoint.distanceSquaredTo(query)) {
            nearestPoint = node.point;
        }
//        StdDraw.setPenColor(StdDraw.MAGENTA);
//
//        node.rect.draw();
//        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
//        StdDraw.setPenRadius(0.03);
//        nearestPoint.draw();
//        StdDraw.setPenColor(StdDraw.GREEN);
//        StdDraw.setPenRadius(0.03);
//        query.draw();
//        TimeUnit.SECONDS.sleep(1);
//
//        // if current node is vertical, and the query is to the left, search in left part of tree.
//        // IF the query is to the right, search right part of the tree.
//        StdDraw.clear();
//        draw();
        double distanceToLine = -1;
        int cmp;
        if (node.orientation) {
            cmp = compareX(query, node.point);
        } else {
            cmp = compareY(query, node.point);
        }
        if (cmp < 0) {
            nearestPoint = nearestPoint(node.left, query, nearestPoint);

        } else {

            nearestPoint = nearestPoint(node.right, query, nearestPoint);
        }
        if (cmp >= 0 && node.left != null) {
            distanceToLine = node.left.rect.distanceSquaredTo(query);
        } else if (cmp < 0 && node.right != null) {
            distanceToLine = node.right.rect.distanceSquaredTo(query);

        }
        // if current node is horizontal, check bottom or top if it resides there


        // Checks if there may be a point closer to the query point that is on the other side of the dividing line
        // Does this by checking if the dividing line is closer to the query point than the current nearest point.

        if (distanceToLine != -1 && distanceToLine < nearestPoint.distanceSquaredTo(query)) {
            // checks other side of tree if distance to line is less than distance to nearest point
            if (cmp < 0) {

                nearestPoint = nearestPoint(node.right, query, nearestPoint);
            } else {

                nearestPoint = nearestPoint(node.left, query, nearestPoint);

            }
        }

        return nearestPoint;
    }


    // Checks the binary search tree for specified point
    public boolean contains(Point2D point) {
        if(point == null) throw new IllegalArgumentException();
        if (get(root, point, VERT) != null) return true;
        return false;
    }

    private Node get(Node node, Point2D point, boolean orientation) {
        if (node == null) return null;
        int cmp;
        if (orientation) {
            cmp = compareX(point, node.point);
        } else {
            cmp = compareY(point, node.point);
        }
        if (cmp < 0) {
            return get(node.left, point, !node.orientation);
        } else if (cmp > 0) {
            return get(node.right, point, !node.orientation);
        } else {

            if (orientation) {
                cmp = compareY(point, node.point);
            } else {
                cmp = compareX(point, node.point);
            }
            if (cmp != 0) {
                return get(node.right, point, !node.orientation);
            } else {
                return node;
            }
        }
    }


    public int size() {
        if (root == null) return 0;
        return root.size;
    }

    // finds the size of node's subtree
    private int size(Node input) {
        if (input == null) return 0;
        return input.size;
    }

    // Comparison of points depending on orientation
    private int compareX(Point2D first, Point2D second) {
        if (first.x() > second.x()) return 1;
        else if (first.x() < second.x()) return -1;
        //  breaks ties with y value
//        if (first.y() > second.y()) return 1;
//        else if (first.y() < second.y()) return -1;
        return 0;

    }

    private int compareY(Point2D first, Point2D second) {
        if (first.y() > second.y()) return 1;
        else if (first.y() < second.y()) return -1;
        // breaks ties with x value
//        if (first.x() > second.x()) return 1;
//        else if (first.x() < second.x()) return -1;


        return 0;
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.625, 1));
        tree.insert(new Point2D(0.75, 0.125));
        tree.insert(new Point2D(0.875, 0.5));
//        System.out.println(tree.root.point);

        tree.insert(new Point2D(0, 0.875));
//        System.out.println(tree.root.left.point);

        tree.insert(new Point2D(0, 0.75));
//        System.out.println(tree.root.left.point);
//        System.out.println(tree.root.left.left.point);

        tree.insert(new Point2D(0.625, 0.875));


        RectHV rect = new RectHV(0, 0, 1, 1);
        System.out.println("////////////////");
        System.out.println(tree.size());


    }
}
