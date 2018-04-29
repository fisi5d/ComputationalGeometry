package aufgabe;

public class City {
	private String name;
	private Point point;
	
	City(String name, Point point) {
		this.name = name;
		this.point = point;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	
}
