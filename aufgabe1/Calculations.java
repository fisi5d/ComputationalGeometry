package aufgabe1;

import aufgabe3.LineSegment;

import java.text.DecimalFormat;

public class Calculations {

    /**
     * Given three points, this method determine if the directed segment AB is clockwise/counterclockwise/collinear
     * from a directed segment AC with respect to their common endpoint A.
     * This is done by calculating the cross product (B-A)x(C-A)
     * Runtime: O(1)
     * @param a Point A
     * @param b Point B
     * @param c Point C
     * @return -1 if counterclockwise, +1 if clockwise and 0 if collinear
     */
    public int crossProduct(Point a, Point b, Point c){
        // (aX*bY - aY*bX) + (aY*cx - aX*cY) + (bX*cY - cX*bY)
        /*
        double det = (a.getCoordX()*b.getCoordY() - a.getCoordY()*b.getCoordX()) +
                (a.getCoordY()*c.getCoordX() - a.getCoordX()*c.getCoordY()) +
                (b.getCoordX()*c.getCoordY() - c.getCoordX()*b.getCoordY());
        */
        //This method above has more multiplications and subs.
        //Therefore it is not sooo efficient as followed simplified one.

        double det = (b.getCoordX() - a.getCoordX()) * (c.getCoordY() - a.getCoordY())
                - (b.getCoordY() - a.getCoordY()) * (c.getCoordX() - a.getCoordX());

        if(det > 0)
            return 1;
        else if(det < 0)
            return -1;
        return 0;
    }

    public double crossProduct(Point a, Point b){
        return (a.getCoordX()*b.getCoordY()) - (a.getCoordY()*b.getCoordX());
    }

    /**
     * Checking if two line segments (represented by P1P2 and Q1Q2) having an intersection by using the product of the
     * cross product.
     * Also includes the special case of "touching" lines (one ore more similar points), which count as intersection
     * as well.
     * Runtime: O(1)
     * @param p1 Point P1
     * @param p2 Point P2
     * @param q1 Point Q1
     * @param q2 Point Q2
     * @return true if intersection
     */
    public boolean intersectingWithCCW(Point p1, Point p2, Point q1, Point q2){
        //Checking if the directed segments are having an opposite orientation (Points on other sides).
        //If so, the product is negative and accepted.
        //We need to accept the zero as well to include the special case "collinear".
        double det1 = (crossProduct(p1,p2,q1) * crossProduct(p1,p2,q2));
        double det2 = (crossProduct(q1,q2,p1) * crossProduct(q1,q2,p2));
        if(det1 > 0)
            return false;
        if(det2 > 0)
            return false;

        //Because we are accepting the cases with cross product equal zero, we need to check if one or more points
        // are on the same line. Otherwise there are not intersecting.
        if(det1 == 0 || det2 == 0) {
            if (onSegment(q1, q2, p1))
                return true;
            else if (onSegment(q1, q2, p2))
                return true;
            else if (onSegment(p1, p2, q1))
                return true;
            else if (onSegment(p1, p2, q2))
                return true;

            return false;
        }

        return true;
    }

    /**
     * Checking if two line segments (represented by P1P2 and Q1Q2) having an intersection by using the product of the
     * cross product.
     * Also includes the special case of "touching" lines (one ore more similar points), which count as intersection
     * as well.
     * @param a Linesegment P1P2
     * @param b Point Q1Q2
     * @return true if intersection
     */
    public boolean intersectingWithCCW(LineSegment a, LineSegment b){
        Point p1 = a.getStartPoint();
        Point p2 = a.getEndPoint();
        Point q1 = b.getStartPoint();
        Point q2 = b.getEndPoint();

        return intersectingWithCCW(p1, p2, q1, q2);
    }

    /**
     * Given three Points this method returns if point C is between the endpoints of segment AB and therefore
     * directly on the segment.
     * Important: For efficiency, this function assumes that C is collinear with segment AB.
     * Runtime: O(1)
     * @param a Point A
     * @param b Point B
     * @param c Point C
     * @return true if on segment
     */
    private boolean onSegment(Point a, Point b, Point c){
        //Check if x coordinate is between x endpoints
        if((Math.min(a.getCoordX(), b.getCoordX()) <= c.getCoordX()) && (Math.max(a.getCoordX(), b.getCoordX()) >= c.getCoordX())){
            //Check if y coordinate is between y endpoints
            if((Math.min(a.getCoordY(), b.getCoordY()) <= c.getCoordY()) && (Math.max(a.getCoordY(), b.getCoordY()) >= c.getCoordY())){
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the intersection point of two line segments (represented by P1P2 and Q1Q2) by solving the
     * linear equations.
     * Also includes the special case collinear.
     * @param a Linesegment P1P2
     * @param b Linesegment Q1Q2
     * @return point if intersection point or null
     */
    public Point intersectingWithEquations(LineSegment a, LineSegment b){

        if(intersectingWithCCW(a,b)){
            Point p1 = a.getStartPoint();
            Point p2 = a.getEndPoint();
            Point q1 = b.getStartPoint();
            Point q2 = b.getEndPoint();

            if((a.getStartPoint().equals(a.getEndPoint()) && (b.getStartPoint().equals(a.getStartPoint()) || b.getEndPoint().equals(a.getStartPoint())))){
                //System.out.println(a.getStartPoint()+ " | "+ a.toString() + " -> " + b.toString());
                return a.getStartPoint();
            }
            else if((b.getStartPoint().equals(b.getEndPoint()) && (a.getStartPoint().equals(b.getStartPoint()) || a.getEndPoint().equals(b.getStartPoint())))){
               // System.out.println(b.getStartPoint()+ " | "+a.toString() + " -> " + b.toString());
                return b.getStartPoint();
            }

            // Line p1p2 represented as a1x + b1y = c1
            double a1 = p2.getCoordY() - p1.getCoordY();
            double b1 = p1.getCoordX() - p2.getCoordX();
            double c1 = a1*(p1.getCoordX()) + b1*(p1.getCoordY());

            // Line q1q2 represented as a2x + b2y = c2
            double a2 = q2.getCoordY() - q1.getCoordY();
            double b2 = q1.getCoordX() - q2.getCoordX();
            double c2 = a2*(q1.getCoordX())+ b2*(q1.getCoordY());

            double determinant = a1*b2 - a2*b1;

            if(determinant == 0){
                //Collinear/parallel on its self
                //therefore no unique intersection point, so we choose start point
                return a.getStartPoint();

            }
            else {

                double x = (b2*c1 - b1*c2)/determinant;
                double y = (a1*c2 - a2*c1)/determinant;

                Point intersectionPoint = new Point(x,y);

                if(onSegment(p1,p2,intersectionPoint) && onSegment(q1,q2,intersectionPoint)) {
                   // System.out.println(intersectionPoint + " | "+a.toString() + " -> " + b.toString());
                    return intersectionPoint;
                }

            }
        }
        return null;
    }

}
