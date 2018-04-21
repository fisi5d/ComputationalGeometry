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
		    		br.readLine(); 						// brauch ich M???; wird aktuell ignoriert, wegen redundanz.
		    		line = br.readLine();
		    		while(!line.equals("\"/>")) {
		    			state.addCircle(createCircle(br, line));
		    			line = br.readLine();
		    		}
		    		System.out.println("finished State "+ state.getName());
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
		while(!currentLine.startsWith("z")) {
			String currentLine2 = currentLine;
			currentLine = currentLine.substring(1, currentLine.length());
			String [] coordinates = currentLine.split(",");
			try {
				Point p = new Point(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
				circle.add(p); 
			} catch (Exception e) {
				System.err.println("Fehler in Zeileninhalt: " + currentLine2);
			}
			currentLine = br.readLine();
		}
	
		return circle;
		
	}
	
}
