/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connectfour;
import java.awt.*;

/**
 * The Board class models the ROWS-by-COLS game board.
 */
public class Board {
   // Updated constants for a 6x7 grid
   public static final int ROWS = 6;  // ROWS x COLS cells
   public static final int COLS = 7;
   public static final int CANVAS_WIDTH = Cell.SIZE * COLS;  // the drawing canvas
   public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;
   public static final int GRID_WIDTH = 8;  // Grid-line's width
   public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2; // Grid-line's half-width
   public static final Color COLOR_GRID = Color.LIGHT_GRAY;  // grid lines
   public static final int Y_OFFSET = 1;  // Fine tune for better display

   // Define properties
   Cell[][] cells; // 2D array of ROWS-by-COLS Cell instances

   /** Constructor to initialize the game board */
   public Board() {
      initGame();
   }

   /** Initialize the game board */
   public void initGame() {
      cells = new Cell[ROWS][COLS];
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col] = new Cell(row, col);
         }
      }
   }

   /** Reset the game board, ready for a new game */
   public void newGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col].newGame();
         }
      }
   }

   /**
    * Drop the player's seed in the selected column, ignoring the row parameter.
    * @return The new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
    */
    public State stepGame(Seed player, int selectedRow, int selectedCol) {
      // Drop to the lowest available row in the selected column
      if (selectedCol >= 0 && selectedCol < COLS) {
         for (int row = ROWS - 1; row >= 0; row--) { // Start from the bottom
            if (cells[row][selectedCol].content == Seed.NO_SEED) {
               cells[row][selectedCol].content = player;
   
               if (hasWon(player, row, selectedCol)) {
                  return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
               }
   
               boolean draw = true;
               for (int r = 0; r < ROWS; ++r) {
                  for (int c = 0; c < COLS; ++c) {
                     if (cells[r][c].content == Seed.NO_SEED) {
                        draw = false;
                        break;
                     }
                  }
               }
   
               return draw ? State.DRAW : State.PLAYING;
            }
         }
      }
      return State.PLAYING;
   }
   

    public boolean hasWon(Seed theSeed, int rowSelected, int colSelected) {
      // Check horizontally (row)
      int count = 0;
      for (int col = 0; col < COLS; ++col) {
         if (cells[rowSelected][col].content == theSeed) {
            count++;
            if (count == 4) return true;
         } else {
            count = 0;
         }
      }
   
      // Check vertically (column)
      count = 0;
      for (int row = 0; row < ROWS; ++row) {
         if (cells[row][colSelected].content == theSeed) {
            count++;
            if (count == 4) return true;
         } else {
            count = 0;
         }
      }
   
      // Check diagonal (\ direction)
      count = 0;
      for (int offset = -3; offset <= 3; ++offset) { // -3 to +3 to check 4 consecutive
         int r = rowSelected + offset;
         int c = colSelected + offset;
         if (r >= 0 && r < ROWS && c >= 0 && c < COLS && cells[r][c].content == theSeed) {
            count++;
            if (count == 4) return true;
         } else {
            count = 0;
         }
      }
   
      // Check anti-diagonal (/ direction)
      count = 0;
      for (int offset = -3; offset <= 3; ++offset) {
         int r = rowSelected + offset;
         int c = colSelected - offset;
         if (r >= 0 && r < ROWS && c >= 0 && c < COLS && cells[r][c].content == theSeed) {
            count++;
            if (count == 4) return true;
         } else {
            count = 0;
         }
      }
   
      return false; // No 4-in-a-line found
   }
   

   /** Paint the board */
   public void paint(Graphics g) {
      // Draw the grid-lines
      g.setColor(COLOR_GRID);
      for (int row = 1; row < ROWS; ++row) {
         g.fillRoundRect(0, Cell.SIZE * row - GRID_WIDTH_HALF,
               CANVAS_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
      }
      for (int col = 1; col < COLS; ++col) {
         g.fillRoundRect(Cell.SIZE * col - GRID_WIDTH_HALF, 0 + Y_OFFSET,
               GRID_WIDTH, CANVAS_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
      }

      // Draw all cells
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col].paint(g);
         }
      }
   }
}
