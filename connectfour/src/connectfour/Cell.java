/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connectfour;
import java.awt.*;
/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
   // Define named constants for drawing
   public static final int SIZE = 120; // cell width/height (square)
   // Symbols (cross/nought) are displayed inside a cell, with padding from border
   public static final int PADDING = SIZE / 5;
   public static final int SEED_SIZE = SIZE - PADDING * 2;

   // Define properties (package-visible)
   /** Content of this cell (Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT) */
   Seed content;
   /** Row and column of this cell */
   int row, col;

   /** Constructor to initialize this cell with the specified row and col */
   public Cell(int row, int col) {
      this.row = row;
      this.col = col;
      content = Seed.NO_SEED;
   }

   /** Reset this cell's content to EMPTY, ready for new game */
   public void newGame() {
      content = Seed.NO_SEED;
   }

   /** Paint itself on the graphics canvas, given the Graphics context */
   public void paint(Graphics g) {
       Graphics2D g2d = (Graphics2D) g;
   
       // Aktifkan Anti-Aliasing
       g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
   
       // Clear the cell background
       g2d.setColor(Board.COLOR_BG); // Background color
       g2d.fillRect(col * SIZE, row * SIZE, SIZE, SIZE);
   
       // Center dan radius
       int centerX = col * SIZE + SIZE / 2;
       int centerY = row * SIZE + SIZE / 2;
       int radius = SIZE / 2 - PADDING;
   
      //  // Shadow (bayangan)
      //  g2d.setColor(new Color(0, 0, 0, 50)); // Bayangan transparan
      //  g2d.fillOval(centerX - radius + 3, centerY - radius + 3, radius * 2, radius * 2);
   
       // Lingkaran utama (abu-abu gelap untuk kosong)
       if (content == Seed.NO_SEED) {
           g2d.setColor(Color.WHITE);
           g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
   
           // Highlight (pencahayaan)
           g2d.setColor(new Color(255, 255, 255, 100)); // Highlight transparan
           g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
       } else if (content == Seed.CROSS) {
           // Lingkaran kuning (Player A)
           g2d.setColor(Color.YELLOW);
           g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
       } else if (content == Seed.NOUGHT) {
           // Lingkaran biru (Player B)
           g2d.setColor(Color.RED);
           g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
       }
   }
   
   
   
   

}
