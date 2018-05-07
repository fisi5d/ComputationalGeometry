package aufgabe1;

public class Point {
	
	private double coordX;
	private double coordY;

	public Point(double x, double y) {
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
	public boolean equals(Object o){
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Point that = (Point) o;
		return this.getCoordX() == that.getCoordX() && this.getCoordY() == that.getCoordY();
	}

	@Override
	public String toString(){
		return "(" + getCoordX() + ", " + getCoordY() +")";
	}
	
}
