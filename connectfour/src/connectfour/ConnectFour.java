/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #7
 * 1 - 5026231066 - Burju Ferdinand Harianja
 * 2 - 5026231132 - Clay Amsal Sebastian Hutabarat
 * 3 - 5026213181 - Sandythia Lova Ramadhani Krisnaprana
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package connectfour;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class ConnectFour extends JPanel {
   private static final long serialVersionUID = 1L; // to prevent serializable warning

   // Define named constants for the drawing graphics
   public static final String TITLE = "Connect Four";
   public static final Color COLOR_BG = Color.WHITE;
   public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
   public static final Color COLOR_CROSS = Color.RED; // Redrgb(250, 60, 27)
   public static final Color COLOR_NOUGHT = Color.YELLOW; // Bluergb(250, 227, 17)
   public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

   // Define game objects
   private Board board; // the game board
   private State currentState; // the current state of the game
   private Seed currentPlayer; // the current player
   private JLabel statusBar; // for displaying status message


   /** Constructor to setup the UI and game components */
   public ConnectFour() {
      // This JPanel fires MouseEvent
      super.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
             int mouseX = e.getX() - 65;
             int mouseY = e.getY() + 30;
         
             // Hitung kolom dan baris berdasarkan posisi klik
             int col = mouseX / Cell.SIZE; // Kolom berdasarkan mouseX
             int row = mouseY / Cell.SIZE; // Baris berdasarkan mouseY
         
             // Pastikan klik berada dalam grid
             if (col >= 0 && col < Board.COLS && row >= 0 && row < Board.ROWS) {
                 // Hitung pusat lingkaran
                 int offsetRight = 10; // Tambahkan offset untuk memperluas ke kanan
                 int centerX = (col * Cell.SIZE) + (Cell.SIZE / 2); // Pusat X dengan bias ke kanan
                 int centerY = (row * Cell.SIZE) + (Cell.SIZE / 2); // Pusat Y tetap
                 int radius = (Cell.SIZE / 2); // Radius lingkaran
                 int tolerance = 20; // Tambahkan toleransi untuk memperluas area valid
                 int adjustedRadius = radius + tolerance; // Radius dengan toleransi
         
                 // Perluas radius hanya ke kanan
                 if (mouseX > centerX) {
                     adjustedRadius += 0; // Tambahkan radius untuk area kanan
                 }
         
                 // Debugging: Tampilkan nilai koordinat
                 System.out.println("Mouse X: " + mouseX + ", Mouse Y: " + mouseY);
                 System.out.println("Center X: " + centerX + ", Center Y: " + centerY + ", Adjusted Radius: " + adjustedRadius);
         
                 // Validasi apakah klik berada di dalam lingkaran yang diperluas
                 if (Math.pow(mouseX - centerX, 2) + Math.pow(mouseY - centerY, 2) <= Math.pow(adjustedRadius, 2)) {
                     // Klik valid, lakukan aksi permainan
                     if (currentState == State.PLAYING) {
                         SoundEffect.EAT_FOOD.play();
         
                         // Cari cell kosong dari bawah ke atas di kolom yang diklik
                         for (int rowI = Board.ROWS - 1; rowI >= 0; rowI--) {
                             if (board.cells[rowI][col].content == Seed.NO_SEED) {
                                 // Perbarui state permainan
                                 currentState = board.stepGame(currentPlayer, rowI, col);
         
                                 // Ganti pemain
                                 currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                                 break;
                             }
                         }
                     } else {
                         // Tampilkan hasil permainan
                         if (currentState == State.CROSS_WON) {
                             showWinnerDialog("Yellow Wins!");
                         } else if (currentState == State.NOUGHT_WON) {
                             showWinnerDialog("Blue Wins!");
                         } else if (currentState == State.DRAW) {
                             showWinnerDialog("It's a Draw!");
                         }
                     }
                     repaint();
                 } else {
                     // Klik tidak valid
                     System.out.println("Klik tidak valid: di luar lingkaran.");
                 }
             } else {
                 // Klik di luar grid
                 System.out.println("Klik di luar grid.");
             }
         }
         
                  
          
          

      });
  
      // Setup the status bar (JLabel) to display status message
      statusBar = new JLabel();
      statusBar.setFont(FONT_STATUS);
      statusBar.setBackground(COLOR_BG_STATUS);
      statusBar.setOpaque(true);
      statusBar.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, 30)); // Adjust status bar width
      statusBar.setHorizontalAlignment(JLabel.LEFT);
      statusBar.setBorder(null); // Remove border here
  
      super.setLayout(new BorderLayout());
      super.add(statusBar, BorderLayout.PAGE_END); // Add status bar
      super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30)); // Adjust size
      super.setBorder(null); // Ensure no border on JPanel
  
      // Set up Game
      initGame();
      newGame();
  }
  
   /** Initialize the game (run once) */
   public void initGame() {
      board = new Board(); // allocate the game-board
   }

   /** Reset the game-board contents and the current-state, ready for new game */
   public void newGame() {
      for (int row = 0; row < Board.ROWS; ++row) {
         for (int col = 0; col < Board.COLS; ++col) {
            board.cells[row][col].content = Seed.NO_SEED; // all cells empty
         }
      }
      currentPlayer = Seed.CROSS; // cross plays first
      currentState = State.PLAYING; // ready to play
   }

   /** Custom painting codes on this JPanel */
   @Override
   public void paintComponent(Graphics g) { // Callback via repaint()
      super.paintComponent(g);
      setBackground(COLOR_BG); // set its background color

      board.paint(g); // ask the game board to paint itself

      // Print status-bar message
      if (currentState == State.PLAYING) {
         statusBar.setForeground(Color.BLACK);
         statusBar.setText((currentPlayer == Seed.CROSS) ? "Yellow's Turn" : "Blue's Turn");
      } else if (currentState == State.DRAW) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("It's a Draw! Click to play again.");
         SoundEffect.DIE.play();
      } else if (currentState == State.CROSS_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("'Yellow' Won! Click to play again.");
         SoundEffect.EXPLODE.play();
      } else if (currentState == State.NOUGHT_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("'Blue' Won! Click to play again.");
         SoundEffect.EXPLODE.play();
      }
   }

   private void showWinnerDialog(String message) {
      // Create a custom dialog for modern aesthetics
      JDialog endGameDialog = new JDialog((Frame) null, "Game Over", true);
      endGameDialog.setSize(400, 200);
      endGameDialog.setLocationRelativeTo(this);
      endGameDialog.setLayout(new BorderLayout());
  
      // Header panel with message
      JPanel headerPanel = new JPanel();
      headerPanel.setBackground(new Color(50, 50, 50));
      JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "<br>What would you like to do?</div></html>");
      messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
      messageLabel.setForeground(Color.WHITE);
      messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
      headerPanel.add(messageLabel);
  
      // Button panel for options
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(new Color(30, 30, 30));
      buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
  
      JButton newGameButton = new JButton("New Game");
      newGameButton.setFont(new Font("Arial", Font.BOLD, 14));
      newGameButton.setBackground(new Color(50, 200, 50));
      newGameButton.setForeground(Color.WHITE);
      newGameButton.setFocusPainted(false);
      newGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
      newGameButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
  
      JButton exitButton = new JButton("Exit");
      exitButton.setFont(new Font("Arial", Font.BOLD, 14));
      exitButton.setBackground(new Color(200, 50, 50));
      exitButton.setForeground(Color.WHITE);
      exitButton.setFocusPainted(false);
      exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
      exitButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
  
      newGameButton.addActionListener(e -> {
          endGameDialog.dispose(); // Close dialog
          initGame();
          newGame(); // Reset game
          repaint(); // Refresh game board
      });
  
      exitButton.addActionListener(e -> System.exit(0));
  
      buttonPanel.add(newGameButton);
      buttonPanel.add(exitButton);
  
      // Add panels to dialog
      endGameDialog.add(headerPanel, BorderLayout.CENTER);
      endGameDialog.add(buttonPanel, BorderLayout.SOUTH);
  
      // Display the dialog
      endGameDialog.setVisible(true);
  }

   /** The entry "main" method */
   public static void play() {
      javax.swing.SwingUtilities.invokeLater(() -> {
          JFrame frame = new JFrame(TITLE);
          frame.setUndecorated(false); // Set to true for no window borders
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
          ConnectFour game = new ConnectFour();
          frame.setContentPane(game);
  
          frame.pack();
          frame.setSize(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 50); // Adjust height for status bar
          frame.setResizable(false);
          frame.setLocationRelativeTo(null); // Center on screen
          frame.setVisible(true);
      });
  }
  
}
