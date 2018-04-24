package aufgabe2;

import java.util.*;

public class main {

	public static void main(String[] args) {
	FileImport fileReader = new FileImport();
	CalculationsPolygon calc = new CalculationsPolygon();
	List<State> list = fileReader.readFile("DeutschlandMitStaedten.svg");
	Map<String,Double> areasOfStates = new HashMap<>();
	double area = 0;
	System.out.println();

	for(int i = 0; i < list.size(); i++){
		for(int j = 0; j < list.get(i).getCircles().size(); j++){
			area += calc.calcPolygonArea(list.get(i).getCircles().get(j));
		}
		areasOfStates.put(list.get(i).getName(), area);
		area = 0;
	}
	System.out.println("Sorted States by its size:");
	System.out.println(entriesSortedByValues(areasOfStates));
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
