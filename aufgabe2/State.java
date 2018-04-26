package aufgabe2;

import java.util.ArrayList;
import java.util.List;
import aufgabe1.Point;

public class State {
	
	private String name;
	private List<List<Point>> circles;
	
	
	public State(String descriptor) {
		this.name = descriptor;
		circles = new ArrayList<>();
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

	public void deleteCircle(int i){
		this.circles.remove(i);
	}

	public void addPointsToCircle(int index, List<Point> points){
		for(int i=0; i<points.size(); i++){
			this.circles.get(index).add(points.get(i));
		}

	}

}
