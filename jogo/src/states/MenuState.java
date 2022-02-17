package jogo.src.states;

import java.awt.Graphics;

import jogo.src.main.Game;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MenuState implements State {

    private int choice = 0;
    private String[] options = { "JOGAR", "AJUDA", "SAIR" };

    private Font font1;
    private Font font2;
    public static BufferedImage menuBackgraund;

    public void init() {
        try {
            menuBackgraund = ImageIO.read(getClass().getResource("../../backgraundonline.jpeg"));
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }
        font1 = new Font("Dialog", Font.PLAIN, 48);
        font2 = new Font("Dialog", Font.PLAIN, 24);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(menuBackgraund, 0, 0, null);
        g.setColor(Color.RED);
        g.setFont(font1);
        g.drawString("SOCCER", Game.getWidth() / 2 - g.getFontMetrics().stringWidth("SOCCER") / 2,
                Game.getHeight() * 1 / 4);

        g.setFont(font2);
        for (int i = 0; i < options.length; i++) {
            g.setColor(Color.red);
            if (i == choice)
                g.setColor(Color.yellow);
            g.drawString(options[i], Game.getWidth() / 2 - g.getFontMetrics().stringWidth(options[i]) / 2,
                    (Game.getHeight() / 2) + i * 30);
        }
    }

    public void KeyPress(int code) {

    }

    public void KeyRelease(int code) {
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            choice--;
            if (choice < 0) {
                choice = options.length - 1;
            }
        } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            choice++;
            if (choice > options.length - 1) {
                choice = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            select();
        }
    }

    private void select() {
        switch (choice) {
            case 0:
                StateManager.setState(2);
                break;
            case 1:
                StateManager.setState(7);
                break;
            case 2:
                System.exit(3);
                break;
            default:
                System.exit(0);
                break;
        }
    }
}
