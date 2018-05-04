package aufgabe2;

import java.util.LinkedList;
import java.util.List;
import aufgabe1.Calculations;
import aufgabe1.Point;

public class CalculationsPolygon {

    /**
     * Calculates the area of a 2D polygon.
     * Going down every line segment (PnPm) and take the area to the left of the chosen side.
     * @param listOfCoords list of points
     * @return area
     */
    public double calcPolygonArea(List<Point> listOfCoords){
        double area = 0;
        int j = listOfCoords.size() -1; //The last vertex is the "previous" one to the first

        for(int i = 0; i < listOfCoords.size(); i++){
            //Calculates a rectangle to the left of the line by ((X0+X1)/2)*(Y0-Y1)
            //For efficiency the division of 2 is moved to the end
            //By going down one side and going up the other side (around the polygon) only the area of
            //the polygon stays. The area to much on the left is getting subtracted.
            area = area + ((listOfCoords.get(j).getCoordX()+listOfCoords.get(i).getCoordX()) * (listOfCoords.get(j).getCoordY()-listOfCoords.get(i).getCoordY()));
            j = i;
        }
        return Math.abs(area/2);
    }

    /**
     * This method checks if a point is inside of a given polygon by using the ray casting algorithm.
     * Works with closed polygons (convex, concave, with hole).
     * @param poly
     * @param point
     * @return
     */
    public boolean polyContainsPoint(Polygon poly, Point point){

        double xMin = Double.MAX_VALUE,xMax = Double.MIN_VALUE,yMin = Double.MAX_VALUE,yMax=Double.MIN_VALUE;
        boolean insideBox = false;
        boolean tmp = false;
        List<Integer> possiblePoly = new LinkedList<>();

        int countCollinear = 0;
        Point endPointRay;

        //Create an axis aligned bounding box around the polygon, to safe unwanted calculations. If the point
        //is outside of the box it is definitely outside of the polygon and therefore safes
        //further intersection calculations
        for(int j=0; j<poly.getPoints().size(); j++){
            if(poly.getPoints().get(j).getCoordX() < xMin)
                xMin = poly.getPoints().get(j).getCoordX();
            else if(poly.getPoints().get(j).getCoordX() > xMax)
                xMax = poly.getPoints().get(j).getCoordX();

            if(poly.getPoints().get(j).getCoordY() < yMin)
                yMin = poly.getPoints().get(j).getCoordY();
            else if(poly.getPoints().get(j).getCoordY() > yMax)
                yMax = poly.getPoints().get(j).getCoordY();
        }
        if (point.getCoordX() < xMin || point.getCoordX() > xMax || point.getCoordY() < yMin || point.getCoordY() > yMax) {
            // Definitely not within the polygon!
            return false;
        }

        //This endpoint is definitely outside of the polygon (different offsets -> not going trough corner
        endPointRay = new Point(xMin-5,yMin-2);
        //Using the intersection counting method (ray casting)
        tmp = rayCasting(poly.getPoints(), endPointRay, point);
        if(poly.getPointsHole() != null){
            //if point is inside of the hole, its definitely outside of the outer polygon
            if(rayCasting(poly.getPointsHole(), endPointRay, point)){
                tmp = false;
            }
            //Otherwise its the result of the first round
        }

        return tmp;
    }

    private boolean rayCasting(List<Point> points, Point endPointRay, Point givenPoint){
        Calculations calcIntersection = new Calculations();
        int countIntersections = 0;

        int colPoint = 0;
        int lr = 0;
        int lrNew = 0;
        for(int i=0; i<points.size(); i++){
            lr = calcIntersection.crossProduct(endPointRay,givenPoint,points.get(colPoint));
            if(lr == 0){
                colPoint++;
            }
            else{
                break;
            }
        }
        for(int j=colPoint+1; j<points.size(); j++){
            lrNew = calcIntersection.crossProduct(endPointRay,givenPoint,points.get(j));
            if(Math.abs(lrNew-lr) == 2){
                lr = lrNew;
                if(calcIntersection.crossProduct(points.get(j-1),points.get(j),endPointRay) * calcIntersection.crossProduct(points.get(j-1),points.get(j),givenPoint) <= 0){
                    countIntersections++; //Point and Endpoint are on different sides of polyline
                }
            }
        }
        if(countIntersections%2 == 0){
            //count intersection is even and therefore OUTSIDE!
        }
        else{
            //count intersection is odd and therefore INSIDE!
            return true;
        }

        return false;
    }
}
