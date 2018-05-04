package aufgabe2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aufgabe1.Point;

public class FileImport {

	public List<State> readFileStates(String filename) {
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
		    			state.addCircle(new Polygon(createCircle(br, line)));
		    			line = br.readLine();
		    		}
		    		//System.out.println("Finished reading State "+ state.getName());
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
		System.out.println("Finish reading states.");
		return states;
	}

	public List<City> readFileCities(String filename){
		List<City> cities = new LinkedList<>();
		String name = "";
		double x = -1;
		double y = -1;

		try {
			Pattern pattern = Pattern.compile( "<path" );
			Matcher match;


			BufferedReader br = new BufferedReader(new FileReader(filename));
			try {
				String line = br.readLine();

				while (line != null) {
					match = pattern.matcher(line);
					if(match.matches()) {					// Found a city
						line = br.readLine();
						while(!line.equals("/>")) {
							if(line.trim().startsWith("id=")) {
								name = line.split("\"")[1];
							}
							else if(line.trim().startsWith("sodipodi:cx")) {
								x = Double.parseDouble(line.split("\"")[1]);
							}
							else if(line.trim().startsWith("sodipodi:cy")) {
								y = Double.parseDouble(line.split("\"")[1]);
							}
							line = br.readLine();
						}
						cities.add(new City(name, new Point(x,y)));
						//System.out.println("Finished reading City: "+ name + "("+x+","+y+")");
					}
					line = br.readLine();
				}
			} finally {
				br.close();
			}

		} catch(IOException e) {
			System.err.println(e);
		}
		System.out.println("Finish reading cities.");
		return cities;
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
						p = new Point(p.getCoordX() + Double.parseDouble(coordinates[0]), p.getCoordY() + Double.parseDouble(y));
						circle.add(p);
						coordinates[0] = coordinates[1].substring(coordinates[1].indexOf("H")+1, coordinates[1].length());
						coordinates[1] = y;
						p = new Point(Double.parseDouble(coordinates[0]), p.getCoordY() + Double.parseDouble(coordinates[1]));
					}
					else{
						p = new Point(p.getCoordX() + Double.parseDouble(coordinates[0]), p.getCoordY() + Double.parseDouble(coordinates[1]));
					}

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
