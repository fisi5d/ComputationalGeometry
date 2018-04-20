package aufgabe1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class main {

	/**
	 * Main method for calculating the number of intersections based on three files.
	 * Runtime: O(n)
	 * @param args
	 */
	public static void main(String[] args) {
		int counter = 0;
		long c =0;
		List<Point> listPoints = null;
		List<Point> listIntersections = new ArrayList<>();
		FileImport fileReader = new FileImport();
		Calculations calc = new Calculations();

		String[] files = new String[3];
		files[0] = "Strecken/s_1000_1.dat";
		files[1] = "Strecken/s_10000_1.dat";
		files[2] = "Strecken/s_100000_1.dat";



		for(int f = 0; f < files.length; f++){
			File file = new File(files[f]);
			listPoints = fileReader.readFile(file.getPath());

			System.out.println("Start calculating...");
			System.out.println("File: " + file.getName());
			long startTime = System.currentTimeMillis();

			for(int i=0; i<listPoints.size(); i=i+2) {
				for (int j = i + 2; j < listPoints.size(); j = j + 2) {
					c++;
					if (calc.intersectingWithCCW(listPoints.get(i), listPoints.get(i + 1), listPoints.get(j), listPoints.get(j + 1))) {
						counter = counter + 1;
						//System.out.println(listPoints.get(i).toString() + listPoints.get(i + 1).toString() + " | " + listPoints.get(j).toString() + listPoints.get(j + 1).toString() + " -> " + i/2 + " " + j/2);
						/*
						listIntersections.add(listPoints.get(i));
						listIntersections.add(listPoints.get(i + 1));
						listIntersections.add(listPoints.get(j));
						listIntersections.add(listPoints.get(j + 1));
						*/
					}
				}
			}

			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			System.out.println("Number of intersections: " + counter);
			System.out.println("Elapsed Time in milliseconds: " + elapsedTime);
			System.out.println("Comparisons: " + c);
			System.out.println("");

			counter = 0;
			c = 0;
			listPoints.clear();
		}


		/*
		class DrawTask implements Runnable {
			Point p1,p2,q1,q2;
			DrawTask(Point p1, Point p2, Point q1, Point q2) {
				this.p1 = p1;
				this.p2 = p2;
				this.q1 = q1;
				this.q2 = q2;
			}
			public void run() {
				new DrawLines(p1,p2,q1,q2).setVisible(true);
			}
		}

		for(int i = 0; i<listIntersections.size(); i=i+4){

			Thread t = new Thread(new DrawTask(listIntersections.get(i), listIntersections.get(i + 1), listIntersections.get(i+2), listIntersections.get(i + 3)));
			t.start();
		} */

	}

}
