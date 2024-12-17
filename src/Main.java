import domain.UI.UI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
    //main class
        SwingUtilities.invokeLater(() -> {

            UI ui = new UI();
            ui.show();
        });

    }
}
