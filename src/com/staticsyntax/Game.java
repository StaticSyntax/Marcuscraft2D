package com.staticsyntax;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;
import com.staticsyntax.marcuscraft.input.KeyManager;
import com.staticsyntax.marcuscraft.input.MouseManager;
import com.staticsyntax.states.GameState;
import com.staticsyntax.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private static final boolean DEV_MODE = true;
    private static final String TITLE = "Marcuscraft 2D";
    private static final String VERSION = "0.4 ALPHA";
    private int achievedfps;

    private int width, height;
    private boolean running;
    private Thread gameThread;
    private Display display;
    private BufferStrategy bufferStrat;
    private KeyManager keyManager = new KeyManager();
    private MouseManager mouseManager = new MouseManager();

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True"); //-Dsun.java2d.opengl=true
        Game game = new Game(1280, 720);
        game.start();
    }

    public Game(int width, int height) {
        this.width = width;
        this.height = height;

        display = new Display(width, height, TITLE + " " + VERSION);
        display.getFrame().addKeyListener(keyManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        bufferStrat = display.getCanvas().getBufferStrategy();

        Assets.init();
        Handler.setGame(this);
        Handler.setKeyManager(keyManager);
        Handler.setMouseManager(mouseManager);
        State.setCurrentState(new GameState());
    }

    public void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        running = false;
        try {
            gameThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(State.getCurrentState() != null) {
            State.getCurrentState().update();
        }
        keyManager.update();
        mouseManager.update();
        developerStats();
    }

    public void render() {
        if(State.getCurrentState() != null) {
            Graphics g = bufferStrat.getDrawGraphics();
            g.clearRect(0, 0, width, height);

            State.getCurrentState().render(g);

            bufferStrat.show();
            g.dispose();
        }
    }

    @Override
    public void run() {
        int fps = 60;
        double timePerTick = 1_000_000_000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        long timer = 0; //FPS Tracking.
        int ticks = 0; //FPS Tracking.

        while(running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime; //FPS Tracking.
            lastTime = now;

            if(delta >= 1) {
                update();
                render();
                ticks++; //FPS Tracking.
                delta--;
            }

            //FPS Tracking.
            if(timer >= 1_000_000_000) {
                achievedfps = ticks;
                ticks = 0;
                timer = 0;
            }
        }
    }

    private void developerStats() {
        if(DEV_MODE) {
            display.getFrame().setTitle(TITLE + " " + VERSION + " [ " + "FPS: " + achievedfps + " | Blocks Rendered: " + Handler.getBlocksRendered() + " ]");
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}