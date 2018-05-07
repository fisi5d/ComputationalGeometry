package aufgabe3;

import aufgabe1.FileImport;
import aufgabe1.Point;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class main {

    public static void main (String[] args){
        List<Point> listPoints = null;
        FileImport fileReader = new FileImport();
        String[] fileNames = new String[4];
        CalculationLineSweep calcLineSweep = new CalculationLineSweep();

        fileNames[0] = "Strecken/s_1000_10.dat";
        fileNames[1] = "Strecken/s_1000_1.dat";
        fileNames[2] = "Strecken/s_10000_1.dat";
        fileNames[3] = "Strecken/s_100000_1.dat";

        //for(int f = 0; f < fileNames.length; f++) {
            File file = new File(fileNames[0]);
            listPoints = fileReader.readFile(file.getPath());
            TreeSet<Event> e = calcLineSweep.init(listPoints);

            /*
        Iterator<Event> it = e.iterator();
        while(it.hasNext()){
            Event t = it.next();
            System.out.println(t.type + " | " + t.point + " | " + t.segment.);
        }*/

           // System.out.println("Start calculating...");
           // System.out.println("File: " + file.getName());

        //}
    }
}

