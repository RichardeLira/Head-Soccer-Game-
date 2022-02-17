package jogo.src.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import jogo.src.entities.Ball;
import jogo.src.entities.Entity;
import jogo.src.entities.Player;
import jogo.src.entities.Player2;
import jogo.src.network.NetHost;
import java.awt.Font;

public class WorldState implements State {

    private BufferedImage background;
    private BufferedImage playersSprites;
    private NetHost host;
    public static ArrayList<Entity> entities = new ArrayList<Entity>();
    public static BufferedImage ball;

    public static int rightPlacar = 0;
    public static int leftPlacar = 0;

    public void init() {
        try {
            background = ImageIO.read(getClass().getResource("../../background.png"));
            ball = ImageIO.read(getClass().getResource("../../ball.png"));
            playersSprites = ImageIO.read(getClass().getResource("../../players-sprites.png"));
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }
        // Iniciado a thread do host e esperando a conexão do outro player
        if (SecondMenuState.isHost() && (host == null)) {
            try {
                host = new NetHost();
                host.start();
            } catch (IOException e) {
                System.out.println("Conetion fail World :: " + e.toString());
            }
        } else if (SlaveState.slave != null) {
            SlaveState.slave.start();
        }

        // Adicionando todos os objetos ao mundo
        entities.add(new Player(50, 115, 32, 32, playersSprites.getSubimage(0, 0, 32, 32)));
        entities.add(new Player2(245, 115, 32, 32, playersSprites.getSubimage(32, 0, 32, 32)));
        entities.add(new Ball(160, 130, 16, 16, ball));

    }

    public void tick() {
        for (Entity e : entities) {
            e.tick();
        }
    }

    public void render(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.setColor(Color.red);
        g.setFont(new Font("Dialog", Font.PLAIN, 32));
        g.drawString(String.valueOf(leftPlacar), 50, 32);
        g.drawString(String.valueOf(rightPlacar), 245, 32);
        for (Entity e : entities) {
            e.render(g);
        }

    }

    public void KeyPress(int code) {
        for (Entity e : entities)
            e.KeyPress(code);
    }

    public void KeyRelease(int code) {
        for (Entity e : entities)
            e.KeyReleased(code);
    }

}
