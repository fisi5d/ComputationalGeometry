package aufgabe3;

import aufgabe1.Calculations;
import aufgabe1.Point;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.*;

public class CalculationLineSweep {

    Calculations calcIntersect = new Calculations();
    List<LineSegment> listSweepLine = new LinkedList<>();

    private int addSegmentIntoSweep(LineSegment seg){
        int index = 0;
        if(listSweepLine.isEmpty()) {
            listSweepLine.add(seg);
            return 0;
        }
        else{

            for(int i=0; i<listSweepLine.size(); i++) {
                double x = (listSweepLine.get(i).getEndPoint().getCoordX() - listSweepLine.get(i).getStartPoint().getCoordX());
                double y = (listSweepLine.get(i).getEndPoint().getCoordY() - listSweepLine.get(i).getStartPoint().getCoordY());
                double dif = Math.abs(listSweepLine.get(i).getStartPoint().getCoordX() - seg.getStartPoint().getCoordX());
                double difX = dif / x;
                double intersectSweepY = ((difX * y) + listSweepLine.get(i).getStartPoint().getCoordY());

                double o1StartY = (long) (seg.getStartPoint().getCoordY() * 1e5) / 1e5;
                double o1EndY = (long) (seg.getEndPoint().getCoordY() * 1e5) / 1e5;
                double o2EndY = (long) (listSweepLine.get(i).getEndPoint().getCoordY() * 1e5) / 1e5;
                double intY = (long) (intersectSweepY * 1e5) / 1e5;

                if (o1StartY < intY) {
                    listSweepLine.add(i, seg);
                    return i;
                }else if (o1StartY > intY) {
                    //do nothing wait till smaller
                }else {
                    //y start the same
                    if (o1EndY < o2EndY) {
                        listSweepLine.add(i, seg);
                        return i;
                    }
                }
            }
            listSweepLine.add(seg);
            return listSweepLine.size()-1;
        }
    }

    public List<Event> lineSweep(List<Point> list){
        /**
         * TreeSet because this implementation provides guaranteed log(n) time cost for the basic operations (add, remove and contains)
         */
        TreeSet<Event> eventQueue;


       /*
        TreeSet<LineSegment> segmentOrder = new TreeSet<>(new Comparator<LineSegment>() {
            @Override
            public int compare(LineSegment o1, LineSegment o2) {
                if(o1.equals(o2))
                    return 0;

                //ccw > 0 if point (start o1) is on the right side (below) of the segment (o2) (cw)
                //ccw < 0 if point (start o1) is on the left side (above) of the segment (o2)
                //System.out.println(o2.getName() +"x"+o1.getName()+"="+calcIntersect.crossProduct(o2.getStartPoint(), o2.getEndPoint(), o1.getStartPoint()));
                //return calcIntersect.crossProduct(o2.getStartPoint(), o2.getEndPoint(), o1.getStartPoint());

                double x = (o2.getEndPoint().getCoordX() - o2.getStartPoint().getCoordX());
                double y = (o2.getEndPoint().getCoordY() - o2.getStartPoint().getCoordY());
                double dif = Math.abs(o2.getStartPoint().getCoordX()-o1.getStartPoint().getCoordX());
                double difX = dif/x;
                double intersectSweepY = ((difX * y)+o2.getStartPoint().getCoordY());

                //Point intersectionPoint = new Point(o1.getStartPoint().getCoordX(), ((difX * y)+o2.getStartPoint().getCoordY()));

                double o1StartY = (long)(o1.getStartPoint().getCoordY() * 1e5) /1e5;
                double o1EndY = (long)(o1.getEndPoint().getCoordY() * 1e5) /1e5;
                double o2EndY = (long)(o2.getEndPoint().getCoordY() * 1e5) /1e5;
                double intY = (long)(intersectSweepY * 1e5) /1e5;


                if(o1StartY < intY)
                    return -1;
                else if(o1StartY > intY)
                    return 1;
                else{
                    //y start the same
                    if(o1EndY < o2EndY)
                        return -1;
                    else if(o1EndY > o2EndY)
                        return 1;
                    else
                        return -1;
                }

            }
        });*/
        List<Event> output = new LinkedList<>();
        eventQueue = init(list);

        Point currentEventPoint;
        LineSegment sweepLine;

        while(!eventQueue.isEmpty()){
            //-----------------------------------------------
/*
            String c = "";
            for(Event ev : eventQueue){
                if(ev.getSegmentIntersection() != null)
                    c += ev.getType() + " " + ev.getSegment().getName() + ev.getSegmentIntersection().getName()+ ", ";
                else
                    c += ev.getType() + " " + ev.getSegment().getName() + ",";
            }
            System.out.println("Queue: " + c);

            String d = "";
            for(LineSegment s : listSweepLine){
                d += s.getName() + ", ";
            }
            System.out.println("Order: " + d);*/
            //-------------------------------------------------
            Event currentEvent = eventQueue.pollFirst(); //Retrieves and removes the first element
            //-------------------------------------------------
/*            System.out.println();
            if(currentEvent.getSegmentIntersection()!= null)
                System.out.println("Current Event: " + currentEvent.getType()+ " " + currentEvent.getSegment().getName() + currentEvent.getSegmentIntersection().getName());
            else
                System.out.println("Current Event: " + currentEvent.getType() + " " + currentEvent.getSegment().getName());*/
            //--------------------------------------------------
            if(currentEvent.getType() == Event.EventType.START){
                //ADD NEW SEGMENT
                LineSegment segEvent = currentEvent.getSegment();
                int index = addSegmentIntoSweep(segEvent);
                LineSegment segAbove = null;
                LineSegment segBelow = null;
                //System.out.println(index);
                if(index < listSweepLine.size()-1){
                    segAbove = listSweepLine.get(index+1);
                    //System.out.println("A: " + segAbove.getName());
                }
                if((index) > 0) {
                    segBelow = listSweepLine.get(index - 1);
                    //System.out.println("B: " + segBelow.getName());
                }
                //LineSegment segAbove = segmentOrder.higher(segEvent);
                //LineSegment segBelow = segmentOrder.lower(segEvent);

                if(segAbove != null) {
                    Point intersectionPoint = calcIntersect.intersectingWithEquations(segEvent, segAbove);
                    if ((intersectionPoint != null) && (intersectionPoint.getCoordX() >= currentEvent.getPoint().getCoordX())) {
                        Event e = new Event(Event.EventType.INTERSECTION, intersectionPoint,segEvent, segAbove);
                        eventQueue.add(e);
                    }
                }
                if(segBelow != null){
                    Point intersectionPoint = calcIntersect.intersectingWithEquations(segEvent, segBelow);
                    if ((intersectionPoint != null) && (intersectionPoint.getCoordX() >= currentEvent.getPoint().getCoordX())) {
                        Event e = new Event(Event.EventType.INTERSECTION, intersectionPoint,segBelow, segEvent);
                        eventQueue.add(e); //first one always below second one. For later swapping
                    }
                }
            }
            else if(currentEvent.getType() == Event.EventType.END){
                //REMOVE SEGMENT
                LineSegment segEvent = currentEvent.getSegment();
                LineSegment segAbove = null;
                LineSegment segBelow = null;
                int index = listSweepLine.indexOf(segEvent);
                if(index < listSweepLine.size()-1)
                    segAbove = listSweepLine.get(index+1);
                if((index) > 0)
                    segBelow = listSweepLine.get(index-1);

                listSweepLine.remove(segEvent);

               //LineSegment segAbove = segmentOrder.higher(segEvent);
               //LineSegment segBelow = segmentOrder.lower(segEvent);
               /* if(!segmentOrder.remove(segEvent)) {
                    System.out.println(segEvent);
                    //System.out.println(segBelow);
                    System.out.println(segmentOrder.toString());
                }*/

                if(segAbove != null && segBelow != null) {
                    Point intersectionPoint = calcIntersect.intersectingWithEquations(segAbove, segBelow);
                    if ((intersectionPoint != null) && (intersectionPoint.getCoordX() >= currentEvent.getPoint().getCoordX())) {
                        //Only add if intersection point is not already in queue
                        //TreeSet is taking care of and only adding if not in queue
                        Event e = new Event(Event.EventType.INTERSECTION, intersectionPoint,segBelow, segAbove);
                        if(!output.contains(e))
                            eventQueue.add(e);
                    }
                }
            }
            else{ //INTERSECTION event
                //SWAP INTERSECTING SEGMENTS
                output.add(currentEvent);
                LineSegment segE1 = currentEvent.getSegmentIntersection(); //Above
                LineSegment segE2 = currentEvent.getSegment(); //Below

                int index = listSweepLine.indexOf(segE2);
                listSweepLine.remove(segE2);
                listSweepLine.add(index+1, segE2);

                //Swapping not possible in TreeSet
                //Changing start point of one segment to other side for switching

                //segmentOrder.remove(segE2);
                //segmentOrder.remove(segE1);
                /*if(segE2.getStartPoint().getCoordY()==segE2.getEndPoint().getCoordY()){
                   // double x = ((segE1.getEndPoint().getCoordX() - currentEvent.getPoint().getCoordX())*0.01);
                    //double y = ((segE1.getEndPoint().getCoordY() - currentEvent.getPoint().getCoordY())*0.01);
                    //Point k = new Point(currentEvent.getPoint().getCoordX()+x, currentEvent.getPoint().getCoordY()+y);
                    segE2.setStartPoint(currentEvent.getPoint());
                    segE1.setStartPoint(currentEvent.getPoint());
                }
                else{
                    //double x = ((segE2.getEndPoint().getCoordX() - currentEvent.getPoint().getCoordX())*0.01);
                    //double y = ((segE2.getEndPoint().getCoordY() - currentEvent.getPoint().getCoordY())*0.01);
                    //Point k = new Point(currentEvent.getPoint().getCoordX()+x, currentEvent.getPoint().getCoordY()+y);
                    segE2.setStartPoint(currentEvent.getPoint());
                    segE1.setStartPoint(currentEvent.getPoint());
                }

                //Point k = new Point(currentEvent.getPoint().getCoordX()+x, currentEvent.getPoint().getCoordY()+y);
                //segE2.setStartPoint(k);
                //segE1.setStartPoint(currentEvent.getPoint());
                segmentOrder.add(segE2);
                segmentOrder.add(segE1);*/

                //Checking for new intersection
                LineSegment segAbove = null;
                LineSegment segBelow = null;
                index = listSweepLine.indexOf(segE2);
                if(index < listSweepLine.size()-1) {
                    segAbove = listSweepLine.get(index + 1);
                }
                if((index-2) >= 0) {
                    segBelow = listSweepLine.get(index - 2);
                }



                //LineSegment segAbove = segmentOrder.higher(segE2);
                //LineSegment segBelow = segmentOrder.lower(segE1);
                if(segAbove != null) {
                    Point intersectionPoint = calcIntersect.intersectingWithEquations(segE2, segAbove);
                    if (intersectionPoint != null) {
                        Event e = new Event(Event.EventType.INTERSECTION, intersectionPoint,segE2, segAbove);
                        if(!currentEvent.equals(e) && (intersectionPoint.getCoordX() >= currentEvent.getPoint().getCoordX())){
                            if(!output.contains(e)) {
                                eventQueue.add(e);
                            }
                        }
                    }
                }
                if(segBelow != null){
                    Point intersectionPoint = calcIntersect.intersectingWithEquations(segE1, segBelow);
                    if (intersectionPoint != null) {
                        Event e = new Event(Event.EventType.INTERSECTION, intersectionPoint,segBelow, segE1); //first one always below second one. For later swapping
                        if(!currentEvent.equals(e) && (intersectionPoint.getCoordX() >= currentEvent.getPoint().getCoordX())) {
                            if(!output.contains(e)) {
                                eventQueue.add(e);
                            }
                        }
                    }
                }
            }
        }
/*
        String d = "";
        for(LineSegment s : listSweepLine){
            d += s.getName() + ", ";
        }
        System.out.println("Order: " + d);*/

        return output;

    }

    private TreeSet<Event> init (List<Point> list){
        TreeSet<Event> e = new TreeSet<>(new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.compareTo(o2);
            }
        });
        LineSegment seg;
        for(int i=0; i<list.size(); i=i+2){
            if(list.get(i).getCoordX() < list.get(i+1).getCoordX())
                seg = new LineSegment(list.get(i), list.get(i+1), "L"+i);
            else if(list.get(i).getCoordX() > list.get(i+1).getCoordX())
                seg = new LineSegment(list.get(i+1), list.get(i), "L"+i);
            else{
                //x is same
                if(list.get(i).getCoordY() < list.get(i+1).getCoordY())
                    seg = new LineSegment(list.get(i), list.get(i+1), "L"+i);
                else
                    seg = new LineSegment(list.get(i+1), list.get(i), "L"+i);
            }

            e.add(new Event(Event.EventType.START,seg.getStartPoint(),seg, null));
            e.add(new Event(Event.EventType.END, seg.getEndPoint(),seg, null));
        }
        return e;
    }
}
