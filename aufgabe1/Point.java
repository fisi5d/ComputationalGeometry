package aufgabe1;

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
	public void setCoordY(double ccordY) {

		this.coordY = ccordY;
	}

	@Override
	public String toString(){
		return "(" + getCoordX() + ", " + getCoordY() +")";
	}
	
}
