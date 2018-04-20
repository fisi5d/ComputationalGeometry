package aufgabe1;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class DrawLines extends JFrame{
    private Point p1,p2,q1,q2;

    public DrawLines(Point p1, Point p2, Point q1, Point q2){
        super(p1.toString() + p2.toString() + q1.toString() + q2.toString());
        setSize(480, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.p1 = p1;
        this.p2 = p2;
        this.q1 = q1;
        this.q2 = q2;
    }

    void drawLines(Graphics g) {
        double zoom = 500;
        int offset = 100;
        Stroke stroke = new BasicStroke(2f);
        Graphics2D g2d = (Graphics2D) g;
        //g2d.scale(2.0,2.0);
        double px1 = (p1.getCoordX()*zoom);
        double px2 = (p2.getCoordX()*zoom);
        double qx1 = (q1.getCoordX()*zoom);
        double qx2 = (q2.getCoordX()*zoom);

        double py1 = (p1.getCoordY()*zoom);
        double py2 = (p2.getCoordY()*zoom);
        double qy1 = (q1.getCoordY()*zoom);
        double qy2 = (q2.getCoordY()*zoom);

        double minX = Math.min(px1, px2);
        minX = Math.min(minX,qx1);
        minX = Math.min(minX,qx2);

        double minY = Math.min(py1, py2);
        minX = Math.min(minX,qy1);
        minX = Math.min(minX,qy2);

        px1 = px1-minX+offset;
        py1 = py1-minY+offset;
        px2 = px2-minX+offset;
        py2 = py2-minY+offset;

        qx1 = qx1-minX+offset;
        qy1 = qy1-minY+offset;
        qx2 = qx2-minX+offset;
        qy2 = qy2-minY+offset;

        g2d.setStroke(stroke);
        g2d.draw(new Line2D.Double(px1, py1, px2, py2));
        g2d.draw(new Line2D.Double(qx1, qy1, qx2, qy2));

        g2d.setColor(Color.RED);
        g2d.drawOval(Math.toIntExact(Math.round(px1)), Math.toIntExact(Math.round(py1)), 5,5);
        g2d.drawOval(Math.toIntExact(Math.round(px2)), Math.toIntExact(Math.round(py2)), 5,5);

        g2d.setColor(Color.BLUE);
        g2d.drawOval(Math.toIntExact(Math.round(qx1)), Math.toIntExact(Math.round(qy1)), 5,5);
        g2d.drawOval(Math.toIntExact(Math.round(qx2)), Math.toIntExact(Math.round(qy2)), 5,5);

    }

    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }
}
