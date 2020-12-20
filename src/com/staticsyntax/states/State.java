package com.staticsyntax.states;

import java.awt.*;

public abstract class State {

    protected static State currentState = null;

    public static void setCurrentState(State state) {
        currentState = state;
    }

    public static State getCurrentState() {
        return currentState;
    }

    public abstract void update();
    public abstract void render(Graphics g);
}
