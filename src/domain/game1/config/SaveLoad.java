package domain.game1.config;

import javax.swing.*;
import java.io.*;

public class SaveLoad {
    private static final String BASE_FILE_NAME = "src/domain/config/gameState_slot";

    public static void saveGameState(GameState gameState, int slot) {
        String fileName = BASE_FILE_NAME + slot + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(gameState);
            oos.flush(); // Ensure data is written to the file
            JOptionPane.showMessageDialog(null, "Game saved successfully to Slot " + slot + ".",
                    "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save game to Slot " + slot + ": " + e.getMessage(),
                    "Save Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static GameState loadGameState(int slot) {
        String fileName = BASE_FILE_NAME + slot + ".dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Failed to load game from Slot " + slot + ". " + "You do not have a saved game in this slot.");
            return null;
        }
    }
}
