import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.random.*;
import javax.swing.*;
import java.util.HashSet;

public class PacMan extends JPanel implements ActionListener, KeyListener{
    class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;
        char direction = 'U';
        int velocityX = 0;
        int velocityY = 0;

        Block(Image image, int x, int y, int width, int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }
        void updateDirection(char direction){
            this.direction = direction;
            updateVelocity();
        }

           void updateVelocity(){
                if (this.direction == 'U'){
                    this.velocityX = 0;
                    this.velocityY = -tileSize/4;
                }
                else if (this.direction == 'D'){
                    this.velocityX = 0;
                    this.velocityY = tileSize/4;
                }
                else if (this.direction == 'L') {
                    this.velocityX = -tileSize/4;
                    this.velocityY = 0;
                }
                else if (this.direction == 'R'){
                    this.velocityX = tileSize/4;
                    this.velocityY = 0;
                }
        }
    }

    private int rowCount = 21;
    private int columnCount = 19;
    private int tileSize = 32;
    private int boardWidth = columnCount * tileSize;
    private int boardHeight = rowCount * tileSize;

    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;
    private Image pacmanUpImage; 
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    // X = wall, O = skip, P = pacman, ' ' = food
    // Ghosts: b = blue, o = orange, p = pink, r = red
    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;

    Timer gameLoop;

    PacMan() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));  // Set the preferred size of the panel
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        
        // Load images
        wallImage = new ImageIcon(getClass().getResource("./wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("./blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("./orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("./pinkGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("./redGhost.png")).getImage();

        // Movements
        pacmanUpImage = new ImageIcon(getClass().getResource("./pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("./pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("./pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("./pacmanRight.png")).getImage();
    
    loadMap();
    gameLoop = new Timer(50, this);
    gameLoop.start();
    }

    public void loadMap() {
        walls = new HashSet<>();
        foods = new HashSet<>();
        ghosts = new HashSet<>();

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                String row = tileMap[r];
                char tileChar = row.charAt(c);

                int x = c * tileSize;
                int y = r * tileSize;

                if (tileChar == 'X') {
                    Block wall = new Block(wallImage, x, y, tileSize, tileSize);
                    walls.add(wall);
                } 
                else if (tileChar == 'b') {
                    Block ghost = new Block(blueGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } 
                else if (tileChar == 'o') {
                    Block ghost = new Block(orangeGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if (tileChar == 'p') {
                    Block ghost = new Block(pinkGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if (tileChar == 'r') {
                    Block ghost = new Block(redGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if (tileChar == 'P') {
                    pacman = new Block(pacmanRightImage, x, y, tileSize, tileSize);
                }
                else if (tileChar == ' ') {
                    Block food = new Block(null, x + 14, y + 14, 4, 4);
                    foods.add(food);
                }
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
     g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);
    
     for(Block ghost : ghosts){
        g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
     } 
     for (Block wall : walls){
        g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
     }

     g.setColor(Color.WHITE);
     for (Block food : foods){
        g.fillRect(food.x, food.y, food.width, food.height);
     }
    }

    public void move(){
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    move();
    repaint();   
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
     }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("KeyEvent " + e.getKeyCode());
    if(e.getKeyCode() == KeyEvent.VK_UP){
        pacman.updateDirection('U');
    }
    else if(e.getKeyCode() == KeyEvent.VK_DOWN){
        pacman.updateDirection('D');
    }
    else if(e.getKeyCode() == KeyEvent.VK_LEFT){
        pacman.updateDirection('L');
    }
    else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
        pacman.updateDirection('R');
    }
    }
}
