package aufgabe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aufgabe.Point;

public class FileImport {

	public Information readFile(String filename) {
		Information infos = new Information();
		List<State> states = new ArrayList<State>();
		List<City> cities = new ArrayList<City>();
		try {
		Pattern patternCity = Pattern.compile(".*odipodi:type=.*");
		Matcher matchCity;
			
		Pattern patternState = Pattern.compile( "<path id=.*" );
		Matcher matchState;
		
		
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
		    String line = br.readLine();
		    
		    while (line != null) {
		    	matchState = patternState.matcher(line);
		    	matchCity = patternCity.matcher(line);
		    	if(matchState.matches()) {					// Found a State
		    		String[] name = line.split("\"");
		    		State state = new State(name[1]);
		    							
		    		line = br.readLine();
		    		while(!line.equals("\"/>")) {
		    			state.addCircle(createCircle(br, line));
		    			line = br.readLine();
	
		    		}
		    		//System.out.println("finished reading State "+ state.getName());
		    		states.add(state);
		    		
		    	} 
		    	if (matchCity.matches()) {
		    		br.readLine();  // ignore style line
		    		line = br.readLine();  //name
		    		String name = line.trim();
		    		name = name.substring("id=".length(), name.length()).replace("\"", "");
		    		
		    		line = br.readLine();
		    		String xString = line.trim();
		    		double x = Double.parseDouble(xString.substring("sodipodi:cx=".length(), xString.length()).replace("\"", ""));
		    		
		    		line = br.readLine();
		    		String yString = line.trim();
		    		double y = Double.parseDouble(yString.substring("sodipodi:cy=".length(), yString.length()).replace("\"", ""));
		    		
		    		Point point = new Point(x, y);
		    		
		    		City city = new City(name, point);
		    		cities.add(city);
		    		
		    		while(!line.equals("/>")) {
		    			line = br.readLine();
		    		}
		    	}
		    	line = br.readLine();
		    }
		} finally {
		    br.close();
		}
		
		} catch(IOException e) {
			System.err.println(e);
		}
		infos.setStates(states);
		infos.setCities(cities);
		return infos;
	}
	

	
	private List<Point> createCircle(BufferedReader br, String currentLine) throws IOException {
		List<Point> circle = new ArrayList<Point>();
		
		while(!currentLine.startsWith("z")) {
			String horizontLine = null;

			if(currentLine.split("H").length > 1) {
				horizontLine = currentLine.split("H")[1];	
				currentLine =  currentLine.split("H")[0];	
			}
			
			switch(currentLine.charAt(0)) {
			case 'M':
				circle.add(createPoint(currentLine));
				break;
			case 'L':
				circle.add(createPoint(currentLine));
				break;
			case 'l':
				Point lastPoint = circle.get(circle.size()-1);
				
				Point newPoint = createPoint(currentLine);
				newPoint.setCoordX(lastPoint.getCoordX() + newPoint.getCoordX());
				newPoint.setCoordY(lastPoint.getCoordY() + newPoint.getCoordY());
				circle.add(newPoint);
				if(horizontLine!= null) {
					newPoint.setCoordX(newPoint.getCoordX() + Double.parseDouble(horizontLine));
					circle.add(newPoint);	
				}
				break;
			default:
				System.err.println("Switch error: " +currentLine);
				break;
			}

			currentLine = br.readLine();
		}
	
		return circle;
		
	}
	
	private Point createPoint(String line) {
		String pointString = line.substring(1, line.length());
		String [] coordinates = pointString.split(",");
		try {
			Point p = new Point(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
			return p;
		} catch (Exception e) {
			System.err.println("Fehler in Zeileninhalt: " + line);
		}
		return null;
		
	}
	
}
