package jogo.src.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jogo.src.states.WorldState;

public class NetSlave extends Thread {
    private List<Integer> dataOutput;
    private List<Integer> dataInput;
    private ObjectOutputStream streamOut;
    private ObjectInputStream streamIn;
    private final int PORT = 49000;
    private Socket client;

    public NetSlave(String Ip) throws UnknownHostException, IOException {
        client = new Socket(Ip, PORT);
        dataInput = new ArrayList<Integer>();
        dataOutput = new ArrayList<Integer>();

    }

    private void StartSlave() throws ClassNotFoundException, IOException {
        while (dataOutput.size() != 1) {
            dataInput.clear();
            dataOutput.clear();

            streamOut = new ObjectOutputStream(client.getOutputStream());
            streamIn = new ObjectInputStream(client.getInputStream());
            // Aguardando o tempo para sincronização
            try {
                TimeUnit.MILLISECONDS.sleep(16);
            } catch (InterruptedException e) {
                System.out.println("Time waiting fail in host :: " + e.toString());
            }
            // Recebimento de dados e aplicação dos mesmos
            dataOutput = (ArrayList<Integer>) streamIn.readObject();
            WorldState.entities.get(0).setXYNet(dataOutput.get(0), dataOutput.get(1)); // set info enemy
            WorldState.entities.get(2).setXYNet(dataOutput.get(2), dataOutput.get(3)); // set info ball
            // Escrevendo os dados para a outra máquina
            dataInput.addAll(WorldState.entities.get(1).GetXYNet()); // player info get
            streamOut.flush();
            // Enviando dados
            streamOut.writeObject(dataInput);

        }

        client.close();
        streamIn.close();
        streamOut.close();

    }
    //Método run herdado da thread
    @Override
    public void run() {
        try {
            StartSlave();
        } catch (ClassNotFoundException e) {
            System.out.println("Thread method run :: Conection class not found  :" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Thread method run :: Conection fail  :" + e.getMessage());
        }
    }

}