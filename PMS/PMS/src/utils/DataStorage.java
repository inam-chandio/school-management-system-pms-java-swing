package utils;

import java.io.*;
import java.util.List;

public class DataStorage {
    public static <T> void saveData(List<T> dataList, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(dataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> loadData(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
