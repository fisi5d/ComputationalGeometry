package aufgabe;

import java.util.ArrayList;
import java.util.List;

public class State {
	
	private String name;
	private List<List<Point>> circles;
	
	
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
