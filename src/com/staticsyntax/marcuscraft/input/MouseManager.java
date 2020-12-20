package com.staticsyntax.marcuscraft.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {

    private int x, y, coolDown;
    private boolean leftClicked, rightClicked;

    public void update() {
        coolDown++;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case 1:
                leftClicked = true;
                break;
            case 3:
                rightClicked = true;
                break;
            default:
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case 1:
                leftClicked = false;
                break;
            case 3:
                rightClicked = false;
                break;
            default:
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateMousePosition(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        updateMousePosition(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    private void updateMousePosition(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLeftClicked() {
        return leftClicked;
    }

    public boolean isRightClicked() {
        return rightClicked;
    }

    public boolean justLeftClicked() {
        if(leftClicked && coolDown >= 10) {
            coolDown = 0;
            return true;
        }
        return false;
    }

    public boolean justRightClicked() {
        if(rightClicked && coolDown >= 10) {
            coolDown = 0;
            return true;
        }
        return false;
    }
}
