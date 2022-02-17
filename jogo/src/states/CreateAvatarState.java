package jogo.src.states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import jogo.src.main.Game;
import java.awt.Font;
import java.awt.Color;

public class CreateAvatarState implements State {

    private Font font1;
    private Font font2;
    private String[] options = { "NAMESELECT", "SALVAR", "VOLTAR" };
    private int currentOption = 0;
    private String name;

    private boolean isJoption = false;

    @Override
    public void init() {
        font1 = new Font("Dialog", Font.PLAIN, 24);
        font2 = new Font("Dialog", Font.PLAIN, 16);
        name = null;
        currentOption = 0;
        isJoption = false;
    }

    @Override
    public void tick() {
        if (currentOption != 0)
            isJoption = false;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(font1);
        String title = "Crie seu personagem";
        g.drawString(title, Game.getWidth() / 2 - g.getFontMetrics().stringWidth(title) / 2, Game.getHeight() * 1 / 5);

        g.setFont(font2);
        g.drawString("Escolha o nome:", Game.getWidth() / 2 - g.getFontMetrics().stringWidth("Escolha o nome:") / 2,
                Game.getHeight() * 2 / 5);

        int posX = Game.getWidth() / 2 - g.getFontMetrics().stringWidth("Escolha o nome:") / 2;
        if (currentOption == 0) {
            g.setColor(new Color(255, 255, 255, 50));
            g.fillRect(posX, Game.getHeight() * 2 / 5 + 10, g.getFontMetrics().stringWidth("Escolha o nome:"), 18);
        }
        g.setColor(new Color(0, 0, 0, 25));
        g.fillRect(posX, Game.getHeight() * 2 / 5 + 10, g.getFontMetrics().stringWidth("Escolha o nome:"), 18);
        if (name != null) {
            g.setColor(Color.white);
            g.setFont(font2);
            g.drawString(name, posX, Game.getHeight() * 2 / 5 + 25);
        }
        for (int i = 1; i < options.length; i++) {
            g.setFont(font2);
            g.setColor(Color.white);
            if (currentOption == i)
                g.setColor(Color.yellow);
            g.drawString(options[i], Game.getWidth() / 2 - g.getFontMetrics().stringWidth(options[i]) / 2,
                    Game.getHeight() * 3 / 5 + (i * 20));
        }

    }

    @Override
    public void KeyPress(int code) {

    }

    @Override
    public void KeyRelease(int code) {
        if (code == KeyEvent.VK_UP) {
            currentOption--;
            if (currentOption < 0)
                currentOption = options.length - 1;
        }
        if (code == KeyEvent.VK_DOWN) {
            currentOption++;
            if (currentOption > options.length - 1)
                currentOption = 0;
        }
        if (code == KeyEvent.VK_ENTER && !isJoption) {
            select();
            isJoption = true;
        }
    }

    private void select() {
        switch (currentOption) {
            case 0:
                name = JOptionPane.showInputDialog("Digite o nome");
                if (name.length() < 0)
                    JOptionPane.showMessageDialog(null, "Digite um nome valido");
                currentOption = 3;
                break;
            case 1:
                if (name.length() > 0) {
                    AvatarListState.names.create(name);
                    StateManager.setState(2);
                } else {
                    JOptionPane.showMessageDialog(null, "Digite um nome valido");
                    currentOption = 2;
                }
                break;
            case 2:
                StateManager.setState(2);
                break;
        }
    }
}
