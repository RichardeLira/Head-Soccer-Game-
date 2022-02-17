package jogo.src.display;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Spritesheet {

    private BufferedImage sprite;

    public Spritesheet(String path) {
        try {
            sprite = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            System.out.println("Fail erro Spritesheet read sprit :" + e.toString());
        }
    }

    public BufferedImage getSprite(int x, int y, int w, int h) {
        return sprite.getSubimage(x, y, w, h);
    }

}
