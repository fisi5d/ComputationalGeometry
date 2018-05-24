package aufgabe3;

import aufgabe1.Calculations;
import aufgabe1.Point;

import java.util.*;

public class CalculationLineSweep {
    //Object for intersection calculation
    Calculations calcIntersect = new Calculations();
    //Sweep Line List
    List<LineSegment> listSweepLine = new LinkedList<>();

    /**
     * Adds a Line Segment to the global sweep line.
     * Sorts the segments based on their Y-value to the sweep line from low to high.
     *
     * @param seg the segment to insert
     * @return position of the added element
     */
    private int addSegmentIntoSweep(LineSegment seg){
        //insert first element.
        if(listSweepLine.isEmpty()) {
            listSweepLine.add(seg);
            return 0;
        }
        else{

            for(int i=0; i<listSweepLine.size(); i++) {
                //Calculating the "intersection point" with the sweep line
                double x = (listSweepLine.get(i).getEndPoint().getCoordX() - listSweepLine.get(i).getStartPoint().getCoordX());
                double y = (listSweepLine.get(i).getEndPoint().getCoordY() - listSweepLine.get(i).getStartPoint().getCoordY());
                double dif = Math.abs(listSweepLine.get(i).getStartPoint().getCoordX() - seg.getStartPoint().getCoordX());
                double difX = dif / x;
                double intersectSweepY = ((difX * y) + listSweepLine.get(i).getStartPoint().getCoordY());

                //Cutting to by the 5th comma decimal
                double o1StartY = (long) (seg.getStartPoint().getCoordY() * 1e5) / 1e5;
                double o1EndY = (long) (seg.getEndPoint().getCoordY() * 1e5) / 1e5;
                double o2EndY = (long) (listSweepLine.get(i).getEndPoint().getCoordY() * 1e5) / 1e5;
                double intY = (long) (intersectSweepY * 1e5) / 1e5;

                //if seg is lower than current in list, insert at the specific point and shift all others to the right
                if (o1StartY < intY) {
                    listSweepLine.add(i, seg);
                    return i;
                }else if (o1StartY > intY) {
                    //do nothing wait till smaller
                }else {
                    //y start the same. Sort by endpoints
                    if (o1EndY < o2EndY) {
                        listSweepLine.add(i, seg);
                        return i;
                    }
                }
            }
            //seg is the highest one. insert at the end
            listSweepLine.add(seg);
            return listSweepLine.size()-1;
        }
    }

    public List<Event> lineSweep(List<Point> list){
        /**
         * TreeSet because this implementation provides guaranteed log(n) time cost for the basic operations (add, remove and contains)
         */
        //Event Queue
        TreeSet<Event> eventQueue;
        //Output list
        List<Event> output = new LinkedList<>();
        eventQueue = init(list);

        //Logic of sweep line
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

                //Get higher and lower element if possible
                LineSegment segEvent = currentEvent.getSegment();
                int index = addSegmentIntoSweep(segEvent);
                LineSegment segAbove = null;
                LineSegment segBelow = null;
                if(index < listSweepLine.size()-1){
                    segAbove = listSweepLine.get(index+1);
                }
                if((index) > 0) {
                    segBelow = listSweepLine.get(index - 1);
                }

                //Calculate possible intersection point with new neighbors
                //Insert only new Intersection if it is in the "future"
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

                //Get higher and lowe element if possible
                LineSegment segEvent = currentEvent.getSegment();
                LineSegment segAbove = null;
                LineSegment segBelow = null;
                int index = listSweepLine.indexOf(segEvent);
                if(index < listSweepLine.size()-1)
                    segAbove = listSweepLine.get(index+1);
                if((index) > 0)
                    segBelow = listSweepLine.get(index-1);

                //remove element
                listSweepLine.remove(segEvent);

                //Calculates intersection point with new neighbors
                if(segAbove != null && segBelow != null) {
                    Point intersectionPoint = calcIntersect.intersectingWithEquations(segAbove, segBelow);
                    if ((intersectionPoint != null) && (intersectionPoint.getCoordX() >= currentEvent.getPoint().getCoordX())) {
                        //Only add if intersection point is not already in output list and in the "future"
                        //TreeSet is taking care of and only adding if not in queue
                        Event e = new Event(Event.EventType.INTERSECTION, intersectionPoint,segBelow, segAbove);
                        if(!output.contains(e))
                            eventQueue.add(e);
                    }
                }
            }
            else{
                //INTERSECTION event
                //SWAP INTERSECTING SEGMENTS

                //insert into output
                output.add(currentEvent);

                //get intersection elements for swapping
                LineSegment segE1 = currentEvent.getSegmentIntersection(); //Above
                LineSegment segE2 = currentEvent.getSegment(); //Below

                //swap elements by adding directly into specific position
                int index = listSweepLine.indexOf(segE2);
                listSweepLine.remove(segE2);
                listSweepLine.add(index+1, segE2);


                //Getting new higher and lower neighbors
                LineSegment segAbove = null;
                LineSegment segBelow = null;
                index = listSweepLine.indexOf(segE2);
                if(index < listSweepLine.size()-1) {
                    segAbove = listSweepLine.get(index + 1);
                }
                if((index-2) >= 0) {
                    segBelow = listSweepLine.get(index - 2);
                }

                //Checking for new intersections
                //Only insert if it is not the current element, in the "future" and not already in output
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

    /**
     * Initialize the event queue Tree Set.
     * Creates based on the x,y values of the points the START and END Events
     * and sorts it low->high into the queue.
     * @param list with all points
     * @return sorted event queue
     */
    private TreeSet<Event> init (List<Point> list){
        TreeSet<Event> e = new TreeSet<>(new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.compareTo(o2);
            }
        });
        LineSegment seg;
        for(int i=0; i<list.size(); i=i+2){
            //Creates line segments with start and endpoint based on their value
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

            //Creates Events
            e.add(new Event(Event.EventType.START,seg.getStartPoint(),seg, null));
            e.add(new Event(Event.EventType.END, seg.getEndPoint(),seg, null));
        }
        return e;
    }
}
