package aufgabe2;

import aufgabe1.Point;

import java.util.*;

public class main {

	public static void main(String[] args) {
	FileImport fileReader = new FileImport();
	CalculationsPolygon calc = new CalculationsPolygon();
	DrawPolygon draw;
	List<State> list = fileReader.readFileStates("DeutschlandMitStaedten.svg");
	List<City> cities = fileReader.readFileCities("DeutschlandMitStaedten.svg");
	Map<String,Double> areasOfStates = new HashMap<>();
	double area = 0;
	List<Integer> listPolyInsidePoly = new LinkedList<>();
	System.out.println();

	for(int i = 0; i < list.size(); i++){
		for(int j = 0; j < list.get(i).getCircles().size(); j++){
			//Check for hole in the polygon.
			//If one point in the polygon is inside of the first (main) (assumption) polygon, then subtract, otherwise add
			if(j != 0) {
				if (calc.polyContainsPoint(list.get(i).getCircles().get(0), new Point(list.get(i).getCircles().get(j).get(0).getCoordX(), list.get(i).getCircles().get(j).get(0).getCoordY()))) {
					area -= calc.calcPolygonArea(list.get(i).getCircles().get(j));
					listPolyInsidePoly.add(j);
				} else {
					area += calc.calcPolygonArea(list.get(i).getCircles().get(j));
				}
			}
			else{
				area = calc.calcPolygonArea(list.get(i).getCircles().get(j));
			}
		}
		for(int j=0; j<listPolyInsidePoly.size(); j++){
			//tought of adding the inner poly to to outer poly. Works fine, but then there is one line segment crossing.
			list.get(i).addPointsToCircle(0,list.get(i).getCircles().get(listPolyInsidePoly.get(j)));
			list.get(i).deleteCircle(listPolyInsidePoly.get(j-j));
		}
		listPolyInsidePoly.clear();
		areasOfStates.put(list.get(i).getName(), area);
		area = 0;
	}
	System.out.println("Sorted States by its size:");
	System.out.println(entriesSortedByValues(areasOfStates));
	System.out.println();
		//System.out.println(list.get(7).getName());
		draw = new DrawPolygon(list.get(7).getCircles());
		//System.out.println(calc.containsPoint(list.get(14), cities.get(0).getCoordinate()));
		//System.out.println(list.get(14).getName() + " " + cities.get(0).getName());


	for(int i = 0; i < cities.size(); i++){
		for(int j = 0; j < list.size(); j++){
			for(int k=0; k < list.get(j).getCircles().size(); k++){
				if(calc.polyContainsPoint(list.get(j).getCircles().get(k),cities.get(i).getCoordinate())) {
					System.out.println(cities.get(i).getName() + " liegt in " + list.get(j).getName());
				}
			}
		}
	}
}

	static List<Map.Entry<String, Double>> entriesSortedByValues(Map<String,Double> map) {

		List<Map.Entry<String, Double>> sortedEntries = new ArrayList<Map.Entry<String, Double>>(map.entrySet());

		Collections.sort(sortedEntries,
				new Comparator<Map.Entry<String, Double>>() {
					@Override
					public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) {
						return e2.getValue().compareTo(e1.getValue());
					}
				}
		);

		return sortedEntries;
	}
}
