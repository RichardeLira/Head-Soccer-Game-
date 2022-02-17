package jogo.src.main;

import jogo.src.display.Display;

import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Color;
import jogo.src.states.StateManager;

public class Game implements Runnable {

    private Thread thread;
    private boolean isRunning;
    private BufferedImage image;

    // FINAL CONSTANTES
    private static final int WIDTH = 340;
    private static final int HEIGHT = 180;
    private static final int SCALE = 3;
    private Display display;
    private StateManager SM;

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public Game() {
        display = new Display("Jogo", WIDTH * SCALE, HEIGHT * SCALE);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        SM = new StateManager();
        display.setKeyListener(SM);
        StateManager.setState(0);
    }

    public void tick() {
        SM.tick();
    }

    public void render() {
        BufferStrategy bs = display.getBufferedStrategy();
        if (bs == null) {
            display.createBufferStrategy();
            bs = display.getBufferedStrategy();
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(153, 153, 255));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        SM.render(g);

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        bs.show();

    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                frames++;
                delta = 0;
            }
            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }
        stop();
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getScale() {
        return SCALE;
    }
}
