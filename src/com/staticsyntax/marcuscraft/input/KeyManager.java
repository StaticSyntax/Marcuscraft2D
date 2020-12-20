package com.staticsyntax.marcuscraft.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys = new boolean[768];
    private boolean[] justPressed = new boolean[keys.length];
    private boolean[] cantPress = new boolean[keys.length];
    public boolean w, a, s, d, e, space, shift;
    public boolean[] numKeys = new boolean[9];

    public void update() {
        for(int i = 0; i < keys.length; i++) {
            if(cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else if(justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if(!cantPress[i] && keys[i]) {
                justPressed[i] = true;
            }
        }

        a = keys[KeyEvent.VK_A];
        d = keys[KeyEvent.VK_D];
        w = keys[KeyEvent.VK_W];
        s = keys[KeyEvent.VK_S];
        e = keys[KeyEvent.VK_E];
        space = keys[KeyEvent.VK_SPACE];
        shift = keys[KeyEvent.VK_SHIFT];

        //KeyCodes for number keys 1-9 are 59-57.
        for(int k = 49; k <= 57; k++) {
            numKeys[k - 49] = keys[k];
        }
    }

    public boolean keyJustPressed(int keyCode) {
        return justPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
