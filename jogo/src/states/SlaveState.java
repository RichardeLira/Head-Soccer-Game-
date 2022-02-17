package jogo.src.states;

import java.awt.Graphics;
import java.io.IOException;
import javax.swing.JOptionPane;
import jogo.src.network.NetSlave;

/*
Responsável pela a interface de conexão com a máquina host 
verificandos os possíveis erros de conecxão e tratando esses erros 
*/
public class SlaveState implements State {

    public static NetSlave slave;
    private String Ip;
    private String decide;
    private boolean againConection = true;

    @Override
    public void init() {
        // Verificando se a conexão com a outra máquina foi realizada
        while (againConection) {
            try {
                Ip = JOptionPane.showInputDialog("Digite o endereço IP da outra máquina");
                JOptionPane.showConfirmDialog(null, "Aperte Ok e aguarde até 10 segundos para a realização da conexão",
                        "Conectando...", JOptionPane.CLOSED_OPTION);
                slave = new NetSlave(Ip);
                JOptionPane.showMessageDialog(null, "Conexão realizada", "Sucesso", JOptionPane.OK_OPTION);
                againConection = false;
                StateManager.setState(4);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Conexão falhou. Verifique o endereço IP", "erro na conexão",
                        JOptionPane.ERROR_MESSAGE);
                decide = JOptionPane.showInputDialog("Deseja tentar se conectar novamente ? [YES/NO]");
                if (decide.equals("NO")) {
                    StateManager.setState(0);
                    againConection = false;
                }
            }
        }

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

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