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
     * @param state
     * @param point
     * @return
     */
    public boolean containsPoint(State state, Point point){
        Calculations calcIntersection = new Calculations();
        double xMin = Double.MAX_VALUE,xMax = Double.MIN_VALUE,yMin = Double.MAX_VALUE,yMax=Double.MIN_VALUE;
        boolean insideBox = false;
        List<Integer> possiblePoly = new LinkedList<>();
        int countIntersections = 0;
        Point endPointRay = point;

        //Create an axis aligned bounding box around the polygon, to safe unwanted calculations. If the point
        //is outside of the box it is definitely outside of the polygon and therefore safes
        //further intersection calculations
        for(int i=0; i<state.getCircles().size(); i++){
            for(int j=0; j<state.getCircles().get(i).size(); j++){
                if(state.getCircles().get(i).get(j).getCoordX() < xMin)
                    xMin = state.getCircles().get(i).get(j).getCoordX();
                else if(state.getCircles().get(i).get(j).getCoordX() > xMax)
                    xMax = state.getCircles().get(i).get(j).getCoordX();

                if(state.getCircles().get(i).get(j).getCoordY() < yMin)
                    xMin = state.getCircles().get(i).get(j).getCoordX();
                else if(state.getCircles().get(i).get(j).getCoordY() > yMax)
                    xMax = state.getCircles().get(i).get(j).getCoordX();
            }
            if (point.getCoordX() < xMin || point.getCoordX() > xMax || point.getCoordY() < yMin || point.getCoordY() > yMax) {
                // Definitely not within the polygon!
            }
            else{
                // Possible within the polygon n. Safes the polygon number to speed up further calculations
                insideBox = true;
                possiblePoly.add(i);
            }
        }
        if(!insideBox) {
            //Point is definitely not within any polygon, end here!
            return false;
        }

        endPointRay.setCoordX(xMin -5);
        endPointRay.setCoordY(yMin -5);
        //Using the intersection counting method (ray casting)
        for(int i=0; i<possiblePoly.size(); i++){
            for(int sides=0; sides < state.getCircles().get(possiblePoly.get(i)).size(); sides = sides + 2) {
                if(calcIntersection.intersectingWithCCW(point, endPointRay,state.getCircles().get(possiblePoly.get(i)).get(sides), state.getCircles().get(possiblePoly.get(i)).get(sides+1))){
                    countIntersections++;
                }
            }
            if(countIntersections%2 == 0){
                //count intersection is even and therefore OUTSIDE!
            }
            else{
                //count intersection is odd and therefore INSIDE!
                return true;
            }
        }

    return false;
    }
}
