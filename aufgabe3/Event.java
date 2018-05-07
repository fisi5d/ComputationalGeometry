package aufgabe3;

import aufgabe1.Point;

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
     * The line segment of this event. Null if intersection
     */
    public final LineSegment segment;

    /**
     * Constructor for new Event
     * @param type
     * @param point
     * @param segment
     */
    public Event(EventType type, Point point, LineSegment segment){
        this.type = type;
        this.point = point;
        this.segment = segment;
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

    public int compareTo(Event that){
        if(this == that || this.equals(that)){
            return 0;
        }
        else if(this.point.getCoordX() < that.point.getCoordX()){
            return -1;
        }
        else if(this.point.getCoordX() > that.point.getCoordX()){
            return 1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Event that = (Event) o;
        if ((this.type.equals(that.type) && this.point.equals(that.point) && this.segment.equals(that.segment))) {
            return true;
        }
        return false;
    }
}
