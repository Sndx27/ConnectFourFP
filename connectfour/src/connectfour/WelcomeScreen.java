/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #7
 * 1 - 5026231066 - Burju Ferdinand Harianja
 * 2 - 5026231132 - Clay Amsal Sebastian Hutabarat
 * 3 - 5026213181 - Sandythia Lova Ramadhani Krisnaprana
 */

package connectfour;

import java.awt.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

public class WelcomeScreen extends JFrame {

    private static final String MUSIC_FILE = "connectfour\\src\\audio\\lofi-alarm-clock-243766.wav";
    private Clip backgroundMusic;

    public WelcomeScreen() {
        setTitle("Connect Four - Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Lokasi selalu di tengah layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        setLayout(null);

        // Background Panel with Tic Tac Toe theme
        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("connectfour\\src\\images\\WhatsApp Image 2024-12-20 at 17.28.16_a37d46a2.jpg").getImage(); // Replace with your image path

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // // Button Panel (Placed at the bottom)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 70)); // Center alignment with more vertical spacing
        buttonPanel.setOpaque(false); // Transparent background


        // Create themed buttons
        JButton startButton = createStyledButton("Start");
        startButton.addActionListener(e -> {
            stopMusic();
            dispose();//welcome screen ditutup
            SwingUtilities.invokeLater(() -> ConnectFour.play());
        });
        backgroundPanel.add(startButton);

        // Option Button
        JButton optionButton = createStyledButton("Options");
        optionButton.setBounds(250, 180, 100, 40);
        optionButton.addActionListener(e -> showOptions());
        backgroundPanel.add(optionButton);

        // Exit Button
        JButton exitButton = createStyledButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        // Add buttons to the panel
        buttonPanel.add(startButton);
        buttonPanel.add(optionButton);
        buttonPanel.add(exitButton);

        // Add button panel to the frame
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Start background music
        playMusic();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(255, 165, 0)); // Orange color (fruit theme)
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 2)); // Red border for fruit theme
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 30)); // Increase button size slightly
    
        // Add hover effect to simulate fruit and fresh theme (color changes)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 87, 0)); // Dark orange-red (hover effect)
                button.setForeground(Color.WHITE); // White text
            }
    
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 165, 0)); // Revert to orange
                button.setForeground(Color.WHITE); // Revert to white text
            }
        });
        return button;
    }
    

    private void playMusic() {
        try {
            if (backgroundMusic == null || !backgroundMusic.isRunning()) {
                File audioFile = new File(MUSIC_FILE);
                if (audioFile.exists()) {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                    backgroundMusic = AudioSystem.getClip();
                    backgroundMusic.open(audioInputStream);
                    backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    System.err.println("Audio file not found: " + audioFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }

    private void showOptions() {
        // Create a custom dialog
        JDialog optionsDialog = new JDialog(this, "About", true);
        optionsDialog.setSize(400, 300);
        optionsDialog.setLocationRelativeTo(this);
        optionsDialog.setLayout(new BorderLayout());
    
        // Header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 50, 50));
        JLabel titleLabel = new JLabel("About the Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
    
        // Content panel with creator information
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(30, 30, 30));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    
        JLabel creatorLabel1 = new JLabel("\u2022 Burju Ferdinand Harianja (066)");
        JLabel creatorLabel2 = new JLabel("\u2022 Clay Amsal Sebastian Hutabarat (132)");
        JLabel creatorLabel3 = new JLabel("\u2022 Sandythia Lova Ramadhani Krisnaprana (181)");
    
        JLabel[] labels = { creatorLabel1, creatorLabel2, creatorLabel3 };
        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setForeground(Color.WHITE);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(Box.createVerticalStrut(10)); // Spacing
            contentPanel.add(label);
        }
    
        // Footer panel with a close button
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(50, 50, 50));
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(200, 50, 50));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    
        closeButton.addActionListener(e -> optionsDialog.dispose());
        footerPanel.add(closeButton);
    
        // Add panels to dialog
        optionsDialog.add(headerPanel, BorderLayout.NORTH);
        optionsDialog.add(contentPanel, BorderLayout.CENTER);
        optionsDialog.add(footerPanel, BorderLayout.SOUTH);
    
        // Display the dialog
        optionsDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.setVisible(true); // Display the welcome screen
        });
    }
}