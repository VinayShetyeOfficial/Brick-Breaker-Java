import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle; // For intersection of paddle for rebound of ball.
import java.awt.event.ActionEvent; // Auto added for Unimplemented methods.
import java.awt.event.ActionListener; // Importing package of ActionListener.
import java.awt.event.KeyEvent; // Auto added for Unimplemented methods.
import java.awt.event.KeyListener; // Importing package of KeyListener.

import javax.swing.Timer;
import javax.swing.JPanel;

/**
 * GamePlay class is responsible for handling the gameplay of the game.
 * It implements KeyListener and ActionListener to respond to user input 
 * and handle the movement of the ball and the paddle.
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener {
  // To prevent auto playing of the game when its starts.
  private boolean play = false;

  // Setting initial Score to 0.
  private int score = 0;

  // 7x3 mapped so 21. (Can change it later.)
  private int totalBricks = 21;

  // Setting time of ball, how fast it should move.
  private Timer timer;

  // Setting the speed of the timer, how fast it should run.
  private int delay = 10;

  // Starting position of slider.
  private int playerX = 310;

  // Starting position of Ball (X-axis).
  private int ballposX = 120;

  // Starting position of Ball (X-axis).
  private int ballposY = 350;

  // Setting the direction of Ball (X-axis).
  private int ballXdir = -1;

  // Setting the direction of Ball (Y-axis).
  private int ballYdir = -2;

  // Declare instance variable for MapGenarator Class
  private MapGenerator map;

  /**
   * GamePlay constructor creates a new MapGenerator object and 
   * sets the focusable state and starts the timer.
   */
  public GamePlay() {
    // Create object of MapGenarator Class
    map = new MapGenerator(3, 7);

    // Use to respond to key events
    addKeyListener(this);

    // Enables component to gain focus once the Frame is displayed.
    setFocusable(true);

    setFocusTraversalKeysEnabled(false);

    // Create a new Timer object with the specified delay and action listener.
    timer = new Timer(delay, this);

    // Start the timer.
    timer.start();
  }

  /**
   * paint method is responsible for drawing the game elements on the screen.
   * It sets the background color, draws the map, borders, scores, paddle and ball.
   * It also checks for game over and win conditions.
   */

  public void paint(Graphics g) {
    // background
    g.setColor(Color.black); // to set background color.
    g.fillRect(1, 1, 692, 592); // create rectangle.

    // drawing map
    map.draw((Graphics2D) g);

    // borders
    g.setColor(Color.yellow); // to set border color.
    g.fillRect(0, 0, 3, 592); // create rectangle.
    g.fillRect(0, 0, 692, 3); // create rectangle.
    g.fillRect(691, 0, 3, 592); // create rectangle.

    // scores
    g.setColor(Color.white);
    g.setFont(new Font("serif", Font.BOLD, 25));
    g.drawString("" + score, 590, 30);

    // the paddle
    g.setColor(Color.green);
    g.fillRect(playerX, 550, 100, 8);

    // the ball
    g.setColor(Color.yellow);
    //g.fillRect(ballposX, ballposY, 20, 20);
    g.fillOval(ballposX, ballposY, 20, 20);

    if (totalBricks <= 0) {
      play = false;
      ballXdir = 0;
      ballYdir = 0;
      g.setColor(Color.red);
      g.setFont(new Font("serif", Font.BOLD, 30));
      g.drawString("You won: " + score, 260, 300);

      g.setFont(new Font("serif", Font.BOLD, 20));
      g.drawString("Press Enter to Restart", 230, 350);
    }

    // For Game Over
    if (ballposY > 570) {
      play = false;
      ballXdir = 0;
      ballYdir = 0;
      g.setColor(Color.red);
      g.setFont(new Font("serif", Font.BOLD, 30));
      g.drawString("Game Over, Score: " + score, 190, 300);

      g.setFont(new Font("serif", Font.BOLD, 20));
      g.drawString("Press Enter to Restart", 230, 350);

    }

    g.dispose(); // Disposes the graphics content and releases any system resources that it is using.
    // Graphic object ('g') cannot be used after dispose() has been called.

  }

  /**
   * The actionPerformed method is called when the timer fires an action event.
   * It is responsible for updating the position of the ball, paddle and checking for collisions.
   */
  public void actionPerformed(ActionEvent e) {
    timer.start();
    if (play) {
      if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
        ballYdir = -ballYdir;
      }

      A: for (int i = 0; i < map.map.length; i++) {
        for (int j = 0; j < map.map[0].length; j++) {
          if (map.map[i][j] > 0) {
            int brickX = j * map.brickWidth + 80;
            int brickY = i * map.brickHeight + 50;
            int brickWidth = map.brickWidth;
            int brickHeight = map.brickHeight;

            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
            Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
            Rectangle brickRect = rect;

            if (ballRect.intersects(brickRect)) {
              map.setBrickValue(0, i, j);
              totalBricks--;
              score += 5;

              if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                ballXdir = -ballXdir;
              } else {
                ballYdir = -ballYdir;
              }
              break A;
            }
          }
        }
      }

      ballposX += ballXdir;
      ballposY += ballYdir;
      if (ballposX < 0) {
        ballXdir = -ballXdir;
      }
      if (ballposY < 0) {
        ballYdir = -ballYdir;
      }
      if (ballposX > 670) {
        ballXdir = -ballXdir;
      }
    }

    repaint();
  }

  /**
   * The keyPressed method is called when a key on the keyboard is pressed.
   * It is responsible for moving the paddle left or right when the left or right arrow keys are pressed.
   */

  @Override
  public void keyPressed(KeyEvent e) {
    // keyPressed is invoked when a physical key is pressed down. Uses KeyCode, int Output.
    // Check if the RIGHT key is pressed
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      // Check if the player's x coordinate is greater than or equal to 600
      if (playerX >= 600) {
        // If so, set the player's x coordinate to 600 (to prevent it from moving out of bounds)
        playerX = 600;
      } else {
        // Otherwise, call the moveRight() method to move the player to the right
        moveRight();
      }
    }

    // Check if the LEFT key is pressed
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      // Check if the player's x coordinate is less than 10
      if (playerX < 10) {
        // If so, set the player's x coordinate to 10 (to prevent it from moving out of bounds)
        playerX = 10;
      } else {
        // Otherwise, call the moveLeft() method to move the player to the left
        moveLeft();
      }
    }

    // Check if the ENTER key is pressed and the game is not in play
    if (e.getKeyCode() == KeyEvent.VK_ENTER && !play) {
      // Set play to true and reset the game values
      play = true;
      ballposX = 120;
      ballposY = 350;
      ballXdir = -1;
      ballYdir = -2;
      playerX = 310;
      score = 0;
      totalBricks = 21;
      map = new MapGenerator(3, 7);

      // Call the repaint() method to update the game screen
      repaint();
    }
  }

  // Method to move the player to the right
  public void moveRight() {
    play = true;
    playerX += 20;
  }

  // Method to move the player to the left
  public void moveLeft() {
    play = true;
    playerX -= 20;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // We don't need to implement this method as it is not necessary for the game's functionality
  }

  @Override
  public void keyTyped(KeyEvent arg0) {
    // We don't need to implement this method as it is not necessary for the game's functionality
  }
}