package jogo.src.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

import jogo.src.display.Spritesheet;
import jogo.src.entities.Player;
import jogo.src.main.Game;
import jogo.src.save.NameList;

import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class AvatarListState implements State {

    private Font font1;
    private Font font2;

    public static NameList names = new NameList();

    private Player player;
    public static Spritesheet playersSprites = new Spritesheet("../../players-sprites.png");;

    private int[] avatarSelection = new int[4];
    private int currentAvatar = 0;
    public static int selectedAvatar = -1;

    private String[] avatarOptions = { "Jogar", "Criar", "Editar", "Remover" };
    private int currentOption = -1;

    // 0 menu de seleção personagem, 1 opções

    // listar os avatares disponiveis, carregado do metodo loadAvatar

    @Override
    public void init() {
        font1 = new Font("Dialog", Font.PLAIN, 24);
        font2 = new Font("Dialog", Font.PLAIN, 16);
        player = new Player(0, 0, 0, 0, playersSprites.getSprite(0, 0, 32, 32));
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(font1);
        g.drawString("Selecione o seu personagem",
                Game.getWidth() / 2 - g.getFontMetrics().stringWidth("Selecione o seu personagem") / 2,
                Game.getHeight() * 1 / 5);

        listAvatares(g);

        listOptions(g);
    }

    private void listAvatares(Graphics g) {
        ArrayList namesList = names.carrega();
        if (namesList != null) {
            for (int i = 0; i < namesList.size(); i++) {
                String name = (String) namesList.get(i);
                int posX = Game.getWidth() / 3;
                int posY = 40 + (i * 36);
                if (i == currentAvatar || i == selectedAvatar) {
                    g.setColor(new Color(249, 214, 28, 40));
                    g.fillRect(posX - 2, posY - 2, posX + 4, 32 + 4);
                }
                g.setColor(new Color(0, 0, 0, 25));
                g.fillRect(posX, posY, posX, 32);

                g.setColor(Color.white);
                g.setFont(font2);
                g.drawString(name, posX + 30, posY + 23);
                g.drawImage(playersSprites.getSprite(0, 0, 32, 32), posX, posY, null);
            }
            for (int i = namesList.size(); i < 3; i++) {
                int posX = Game.getWidth() / 3;
                int posY = 40 + (i * 36);
                if (i == currentAvatar || i == selectedAvatar) {
                    g.setColor(new Color(249, 214, 28, 40));
                    g.fillRect(posX - 2, posY - 2, posX + 4, 32 + 4);
                }
                g.setColor(new Color(0, 0, 0, 25));
                g.fillRect(posX, posY, posX, 32);
                g.setColor(Color.white);
                g.setFont(font2);
                g.drawString("Vazio", posX + 30, posY + 23);
            }
        }
    }

    private void listOptions(Graphics g) {
        for (int i = 0; i < avatarOptions.length; i++) {
            g.setColor(Color.white);
            g.setFont(font2);
            if (currentOption == i && currentAvatar == avatarSelection.length - 1)
                g.setColor(Color.yellow);
            g.drawString(avatarOptions[i], (Game.getWidth() * i / 4) + 10, Game.getHeight() - 10);
        }
    }

    @Override
    public void KeyPress(int code) {

    }

    @Override
    public void KeyRelease(int code) {
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            currentAvatar++;
            if (currentAvatar > avatarSelection.length - 1)
                currentAvatar = 0;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            currentAvatar--;
            if (currentAvatar < 0) {
                currentAvatar = avatarSelection.length - 1;
            }
        }
        if (currentAvatar == avatarSelection.length - 1) {
            currentOption = currentOption == -1 ? currentOption = 0 : currentOption;
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                currentOption++;

                if (currentOption > avatarOptions.length - 1)
                    currentOption = 0;
            } else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                currentOption--;

                if (currentOption < 0)
                    currentOption = avatarSelection.length - 1;
            }
        } else {
            currentOption = -1;
        }
        if (code == KeyEvent.VK_ENTER) {
            select();

        }
    }

    private void select() {
        if (currentAvatar < avatarSelection.length - 1) {
            selectedAvatar = currentAvatar;
        }
        if (currentOption >= 0) {
            switch (currentOption) {
                case 0:
                    StateManager.setState(1);
                    break;
                case 1:
                    StateManager.setState(5);
                    break;
                case 2:
                    if (selectedAvatar >= 0 && selectedAvatar < names.carrega().size())
                        StateManager.setState(6);
                    break;
                case 3:
                    names.remove(selectedAvatar);
                    break;
            }
        }
    }

}
