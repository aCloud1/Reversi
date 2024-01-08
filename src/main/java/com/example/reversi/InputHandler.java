package com.example.reversi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements MouseListener {
    int x, y;
    boolean got_input = false;

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getY();
        y = e.getX();
        got_input = true;
    }

    public boolean hasInput() { return got_input; }
    public void inputHandled() { got_input = false; }

    public Point getCoordinatesAsPoint() {
        return new Point(x, y);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
