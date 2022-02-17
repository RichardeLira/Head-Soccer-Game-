package jogo.src.states;

import java.awt.Graphics;

public interface State {

    void init();

    void tick();

    void render(Graphics g);

    void KeyPress(int code);

    void KeyRelease(int code);

}
