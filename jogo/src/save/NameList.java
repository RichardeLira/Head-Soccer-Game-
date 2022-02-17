package jogo.src.save;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class NameList {
    private ArrayList<String> names;

    public NameList() {
        names = new ArrayList<>();
    }

    public void create(String valor) {
        File file = new File("arquivo.txt");
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("arquivo.txt"))) {
                writer.write(valor);
            } catch (Exception e) {
                System.out.println("Name list fail :" + e.toString());
            }
        } else {
            carrega();
            if (names.size() == 3)
                return;
            names.add(valor);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("arquivo.txt"))) {

                for (int i = 0; i < names.size(); i++) {
                    writer.write(names.get(i));
                    if (i < names.size() - 1)
                        writer.newLine();
                }

            } catch (IOException e) {
                System.out.println("Name list fail :" + e.toString());
            }
        }
    }

    public ArrayList<String> carrega() {
        File file = new File("arquivo.txt");
        if (!file.exists())
            return names;
        else {
            names = new ArrayList<String>();
            try (
                    BufferedReader reader = new BufferedReader(new FileReader("arquivo.txt"))) {
                String singleLine = null;
                while ((singleLine = reader.readLine()) != null) {
                    names.add(singleLine);
                }
            } catch (IOException e) {

            }
            return names;
        }
    }

    private void save() {
        File file = new File("arquivo.txt");
        if (file.exists()) {
            try (
                    BufferedWriter write = new BufferedWriter(new FileWriter("arquivo.txt"));) {
                for (int i = 0; i < names.size(); i++) {
                    write.write(names.get(i));
                    if (i < names.size() - 1)
                        write.newLine();
                }
            } catch (IOException e) {
                // TODO: handle exception
            }
        } else {
            System.out.println("arquivo n existe");
        }
    }

    public void update(int i, String name) {
        File file = new File("arquivo.txt");
        if (!file.exists())
            System.out.println("Nao existe arquivo");
        else {
            carrega();
            if (i < names.size()) {
                names.set(i, name);
                save();
            } else {
                System.out.println("indice invalido");
            }

        }
    }

    public void remove(int i) {
        carrega();
        if (i < names.size()) {
            names.remove(i);
            save();
        } else
            System.out.println("indice invalido");
    }
}
