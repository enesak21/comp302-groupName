package domain.UI;
import javax.swing.*;
import java.awt.*;


public class UI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;


    public UI() {
        //controller class can be added as parameter here
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("2D Game");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add screens
        mainPanel.add(createHomeScreen(), "Home");
        mainPanel.add(createBuildScreen(), "Build");
        mainPanel.add(createGameScreen(), "Game");

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);  //change later
    }

    public void show() {
        frame.setVisible(true);
        cardLayout.show(mainPanel, "Home");
    }

    private JPanel createHomeScreen() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // Background Image
        ImageIcon originalIcon = new ImageIcon("src/resources/Rokue-like logo 4.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(0, 0, 800, 600); // Full screen for the image

        // Transparent Button
        JButton startButton = new JButton("Start Game");
        startButton.setBounds(300, 470, 200, 50); // Position button on the image
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFont(new Font("", Font.BOLD, 25));  //font can be added here
        startButton.setForeground(Color.black);

        startButton.addActionListener(e -> {

            cardLayout.show(mainPanel, "Build");
        });

        // Add Components to LayeredPane
        layeredPane.add(imageLabel, Integer.valueOf(0)); // Background layer
        layeredPane.add(startButton, Integer.valueOf(1)); // Foreground layer

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(layeredPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBuildScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("build components here", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JButton completeButton = new JButton("Complete Build");
        completeButton.addActionListener(e -> {

            cardLayout.show(mainPanel, "Game");
        });

        panel.add(title, BorderLayout.CENTER);
        panel.add(completeButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createGameScreen() {
        JPanel panel = new JPanel() {  //BURASI YERİNE KENDİ PLAY MODE EKRANINI EKLEYİN @CEMAL @MUHAMMED @İBRAHİM
                                       //UI PACKAGE İÇİNDE AYRI Bİ ŞEKİLDE GAME_PANEL CLASS OLARAK TANIMLANABİLİR (böyle yaparsak
                                       //homescreenin de öyle tanımlanması mantıklı olur) YA DA
                                       //DİREKT BURADA TANIMLANABİLİR NET KONUŞULMADIĞI İÇİN BU ŞEKİLDE YAPTIM DEĞİŞTİRİLEBİLİR
                                       //BÖYLE KALACAKSA UI PACKAGE SİLİNİP, UI CLASS GAME'İN İÇİNE ALINABİLİR
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillRect(50, 50, 50, 50); // Example player square
            }
        };
        panel.setBackground(Color.WHITE);

        JLabel gameLabel = new JLabel("Game Playing Mode");
        gameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(gameLabel);

        return panel;
    }
}
