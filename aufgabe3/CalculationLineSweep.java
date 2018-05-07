package aufgabe3;

import aufgabe1.Calculations;
import aufgabe1.Point;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class CalculationLineSweep {
    /**
     * TreeSet because this implementation provides guaranteed log(n) time cost for the basic operations (add, remove and contains)
     */
    TreeSet<Event> eventQueue;
    TreeSet<LineSegment> segmentOrder = new TreeSet<>(new Comparator<LineSegment>() {
        @Override
        public int compare(LineSegment o1, LineSegment o2) {
            if(o1.equals(02))
                return 0;
            else if(o1.getStartPoint().getCoordY() < o2.getStartPoint().getCoordY())
                return -1;
            else if(o1.getStartPoint().getCoordY() > o2.getStartPoint().getCoordY())
                return 1;

            return 0;
        }
    });
    List<Event> output = new LinkedList<>();
    Calculations calcIntersect = new Calculations();

    public List<Event> lineSweep(List<Point> list){
        eventQueue = init(list);


        Point currentEventPoint;
        LineSegment sweepLine;

        while(!eventQueue.isEmpty()){
            Event currentEvent = eventQueue.pollFirst(); //Retrieves and removes the first element
            if(currentEvent.getType() == Event.EventType.START){
                //ADD NEW SEGMENT
                LineSegment segEvent = currentEvent.getSegment();
                segmentOrder.add(segEvent);
                LineSegment segAbove = segmentOrder.higher(segEvent);
                LineSegment segBelow = segmentOrder.lower(segEvent);
                if(calcIntersect.intersectingWithCCW(segEvent, segAbove)){
                    //eventQueue.add(new Event(Event.EventType.INTERSECTION, new Point(),null))
                }
                if(calcIntersect.intersectingWithCCW(segEvent, segBelow)){
                    //eventQueue.add(new Event(Event.EventType.INTERSECTION, new Point(),null))
                }
            }
            else if(currentEvent.getType() == Event.EventType.END){
                //REMOVE SEGMENT
                LineSegment segEvent = currentEvent.getSegment();
                LineSegment segAbove = segmentOrder.higher(segEvent);
                LineSegment segBelow = segmentOrder.lower(segEvent);
                segmentOrder.remove(segEvent);
                if(calcIntersect.intersectingWithCCW(segAbove, segBelow)){
                    //If(I is not in eventQueue already) Insert I into EventQueue;
                }
            }
            else{ //INTERSECTION event
                //SWAP INTERSECTING SEGMENTS
                output.add(currentEvent);
                LineSegment segE1
                segmentOrder.
            }
        }


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
            seg = new LineSegment(list.get(i), list.get(i+1));
            e.add(new Event(Event.EventType.START,seg.getStartPoint(),seg));
            e.add(new Event(Event.EventType.END, seg.getEndPoint(),seg));
        }

        return e;
    }
}
