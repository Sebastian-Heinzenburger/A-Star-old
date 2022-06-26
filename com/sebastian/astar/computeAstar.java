package com.sebastian.astar;

import com.sebastian.gui.*;

import java.util.ArrayList;

public class computeAstar
{
    public static ArrayList<Box> AllWayPoints;

    public static void compAndColor() {}

    public static void setValues(Box Parent, Box b) {
        b.Svalue = Math.sqrt(Math.pow(Math.abs(b.getX() - Parent.getX()), 2.0D) + Math.pow(Math.abs(b.getY() - Parent.getY()), 2.0D));
        b.Evalue = Math.sqrt(Math.pow(Math.abs(b.getX() - getEnd().getX()), 2.0D) + Math.pow(Math.abs(b.getY() - getEnd().getY()), 2.0D));
        b.value = b.Svalue + b.Evalue;
    }



    private static Box getBoxAt(int x, int y) {
        for (Box b : Panel.boxes) {
            if (b.getDrawX() <= x && b.getDrawX() + b.getSize() >= x &&
                    b.getDrawY() <= y && b.getDrawY() + b.getSize() >= y)
            {
                return b;
            }
        }

        return null;
    }

    public static void setObstacle(int x, int y) {
        Box bx = getBoxAt(x, y);
        bx.setState(Box.STATE.OBSTACLE);
    }

    public static void setStart(int x, int y) {
        Box target = getBoxAt(x, y);
        Box oldStart = getStart();
        if (target != null) {
            target.setState(Box.STATE.START);
            if (oldStart != null) { oldStart.setState(Box.STATE.NONE);
                AllWayPoints = new ArrayList<Box>();

                for (Box b : Panel.boxes) {
                    if (b.getState() == Box.STATE.BESTWAYPOINT || b.getState() == Box.STATE.WAYPOINT) b.setState(Box.STATE.NONE);
                    b.value = 0.0D;
                    b.Svalue = 0.0D;
                    b.Evalue = 0.0D;
                    b.wasnice = false;
                    b.closed = false;
                    b.parent = null;
                }  }

            if (isAllReady()) startFinding();

        }
    }

    private static Box getStart() {
        for (Box b : Panel.boxes) {
            if (b.getState() == Box.STATE.START) {
                return b;
            }
        }
        return null;
    }

    public static void setEnd(int x, int y) {
        Box target = getBoxAt(x, y);
        Box oldEnd = getEnd();
        if (target != null) {
            target.setState(Box.STATE.END);
            if (oldEnd != null) { oldEnd.setState(Box.STATE.NONE);
                AllWayPoints = new ArrayList<Box>();

                for (Box b : Panel.boxes) {
                    if (b.getState() == Box.STATE.BESTWAYPOINT || b.getState() == Box.STATE.WAYPOINT) b.setState(Box.STATE.NONE);
                    b.value = 0.0D;
                    b.Svalue = 0.0D;
                    b.Evalue = 0.0D;
                    b.wasnice = false;
                    b.closed = false;
                    b.parent = null;
                }  }
            if (isAllReady()) startFinding();

        }
    }

    private static Box getEnd() {
        for (Box b : Panel.boxes) {
            if (b.getState() == Box.STATE.END) {
                return b;
            }
        }
        return null;
    }


    private static boolean isAllReady() { return (getStart() != null && getEnd() != null); }


    private static void startFinding() {
        Box next = getStart();
        AllWayPoints = new ArrayList<Box>();
        boolean stopped = false;
        for (int i = 0; i < 1000 && !stopped; i++) {

            for (Box b : getNeighboursOf(next)) {
                setValues(next, b);
                if (b.getState() == Box.STATE.END) { stopped = true;






                }

                else if (b.getState() != Box.STATE.START) { b.setState(Box.STATE.WAYPOINT); }
                next.wasnice = true;
            }

            AllWayPoints.addAll(getNeighboursOf(next));
            next = boxWithSmallestValueOf(AllWayPoints);
        }
    }

    private static Box boxWithSmallestValueOf(ArrayList<Box> AllWp) {
        double smallestValue = 99999.0D;
        Box smallestBox = new Box();


        for (Box b : AllWp) {
            if (b.value < smallestValue && !b.closed) {
                smallestValue = b.value;
                smallestBox = b;
            }
        }

        return smallestBox;
    }

    private static ArrayList<Box> getNeighboursOf(Box b) {
        Box oben = getBoxAt(b.getX(), b.getY() - b.getSize());
        Box unten = getBoxAt(b.getX(), b.getY() + b.getSize() + 11);
        Box links = getBoxAt(b.getX() - b.getSize(), b.getY());
        Box rechts = getBoxAt(b.getX() + b.getSize(), b.getY());
        ArrayList<Box> nb = new ArrayList<Box>();
        if (oben != null && oben.getState() != Box.STATE.OBSTACLE) nb.add(oben);
        if (unten != null && unten.getState() != Box.STATE.OBSTACLE) nb.add(unten);
        if (rechts != null && rechts.getState() != Box.STATE.OBSTACLE) nb.add(rechts);
        if (links != null && links.getState() != Box.STATE.OBSTACLE) nb.add(links);

        for (Box b2 : nb) {
            b2.parent = b;
        }
        b.closed = true;
        return nb;
    }

    private static ArrayList<Box> getClosedNeighboursOf(Box b) {
        Box oben = getBoxAt(b.getX(), b.getY() - b.getSize());
        Box unten = getBoxAt(b.getX(), b.getY() + b.getSize() + 11);
        Box links = getBoxAt(b.getX() - b.getSize(), b.getY());
        Box rechts = getBoxAt(b.getX() + b.getSize(), b.getY());
        ArrayList<Box> nb = new ArrayList<Box>();
        if (oben != null && oben.getState() != Box.STATE.OBSTACLE) nb.add(oben);
        if (unten != null && unten.getState() != Box.STATE.OBSTACLE) nb.add(unten);
        if (rechts != null && rechts.getState() != Box.STATE.OBSTACLE) nb.add(rechts);
        if (links != null && links.getState() != Box.STATE.OBSTACLE) nb.add(links);

        b.closed = true;
        return nb;
    }


    private static void colorPath(Box LastOne, int lenght) {
        LastOne.setState(Box.STATE.BESTWAYPOINT);
        Box f = LastOne;
        for (int i = 0; i < lenght; i++) {

            System.out.println(i + "/" + lenght);

            for (Box e : getClosedNeighboursOf(f)) {
                f.parent.setState(Box.STATE.BESTWAYPOINT);
                if (e.closed && e.getState() == Box.STATE.WAYPOINT) {
                    System.out.println(f.wasnice);
                    f = e;
                }
            }
        }
    }
}
