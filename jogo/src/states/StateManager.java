package jogo.src.states;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class StateManager implements KeyListener {

    private final int MAIN_MENU = 0;
    private final int SECOND_MENU = 1;
    private final int AVATAR_LIST = 2;
    private final int SLAVESTATE = 3;
    private final int WORLD = 4;
    private final int CREATE_MENU = 5;
    private final int UPDATE_MENU = 6;
    private final int HELPSATE = 7;
    private static int currentState = 0;
    private static State[] states = new State[10];

    public StateManager() {
        states[MAIN_MENU] = new MenuState();
        states[SECOND_MENU] = new SecondMenuState();
        states[SLAVESTATE] = new SlaveState();
        states[AVATAR_LIST] = new AvatarListState();
        states[WORLD] = new WorldState();
        states[CREATE_MENU] = new CreateAvatarState();
        states[UPDATE_MENU] = new UpdateAvatarState();
        states[HELPSATE] = new HelpSate();
    }

    public void tick() {
        states[currentState].tick();
    }

    public void render(Graphics g) {
        states[currentState].render(g);
    }

    public static void setState(int state) {
        currentState = state;
        states[currentState].init();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        states[currentState].KeyPress(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        states[currentState].KeyRelease(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
