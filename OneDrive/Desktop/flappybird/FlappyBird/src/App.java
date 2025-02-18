//importing necessary libraries
import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {
        //Setting the board size
        int boardWidth = 360;
        int boardHeight = 640;

        //Setting up the display window of the game
        JFrame frame = new JFrame("Flappy Bird"); //Title at the top
        //frame.setVisible(true); //window visibilty
        frame.setSize(boardWidth, boardHeight); //size
        frame.setLocationRelativeTo(null); //stay in the center
        frame.setResizable(false); //no resizing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close button
        
        FlappyBird flappyBird = new FlappyBird(); //instance of constructor
        frame.add(flappyBird);
        frame.pack(); //size excluding top title bar
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}
