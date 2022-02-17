package jogo.src.entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;

public class Entity {
    protected int x;
    protected int y;
    private int width;
    private int height;
    protected BufferedImage sprite;

    public Entity(int x, int y, int w, int h, BufferedImage sprite) {
        // Inicio de jogo - local do personagem
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.sprite = sprite;
    }

    // Carragamento do sprite
    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(sprite, x, y, width, height, null);
    }

    public void KeyPress(int code) {

    }

    public void KeyReleased(int code) {

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setXYNet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Integer> GetXYNet() {
        List<Integer> coordinates = new ArrayList<Integer>();
        coordinates.add(this.x);
        coordinates.add(this.y);
        return coordinates;

    }

}
