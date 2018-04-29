package aufgabe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aufgabe.Point;

public class FileImport {

	public List<Point> readFile(String filename) {
		List<Point> list = new ArrayList<Point>();
		try {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
		    String line = br.readLine();
		    
		    while (line != null) {
			    String[] points = line.split(" ");
			    Point firstPoint = new Point(Double.parseDouble(points[0]), Double.parseDouble(points[1]));
			    Point secondPoint = new Point(Double.parseDouble(points[2]), Double.parseDouble(points[3]));
			    list.add(firstPoint);
			    list.add(secondPoint);
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}
		
		} catch(IOException e) {
			System.err.println(e);
		}
		return list;
	}
	
}
