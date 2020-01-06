import edu.princeton.cs.algs4.StdDraw;

public class LineSegment {
    private final Point terminus1;
    private final Point terminus2;

    // constructs the line segment between points p and q
    public LineSegment(Point p, Point q){
        if (p == null || q == null) {
            throw new NullPointerException("argument is null");
        }
        terminus1 = p;
        terminus2 = q;
    }
    // draws this line segment
    public   void draw(){
        terminus1.drawTo(terminus2);
    }
    // string representation
    public String toString(){
        return terminus1 + " -> " + terminus2;
    }
    public int hashCode(){
        throw new UnsupportedOperationException();
    }
}