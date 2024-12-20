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
        setLayout(new BorderLayout());

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
            stopMusic(); // Stop background music
            dispose();   // Close WelcomeScreen
            SwingUtilities.invokeLater(() -> ConnectFour.play()); // Start the game
        });

        JButton optionButton = createStyledButton("Options");
        optionButton.addActionListener(e -> showOptions());

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
        JOptionPane.showMessageDialog(this,
                "<html><div style='text-align: center;'>"
                        + "Game created by:<br>"
                        + "<b>Burju Ferdinand Harianja (066)</b><br>"
                        + "<b>Clay Amsal Sebastian Hutabarat (132)</b><br>"
                        + "<b>Sandythia Lova Ramadhani Krisnaprana (181)</b>"
                        + "</div></html>",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.setVisible(true);
        });
    }
}