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
   public static final Color COLOR_CROSS = new Color(239, 105, 80); // Red #EF6950
   public static final Color COLOR_NOUGHT = new Color(64, 154, 225); // Blue #409AE1
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
            int mouseX = e.getX();
            int mouseY = e.getY();
            // Get the row and column clicked
            int row = mouseY / Cell.SIZE;
            int col = mouseX / Cell.SIZE;

            if (currentState == State.PLAYING) {
               SoundEffect.EAT_FOOD.play();
               if (col >= 0 && col < Board.COLS) {
                  // Look for an empty cell starting from the bottom row
                  for (int rowI = Board.ROWS - 1; rowI >= 0; rowI--) {
                     if (board.cells[rowI][col].content == Seed.NO_SEED) {
                        currentState = board.stepGame(currentPlayer, rowI, col); // Update state
                        // Switch player
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        break;
                     }
                  }
               }
            } else {
               if (currentState == State.CROSS_WON) {
                  showWinnerDialog("Cross Wins!");
               } else if (currentState == State.NOUGHT_WON) {
                  showWinnerDialog("Nought Wins!");
               } else if (currentState == State.DRAW) {
                  showWinnerDialog("It's a Draw!");
               }
            }
            repaint();
         }
      });

      // Setup the status bar (JLabel) to display status message
      statusBar = new JLabel();
      statusBar.setFont(FONT_STATUS);
      statusBar.setBackground(COLOR_BG_STATUS);
      statusBar.setOpaque(true);
      statusBar.setPreferredSize(new Dimension(300, 30));
      statusBar.setHorizontalAlignment(JLabel.LEFT);
      statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

      super.setLayout(new BorderLayout());
      super.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
      super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
      // account for statusBar in height
      super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

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
         statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
      } else if (currentState == State.DRAW) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("It's a Draw! Click to play again.");
         SoundEffect.DIE.play();
      } else if (currentState == State.CROSS_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("'X' Won! Click to play again.");
         SoundEffect.EXPLODE.play();
      } else if (currentState == State.NOUGHT_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("'O' Won! Click to play again.");
         SoundEffect.EXPLODE.play();
      }
   }

   private void showWinnerDialog(String message) {
      int response = JOptionPane.showOptionDialog(this,
            message + "\nDo you want to play again?","Game Over",
            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, 
            null, new Object[] {"Yes", "No"}, "Yes"); 

      if (response == JOptionPane.YES_OPTION) {
         newGame();
         currentState = State.PLAYING;
         currentPlayer = Seed.CROSS;
         repaint();
      } else {
         System.exit(0);
      }
   }

   /** The entry "main" method */
   public static void play() {
      // Run GUI construction codes in Event-Dispatching thread for thread safety
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            JFrame frame = new JFrame(TITLE);
            // Set the content-pane of the JFrame to an instance of main JPanel
            frame.setContentPane(new ConnectFour());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // center the application window
            frame.setVisible(true); // show it
         }
      });
   }
}
