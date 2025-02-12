import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        // Game board configuration
        int rowCount = 21;
        int columnCount = 19;
        int tileSize = 32;
        int boardWidth = columnCount * tileSize;
        int boardHeight = rowCount * tileSize;

        // Create a JFrame for the game
        JFrame frame = new JFrame("Pac Man");

        // Create the game panel (PacMan)
        PacMan pacmanGame = new PacMan();

        // Set the size of the frame to match the board size
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);  // Center the window on the screen
        frame.setResizable(false);  // Prevent resizing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close the application when the window is closed
        
        // Add the PacMan game panel to the frame
        frame.add(pacmanGame);
        frame.pack();
        pacmanGame.requestFocus();
        
        // Make the frame visible
        frame.setVisible(true);
    }
}
