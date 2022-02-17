package jogo.src.states;

import java.awt.Graphics;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class HelpSate implements State {
    private byte[] text;
    private String read;

    @Override
    public void init() {
        Path way = Paths.get("jogo/HelpSate.txt");
        try {
            text = Files.readAllBytes(way);
            read = new String(text);
            JOptionPane.showMessageDialog(null, read, "Ajuda", JOptionPane.YES_OPTION);
            StateManager.setState(0);
        } catch (IOException e) {
            System.out.println("Path fail in HelpSate :" + e.toString());
        }

    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub

    }

    @Override
    public void KeyPress(int code) {
        // TODO Auto-generated method stub

    }

    @Override
    public void KeyRelease(int code) {
        // TODO Auto-generated method stub

    }

}
