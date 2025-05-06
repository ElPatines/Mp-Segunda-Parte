package GameManager;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BinaryAdapter implements Adapter {

    public void saveGameData(LinkedHashMap<String, Player> userList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/userList.ser"))) {
            oos.writeObject(userList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedHashMap<String, Player> loadGameData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/userList.ser"))) {
            return (LinkedHashMap<String, Player>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Game data file not found: " + e.getMessage());
            return new LinkedHashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new LinkedHashMap<>();
        }
    }

    public void saveRankingData(ArrayList<Player> rankingList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/rankingList.ser"))) {
            oos.writeObject(rankingList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Player> loadRankingData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/rankingList.ser"))) {
            return (ArrayList<Player>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Ranking data file not found: " + e.getMessage());
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveAdminData(ArrayList<Admin> adminList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/adminList.ser"))) {
            oos.writeObject(adminList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Admin> loadAdminData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/adminList.ser"))) {
            return (ArrayList<Admin>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Admin data file not found: " + e.getMessage());
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}