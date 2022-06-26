package com.sebastian.astar;

public class Box {
    private int x;
    private int y;
    public static int size = 35;
    public enum STATE { START, END, WAYPOINT, OBSTACLE, NONE, BESTWAYPOINT; }
    private STATE state = STATE.NONE;
    public Box parent;
    public double value = 0.0D;
    public double Svalue = 0.0D;
    public double Evalue = 0.0D;
    public boolean wasnice = false;
    public boolean closed = false;

    public Box() {}

    public Box(int x, int y, STATE _state) {
        this.x = x;
        this.y = y;
        this.state = _state;
    }

    public int getSize() { return size; }

    public int getX() { return this.x; }

    public int getY() { return this.y; }

    public int getDrawX() { return this.x - size / 2; }

    public int getDrawY() { return this.y - size / 2; }

    public STATE getState() { return this.state; }

    public void setState(STATE newState) { this.state = newState; }
}
