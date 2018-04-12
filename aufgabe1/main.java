package aufgabe1;

import java.util.List;


public class main {

	public static void main(String[] args) {
		FileImport fileReader = new FileImport();
		List<Point> list = fileReader.readFile("C:/Users/Markus/Documents/workspace/Aufgabe1/strecken/s_1000_1.dat");
		//System.out.println(list.size());
		

	}

}
