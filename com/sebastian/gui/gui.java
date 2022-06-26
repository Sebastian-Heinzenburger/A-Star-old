package com.sebastian.gui;

import javax.swing.JFrame;

public class gui
{
    public static JFrame Frame;

    public static void gui() {
        Frame = new JFrame("A* Pathfinding");
        Frame.setDefaultCloseOperation(3);
        Frame.setSize(800, 450);
        Frame.add(new Panel());
        Frame.setVisible(true);
    }
}
