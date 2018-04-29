package aufgabe;


import java.util.List;

import aufgabe.Logic;


public class LogicA2 {

	
	public double calcAcreage(State state) {
		
		System.out.println("Area of: "+ state.getName());
		double result = 0;
		
		for(int i = 0; i < (state.getCircles().size()); i++) {
			List<Point> circle = state.getCircles().get(i);
			double result2 = 0;
			for(int numberOfPointsInCircle = 0; numberOfPointsInCircle < (circle.size()-1); numberOfPointsInCircle++) {
				result2 = result2 + calcOneAcreage(circle.get(numberOfPointsInCircle), circle.get(numberOfPointsInCircle+1));	
			}
			result = result + Math.abs(result2);			// if area of polygone is < 0, it will be added as positive area
			//System.out.println(result);
		}
		
		return result;
	}
	

	public double calcOneAcreage(Point p1, Point p2) {
//		System.out.println(p1.getCoordX() + " " +p1.getCoordY());
//		System.out.println(p2.getCoordX() + " " +p2.getCoordY());
		double calc = ((p1.getCoordY() + p2.getCoordY()) / 2) * (p1.getCoordX() - p2.getCoordX());
		
		return calc;
		
	}
	
	public boolean pointInPolygone(Point pointToCheck, List<Point> polygone) {
		
		if(polygone.size() < 1) {
			return false;
		}

		double minX = polygone.get(0).getCoordX();
		double maxX = polygone.get(0).getCoordX();
		double minY = polygone.get(0).getCoordY();
		double maxY = polygone.get(0).getCoordY();
		
		for(int i = 1; i < polygone.size(); i++) {
			if(polygone.get(i).getCoordX() < minX) {
				minX=polygone.get(i).getCoordX();
			}
			else if(polygone.get(i).getCoordX() > maxX) {
				maxX=polygone.get(i).getCoordX();
			}
			
			if(polygone.get(i).getCoordY() < minY) {
				minY=polygone.get(i).getCoordY();
			}else if(polygone.get(i).getCoordY() > maxY) {
				maxY=polygone.get(i).getCoordY();
			}
		}
		
		Point testPoint = new Point(maxX+100, maxY+100);
		
		if ((minX < pointToCheck.getCoordX() && pointToCheck.getCoordX() < maxY) && (minY < pointToCheck.getCoordY() && pointToCheck.getCoordY() < maxY)) { //out of the box
			return false;
		}
		
		
		Logic logicA1 = new Logic();
		int i = 1;
		while (logicA1.ccw(testPoint, pointToCheck, polygone.get(i)) == 0) {
			i++;
		}
		
		int s= 0;
		int lr = (int) Math.abs(logicA1.ccw(testPoint, pointToCheck, polygone.get(i)));
		
		for(int j = i; j < polygone.size(); j++) {
			
			int lrnew = (int) Math.abs(logicA1.ccw(testPoint, pointToCheck, polygone.get(j)));
			if((lrnew - lr) == 2) {
			
				lr = lrnew;
				if(logicA1.ccw(polygone.get(j-1), polygone.get(j), testPoint)*logicA1.ccw(polygone.get(j-1), polygone.get(j), pointToCheck) <= 0) {
					s++;
				}
			}
		}
		System.out.println(s);
		
		
		
		return false;
	}
	
	
}
