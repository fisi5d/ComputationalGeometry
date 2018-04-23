package aufgabe2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aufgabe2.Point;

public class FileImport {

	public List<State> readFile(String filename) {
		List<State> states = new ArrayList<State>();

		try {
		
		Pattern pattern = Pattern.compile( "<path id=.*" );
		Matcher match;
		
		
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
		    String line = br.readLine();
		    
		    while (line != null) {
		    	match = pattern.matcher(line);
		    	if(match.matches()) {					// Found a State
		    		String[] name = line.split("\"");
		    		State state = new State(name[1]);
		    		//br.readLine(); 						// brauch ich M???; wird aktuell ignoriert, wegen redundanz.
		    		line = br.readLine();
		    		while(!line.equals("\"/>")) {
		    			state.addCircle(createCircle(br, line));
		    			line = br.readLine();
		    		}
		    		System.out.println("Finished reading State "+ state.getName());
		    		states.add(state);
		    	} 
		    	line = br.readLine();
		    }
		} finally {
		    br.close();
		}
		
		} catch(IOException e) {
			System.err.println(e);
		}
		return states;
	}
	

	
	private List<Point> createCircle(BufferedReader br, String currentLine) throws IOException {
		
		List<Point> circle = new ArrayList<Point>();
		Point p = null;
		while(!currentLine.startsWith("z") && !currentLine.startsWith("Z")) {

			//If line starts with an uppercase letter, coordinate is absolute
			//Else the coordinate is relative to the last point, so need to calc to absolute
			if(Character.isUpperCase(currentLine.charAt(0))){
				currentLine = currentLine.substring(1, currentLine.length());
				String [] coordinates = currentLine.split(",");
				try {
					p = new Point(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
					circle.add(p);
				} catch (Exception e) {
					System.err.println("1)Fehler in Zeileninhalt: " + currentLine);
				}
			}
			else{
				currentLine = currentLine.substring(1, currentLine.length());
				String [] coordinates = currentLine.split(",");
				try {
					//Check for horizontal or vertical abbreviations at the end of the line
					if(coordinates[1].contains("H")){
						String y = coordinates[1].substring(0,coordinates[1].indexOf("H"));
						p = new Point(p.getCoordX() - Double.parseDouble(coordinates[0]), p.getCoordY() - Double.parseDouble(y));
						circle.add(p);
						coordinates[0] = coordinates[1].substring(coordinates[1].indexOf("H")+1, coordinates[1].length());
						coordinates[1] = y;
					}
					p = new Point(p.getCoordX() - Double.parseDouble(coordinates[0]), p.getCoordY() - Double.parseDouble(coordinates[1]));
					circle.add(p);
				} catch (Exception e) {
					System.err.println("2)Fehler in Zeileninhalt: " + currentLine);
				}
			}

			currentLine = br.readLine();
		}
	
		return circle;
		
	}
	
}
