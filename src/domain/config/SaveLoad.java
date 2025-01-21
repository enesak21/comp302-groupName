package domain.config;

import java.io.*;

public class SaveLoad {
    private static String fileName = "src/domain/config/gameState.dat";

    public static void saveGameState(GameState gameState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(gameState);
            oos.flush(); // Ensure data is written to the file
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
        }
    }

    public static GameState loadGameState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load game: " + e.getMessage());
            return null;
        }
    }
}
