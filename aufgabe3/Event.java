package aufgabe3;

import aufgabe1.Point;

import java.util.TreeSet;

public class Event {

    /**
     * Enum representing the type of the event
     */
    public static enum EventType{
        START,
        END,
        INTERSECTION
    }

    /**
     * The type of the Event
     */
    public final EventType type;

    /**
     * The point for this event. Start, end or intersection point.
     */
    public final Point point;

    /**
     * The line segment of this event.
     */
    public final LineSegment segment;

    /**
     * The second line secment of this event. Always null, except for intersection.
     */
    public final LineSegment segmentIntersection;

    /**
     * Constructor for new Event
     * @param type
     * @param point
     * @param segment
     * @param segmentIntersection
     */
    public Event(EventType type, Point point, LineSegment segment, LineSegment segmentIntersection){
        this.type = type;
        this.point = point;
        this.segment = segment;
        this.segmentIntersection = segmentIntersection;
    }

    public EventType getType() {
        return type;
    }

    public Point getPoint() {
        return point;
    }

    public LineSegment getSegment() {
        return segment;
    }

    public LineSegment getSegmentIntersection() {
        return segmentIntersection;
    }

    public int compareTo(Event that){
        double thisX = (long)(this.point.getCoordX() * 1e5) /1e5;
        double thisY = (long)(this.point.getCoordY() * 1e5) /1e5;
        double thatX = (long)(that.point.getCoordX() * 1e5) /1e5;
        double thatY = (long)(that.point.getCoordY() * 1e5) /1e5;

        if(this == that || this.equals(that)){
            return 0;
        }
        else if(thisX < thatX){
            return -1;
        }
        else if(thisX > thatX){
            return 1;
        }
        //if X is the same, sort by Y
        else if(thisY < thatY){
            return -1;
        }
        else if(thisY > thatY){
            return 1;
        }

        //return one, to allow same points of different segments (names)
        //x and y are the same, sort by start -> end to accept also two one point segments
        if(this.getType().equals(EventType.START) && that.getType().equals(EventType.START))
            return -1;
        if(this.getType().equals(EventType.INTERSECTION) && that.getType().equals(EventType.END))
            return -1;
        return 1;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Event that = (Event) o;
        if((this.segmentIntersection != null) && (that.segmentIntersection != null)) {
            if ((this.type.equals(that.type) && this.point.equals(that.point))) {
                if((this.segment.equals(that.segment) && (this.segmentIntersection.equals(that.segmentIntersection))) || (this.segment.equals(that.segmentIntersection) && (this.segmentIntersection.equals(that.segment)))) {
                    return true;
                }
            }
        }
        else if((this.segmentIntersection == null) && (that.segmentIntersection == null)){
            if ((this.type.equals(that.type) && this.point.equals(that.point) && this.segment.equals(that.segment))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        if(segmentIntersection != null)
            return "["+this.type + " " + this.segment.getName()+this.segmentIntersection.getName() + ", " + this.point.toString() + ", " + this.segment.toString() + ", " + this.segmentIntersection.toString() +"]";
        else
            return "["+this.type + " " + this.segment.getName() + ", " + this.point.toString() + ", " + this.segment.toString() + "]";
    }
}
