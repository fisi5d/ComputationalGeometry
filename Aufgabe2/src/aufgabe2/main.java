package aufgabe2;

import java.util.List;

import aufgabe2.FileImport;
import aufgabe2.Point;

public class main {
	public static void main(String[] args) {
	FileImport fileReader = new FileImport();
	List<State> list = fileReader.readFile("DeutschlandMitStaedten.svg");
	System.out.println(list.size());
}
}
