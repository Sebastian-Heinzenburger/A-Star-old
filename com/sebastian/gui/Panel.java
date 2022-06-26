package com.sebastian.gui;
import com.sebastian.astar.Box;
import com.sebastian.astar.computeAstar;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Panel extends JPanel {
    public static ArrayList<Box> boxes = new ArrayList<Box>();


    Panel() {
        setBackground(Color.black);
        addListeners();
    }

    private void addListeners() {
        requestFocusInWindow();
        addMouseListener(new MouseListener()
        {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3) computeAstar.setEnd(e.getX(), e.getY());

                if (e.getButton() == 1) computeAstar.setStart(e.getX(), e.getY());
                Panel.this.repaint();
            }





            public void mousePressed(MouseEvent e) {}




            public void mouseReleased(MouseEvent e) {}




            public void mouseEntered(MouseEvent e) {}




            public void mouseExited(MouseEvent e) {}
        });
        addMouseMotionListener(new MouseMotionListener()
        {
            public void mouseDragged(MouseEvent e) {
                try {
                    if (SwingUtilities.isMiddleMouseButton(e))
                        computeAstar.setObstacle(e.getX(), e.getY());
                } catch (Exception ex) {
                    System.out.println("");
                }
            }



            public void mouseMoved(MouseEvent e) {}
        });
    }



    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.lightGray);
        int size = Box.size;
        for (int y = 0; y < getRootPane().getHeight(); y = y + size + 10) {
            int x; for (x = 0; x < getRootPane().getWidth(); x = x + size + 10) {
                boolean boxAllreadyThere = false;
                for (Box b : boxes) {
                    if (b.getDrawX() == x && b.getDrawY() == y) boxAllreadyThere = true;
                }
                if (!boxAllreadyThere) boxes.add(new Box(x + size / 2, y + size / 2, Box.STATE.NONE));
            }
        }
        computeAstar.compAndColor();
        for (Box b : boxes) {
            if (b.getState() == Box.STATE.START) g.setColor(Color.BLUE);
            if (b.getState() == Box.STATE.END) g.setColor(Color.RED);
            if (b.getState() == Box.STATE.OBSTACLE) g.setColor(Color.darkGray);
            if (b.getState() == Box.STATE.WAYPOINT) { g.setColor(Color.GREEN); if (b.closed) g.setColor(Color.orange);  }
            if (b.getState() == Box.STATE.WAYPOINT && b.wasnice) g.setColor(Color.CYAN);
            if (b.getState() == Box.STATE.BESTWAYPOINT) g.setColor(Color.YELLOW);
            g.fillRect(b.getDrawX(), b.getDrawY(), size, size);
            if (b.getState() == Box.STATE.WAYPOINT) { g.setColor(Color.magenta); g.drawString("" + (int)(b.Svalue + b.value), b.getDrawX(), (int)(b.getDrawY() + size * 0.2D)); }
            if (b.getState() == Box.STATE.WAYPOINT) { g.setColor(Color.magenta); g.drawString("" + (int)b.Evalue, b.getDrawX() + size - (int)(size * 0.5D), (int)(b.getDrawY() + size * 0.92D)); }
            if (b.getState() == Box.STATE.WAYPOINT) { g.setColor(Color.black); g.drawString("" + (int)b.value, b.getDrawX() + (int)(size * 0.2D), (int)(b.getDrawY() + size * 0.6D)); }

            g.setColor(Color.lightGray);
        }
    }
}
