package aufgabe;

import java.util.List;

import aufgabe.FileImport;
import aufgabe.Point;


public class main {

	public static void main(String[] args) {
		FileImport fileReader = new FileImport();
		List<Point> list = fileReader.readFile("strecken/s_1000_1.dat");
//		System.out.println(list.size());
		int counter = 0;
		int noIntersection = 0;
		Logic logic = new Logic();
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < list.size(); i=i+2) {
			//erste Strecke
			Point p_one = list.get(i);
			Point p_two = list.get(i+1);
			
			for (int j = i+2; j < list.size(); j=j+2) {
				//zweite Strecke
				Point p_three = list.get(j);
				Point p_four = list.get(j+1);
				
				if((logic.ccw(p_one, p_two, p_three) * logic.ccw(p_one, p_two, p_four)) <= 0) {
					if((logic.ccw(p_three, p_four, p_one) * logic.ccw(p_three, p_four, p_two)) <= 0) {
						if(logic.check(p_one, p_two, p_three, p_four) ) {
							counter ++;
						}else {
							noIntersection++;
						}
					
						
					} else {
						noIntersection++;
					}
				} 
				else {
					noIntersection++;
				}
				
			}
			
		}
		long time = System.currentTimeMillis() - startTime;
		
		System.out.println("Überschneidungen: " +counter);
		System.out.println("keine Überschneidung: "+noIntersection);
		System.out.println("Dauer: " +time+"ms");
			
	}

}
