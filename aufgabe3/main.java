package aufgabe3;

import aufgabe1.FileImport;
import aufgabe1.Point;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
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
        System.out.println("Computational: Aufgabe 3");

        //for(int f = 0; f < fileNames.length; f++) {
            File file = new File(fileNames[0]);
            listPoints = new LinkedList<>();
            listPoints = fileReader.readFile(file.getPath());

/*
            listPoints.add(new Point(30, 70));
            listPoints.add(new Point(80, 0));

            listPoints.add(new Point(10,30));
            listPoints.add(new Point(80,30));

            listPoints.add(new Point(20, 10));
            listPoints.add(new Point(60, 70));

            listPoints.add(new Point(30, 50));
            listPoints.add(new Point(40, 50));

            listPoints.add(new Point(10, 10));
            listPoints.add(new Point(10.1, 10.1));

            listPoints.add(new Point(10, 10));
            listPoints.add(new Point(10, 10.1));

            listPoints.add(new Point(50, 70));
            listPoints.add(new Point(75, 25));

*/
/*
            listPoints.add(new Point(5, 90));
            listPoints.add(new Point(90, 40));

            listPoints.add(new Point(20,25));
            listPoints.add(new Point(45,80));

            listPoints.add(new Point(10, 50));
            listPoints.add(new Point(90, 60));

            listPoints.add(new Point(30, 35));
            listPoints.add(new Point(40, 40));

            listPoints.add(new Point(50, 45));
            listPoints.add(new Point(50, 45));

            listPoints.add(new Point(50, 45));
            listPoints.add(new Point(50, 45));
*/

            System.out.println("Start calculating...");
            System.out.println("File: " + file.getName());
            long startTime = System.currentTimeMillis();

            List<Event> result = calcLineSweep.lineSweep(listPoints);

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Number of intersections: " + result.size());
            System.out.println("Elapsed Time in milliseconds: " + elapsedTime);
            System.out.println();
            listPoints.clear();



            for(int i=0; i<result.size(); i++){
                //System.out.println(result.get(i).getSegment().getName() + result.get(i).getSegmentIntersection().getName());
                //System.out.println(result.get(i).getSegment().toString() + " --->> " + result.get(i).getSegmentIntersection().toString());
            }

        //}
    }
}

