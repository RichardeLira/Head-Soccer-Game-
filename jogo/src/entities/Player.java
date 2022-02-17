package jogo.src.entities;

import java.awt.image.BufferedImage;

import jogo.src.main.Game;
import jogo.src.states.SecondMenuState;
import jogo.src.states.WorldState;

import java.awt.event.KeyEvent;
import java.awt.Rectangle;

public class Player extends Entity {

    private int spd = 2;
    private boolean right, left;
    private boolean jump = false;
    private boolean isJumping = false;
    private boolean isAir = false;

    public Player(int x, int y, int w, int h, BufferedImage sprite) {
        super(x, y, w, h, sprite);
    }

    @Override
    public void tick() {
        if (right) {
            x += spd;
            if (x >= Game.getWidth() - 32)
                x = Game.getWidth() - 32;
        } else if (left) {
            x -= spd;
            if (x <= 0)
                x = 0;
        }
        // gravity
        if (y == 115) {
            spd = 2;
            isAir = false;
        }
        if (y != 115 && !isJumping)
            y += 2;
        if (jump) {
            isJumping = true;
            isAir = true;
        }
        if (isJumping) {
            y -= 3;
            if (y < 70) {
                isJumping = false;
                jump = false;
            }
        }
        kickingBall();

    }

    @Override
    public void KeyPress(int code) {
        if (SecondMenuState.isHost()) {
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
                this.left = true;
            } else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
                this.right = true;
            }
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
                if (!isAir)
                    this.jump = true;
        }
    }

    @Override
    public void KeyReleased(int code) {
        if (SecondMenuState.isHost()) {
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
                this.left = false;
            } else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
                this.right = false;
            }
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
                this.jump = false;
        }
    }

    public void kickingBall() {
        Rectangle rectRight = new Rectangle(x + 23, y + 9, 2, 22);
        Rectangle rectUp = new Rectangle(x + 6, y + 8, 18, 2);
        Rectangle rectLeft = new Rectangle(x + 6, y + 9, 2, 22);

        for (int i = 0; i < WorldState.entities.size(); i++) {
            Entity e = WorldState.entities.get(i);
            if (e instanceof Ball) {
                Rectangle ball = new Rectangle(e.getX(), e.getY(), 16, 16);
                if (rectRight.intersects(ball)) {
                    right = false;
                    kick("right");
                } else if (rectUp.intersects(ball))
                    kick("up");
                else if (rectLeft.intersects(ball)) {
                    left = false;
                    kick("left");
                }
            }
        }
    }

    public void kick(String direction) {
        Ball.kick(direction);
    }
}