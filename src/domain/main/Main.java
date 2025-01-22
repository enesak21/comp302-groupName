package domain.main;

import domain.UI.UI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

            SwingUtilities.invokeLater(() -> {
                    UI ui = new UI();
                    ui.show();
            });
    }
}