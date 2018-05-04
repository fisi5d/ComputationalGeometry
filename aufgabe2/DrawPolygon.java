package aufgabe2;

import aufgabe1.Point;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPolygon {

    private JFrame mainMap;
    private Polygon poly;

    public DrawPolygon(List<Point> polygons) {

        initComponents(polygons);

    }

    private void initComponents(List<Point> polygons) {

        mainMap = new JFrame();
        mainMap.setResizable(true);

        mainMap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        List<Integer> x = new LinkedList<>();
        List<Integer> y = new LinkedList<>();
        //for(int s=0; s<polygons.size(); s++){
            for(int i=0; i<polygons.size(); i++){
                x.add((int)polygons.get(i).getCoordX());
                y.add((int)polygons.get(i).getCoordY());
            }
            int[] xPoly = x.stream().mapToInt(i->i).toArray();
            int[] yPoly = y.stream().mapToInt(i->i).toArray();

            poly = new Polygon((int[])xPoly, yPoly, xPoly.length);
            JPanel p = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.BLUE);
                    g.drawPolygon(poly);
                }

                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(800, 600);
                }
            };
            mainMap.add(p);
            x.clear();
            y.clear();
       // }
        mainMap.pack();
        mainMap.setVisible(true);

    }
}
