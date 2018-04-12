package aufgabe1;

public class Point {
	
	private double coordX;
	private double ccordY;

	Point(double x, double y) {
		setCoordX(x);
		setCcordY(y);
	}

	public double getCoordX() {
		return coordX;
	}
	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}
	public double getCcordY() {
		return ccordY;
	}
	public void setCcordY(double ccordY) {
		this.ccordY = ccordY;
	}
	
	
}
