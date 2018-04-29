package aufgabe;

import aufgabe.Point;

public class Logic {

	public double ccw(Point p_one, Point p_two, Point p_three) {

		double[][] matrix = createMatrix(p_one, p_two, p_three);

		double result = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0])
				+ (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0])
				+ (matrix[0][1] * matrix[2][0] - matrix[0][0] * matrix[2][1]);

		// System.out.println(result);
		if(result < 0) {
			return -1;
		}
		if (result ==0) {
			return 0;
		} else {
			return 1;
		}
		
		

	}

	public boolean check(Point p_one, Point p_two, Point p_three, Point p_four) {

		return (check(p_one, p_two, p_three) || check(p_one, p_two, p_four));
	}

	private boolean check(Point p_one, Point p_two, Point p_three) {

		if (((p_one.getCoordX() <= p_three.getCoordX() && p_two.getCoordX() >= p_three.getCoordX())
				|| (p_one.getCoordX() >= p_three.getCoordX() && p_two.getCoordX() <= p_three.getCoordX()))
				&& ((p_one.getCoordY() <= p_three.getCoordY() && p_two.getCoordY() >= p_three.getCoordY())
						|| (p_one.getCoordY() >= p_three.getCoordY() && p_two.getCoordY() <= p_three.getCoordY()))) {
			return true;
		} else {
			return false;
		}
	}

	private double[][] createMatrix(Point p_one, Point p_two, Point p_three) {
		double[][] matrix = new double[3][3];
		matrix[0][0] = p_one.getCoordX();
		matrix[0][1] = p_one.getCoordY();
		matrix[0][2] = 1;

		matrix[1][0] = p_two.getCoordX();
		matrix[1][1] = p_two.getCoordY();
		matrix[1][2] = 1;

		matrix[2][0] = p_three.getCoordX();
		matrix[2][1] = p_three.getCoordY();
		matrix[2][2] = 1;

		return matrix;
	}

}
