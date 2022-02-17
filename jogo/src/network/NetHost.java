package jogo.src.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jogo.src.states.WorldState;

public class NetHost extends Thread {

    private static List<Integer> dataOutput;
    private static List<Integer> dataInput;
    private ObjectOutputStream streamOut;
    private ObjectInputStream streamIn;
    private final int PORT = 49000;
    private ServerSocket server;
    private Socket client;

    public NetHost() throws IOException {
        server = new ServerSocket(PORT);
        client = new Socket();
        dataInput = new ArrayList<Integer>();
        dataOutput = new ArrayList<Integer>();
        System.out.println("Port is listening");
    }

    private void StartHost() throws IOException, ClassNotFoundException {
        System.out.println("Waiting");
        client = server.accept(); // esperando por conexões
        System.out.println("conectado");
        while (dataOutput.size() != 1) {
            dataInput.clear();
            dataOutput.clear();
            // Escrevendo os dados para a outra máquina
            streamOut = new ObjectOutputStream(client.getOutputStream());
            streamIn = new ObjectInputStream(client.getInputStream());
            // Aguardando o tempo para sincronização
            try {
                TimeUnit.MILLISECONDS.sleep(16);
            } catch (InterruptedException e) {
                System.out.println("Time waiting fail in host :: " + e.toString());
            }
            // Enviando dados
            dataInput.addAll(WorldState.entities.get(0).GetXYNet()); // player1 info
            dataInput.addAll(WorldState.entities.get(2).GetXYNet()); // ball info
            streamOut.flush();
            streamOut.writeObject(dataInput);
            // Recebimento de dados e aplicação dos mesmos
            dataOutput = (ArrayList<Integer>) streamIn.readObject();
            WorldState.entities.get(1).setXYNet(dataOutput.get(0), dataOutput.get(1)); // set info other player
        }

        streamIn.close();
        streamOut.close();
        server.close();
        client.close();
    }

    // Método run herdado da thread
    @Override
    public void run() {
        try {
            StartHost();
        } catch (ClassNotFoundException e) {
            System.out.println("Thread method run :: Conection class not found  :" + e.getMessage());

        } catch (IOException e) {
            System.out.println("Thread method run :: Conection fail  :" + e.getMessage());

        }

    }

}
