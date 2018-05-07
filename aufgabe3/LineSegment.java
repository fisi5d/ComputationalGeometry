package aufgabe3;

import aufgabe1.Point;

public class LineSegment {

    private Point startPoint;
    private Point endPoint;

    public LineSegment(Point startPoint, Point endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
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

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LineSegment that = (LineSegment) o;
        return this.getStartPoint().equals(that.getStartPoint()) && this.getEndPoint().equals(that.getEndPoint());
    }
}
