package jogo.src.entities;

import java.awt.image.BufferedImage;

import jogo.src.main.Game;
import jogo.src.states.WorldState;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball extends Entity {

    private int spd = 2;
    private boolean right;
    private boolean left;
    private boolean isAir;
    private int rotacao = 0;

    private static int rightForce = 0;
    private static int leftForce = 0;
    private static int upForce = 0;

    private static boolean rightKick;
    private static boolean leftKick;

    public Ball(int x, int y, int w, int h, BufferedImage sprite) {
        super(x, y, w, h, sprite);

    }

    @Override
    public void tick() {
        if (x > Game.getWidth() - 16 || x < 0)
            spd *= -1;
        if (y < 130 && !isAir) {
            y++;
        }
        if (spd > 0) {
            right = true;
        } else if (spd < 0) {
            left = true;

        }

        if (rightForce > 0) {
            x += spd;
            rightForce--;
            leftForce = 0;
            rotacao += 4;
        }
        if (rightKick) {
            spd = Math.abs(spd);
            rightKick = false;
        }
        if (leftKick) {
            spd = -2;
            leftKick = false;
        }
        if (leftForce > 0) {
            x += spd;
            leftForce--;
            rightForce = 0;
            rotacao -= 4;
        }
        if (upForce > 0) {
            upForce--;
            isAir = true;
            y--;
        }
        if (upForce == 0)
            isAir = false;

        traveColision();
        if (goal() > 0) {
            if (goal() == 2) {
                WorldState.leftPlacar++;
            } else if (goal() == 1) {
                WorldState.rightPlacar++;
            }
            rightForce = 0;
            leftForce = 0;
            upForce = 0;
            x = 160;
            y = 40;
        }
    }

    @Override
    public void render(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        g2.rotate(Math.toRadians(rotacao), x + 8, y + 8);
        g.drawImage(WorldState.ball, x, y, null);

        g2 = null;
    }

    @Override
    public void KeyPress(int code) {
        // TODO Auto-generated method stub

    }

    @Override
    public void KeyReleased(int code) {
        // TODO Auto-generated method stub

    }

    public void traveColision() {
        Rectangle leftTrave = new Rectangle(0, 91, 30, 3);
        Rectangle rightTrave = new Rectangle(309, 91, 30, 3);

        Rectangle upBall = new Rectangle(x, y, 16, 1);
        Rectangle downBall = new Rectangle(x, y + 16, 16, 1);

        if (leftTrave.intersects(downBall)) {
            isAir = true;
            y = leftTrave.y - 16;
        }
        if (leftTrave.intersects(upBall)) {
            isAir = false;
            upForce = 0;
        }
        if (rightTrave.intersects(downBall)) {
            isAir = true;
            y = leftTrave.y - 16;
        }
        if (rightTrave.intersects(upBall)) {
            isAir = false;
            upForce = 0;
        }

    }

    public static void kick(String direction) {
        switch (direction) {
            case "right":
                rightForce = 120;
                upForce = 45;
                rightKick = true;
                break;
            case "left":
                leftForce = 90;
                upForce = 45;
                leftKick = true;
                break;
            case "up":
                upForce = 50;
                rightForce = 60;
                break;
        }
    }

    public int goal() {
        if (x + 16 <= 23) {
            if (y + 16 >= 94) {
                return 1;
            }
        }
        if (x >= 314) {
            if (y + 16 >= 94) {
                return 2;
            }
        }
        return 0;
    }

}
