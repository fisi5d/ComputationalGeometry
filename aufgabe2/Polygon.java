package aufgabe2;

import aufgabe1.Point;

import java.util.LinkedList;
import java.util.List;

public class Polygon {
    private List<Point> points = new LinkedList<>();
    private List<Point> pointsHole = null;

    public Polygon(List<Point> points){
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPointsHole() {
        return pointsHole;
    }

    public void setPointsHole(List<Point> pointsHole) {
        this.pointsHole = pointsHole;
    }
}
