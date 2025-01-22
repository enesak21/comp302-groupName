package domain.game1.main;

import domain.game1.UI.UI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

            SwingUtilities.invokeLater(() -> {
                    UI ui = new UI();
                    ui.show();
            });
    }
}