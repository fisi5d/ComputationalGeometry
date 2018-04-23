package aufgabe2;

import java.util.List;

public class Calculations {

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
}
