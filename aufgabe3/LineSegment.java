package aufgabe3;

import aufgabe1.Point;

public class LineSegment {

    private Point startPoint;
    private Point endPoint;
    private final String name;

    public LineSegment(Point startPoint, Point endPoint, String name){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.name = name;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        LineSegment that = (LineSegment) o;
        //Compare start and endpoint as well as the name
        return ((this.getStartPoint().equals(that.getStartPoint())) && (this.getEndPoint().equals(that.getEndPoint())) && (this.getName().equals(that.getName())));
    }

    @Override
    public String toString(){
        return getName() + ": " + getStartPoint() + "|" + getEndPoint();
    }
}
