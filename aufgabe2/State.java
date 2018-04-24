package aufgabe2;

import java.util.ArrayList;
import java.util.List;
import aufgabe1.Point;

public class State {
	
	private String name;
	private List<List<aufgabe1.Point>> circles;
	
	
	public State(String descriptor) {
		this.name = descriptor;
		circles = new ArrayList<List<Point>>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<List<Point>> getCircles() {
		return circles;
	}
	public void addCircle(List<Point> circle) {
		this.circles.add(circle);
	}

}
