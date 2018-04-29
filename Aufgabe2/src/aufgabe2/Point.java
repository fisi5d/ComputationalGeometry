package aufgabe;

public class Point {
	
	private double coordX;
	private double coordY;


	Point(double x, double y) {
		setCoordX(x);
		setCoordY(y);
	}

	public double getCoordX() {
		return coordX;
	}
	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}
	public double getCoordY() {
		return coordY;
	}
	public void setCoordY(double coordY) {
		this.coordY = coordY;
	}
	
	
}
