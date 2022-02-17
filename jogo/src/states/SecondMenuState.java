package jogo.src.states;

import java.awt.Color;
import jogo.src.main.Game;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class SecondMenuState implements State {

    private int choice = 0;
    private String[] options = { "CRIAR", "ENTRAR", "VOLTAR" };

    private Font font1;
    private Font font2;
    public static BufferedImage menuBackgraund;

    private static boolean host = false;

    @Override
    public void init() {
        try {
            menuBackgraund = ImageIO.read(getClass().getResource("../../backgraundonline.jpeg"));
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }
        font1 = new Font("Dialog", Font.PLAIN, 24);
        font2 = new Font("Dialog", Font.PLAIN, 24);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(menuBackgraund, 0, 0, null);
        g.setColor(Color.red);
        g.setFont(font1);
        g.drawString("Crie ou entre em uma partida",
                Game.getWidth() / 2 - g.getFontMetrics().stringWidth("Crie ou entre em uma partida") / 2,
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

    @Override
    public void KeyPress(int code) {

    }

    @Override
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
                // CRIAR PARTIDA
                host = true;
                StateManager.setState(4);
                break;
            case 1:
                // ENTRAR PARTIDA
                host = false;
                StateManager.setState(3);
                break;
            case 2:
                // VOLTAR MENU PRINCIPAL
                choice = 0;
                StateManager.setState(0);
                break;
            default:
                System.exit(0);
                break;
        }
    }

    public static boolean isHost() {
        return host;
    }
}
