import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
    // 2D array that will contain all the bricks
    public int map[][];
    // Width and height of each brick
    public int brickWidth;
    public int brickHeight;

    /**
     * Constructor for creating a map with a given number of rows and columns
     *
     * @param row number of rows in the map
     * @param col number of columns in the map
     */
    public MapGenerator(int row, int col){
        // Initialize map with the given number of rows and columns
        map = new int[row][col];

        // Fill the map with bricks (value of 1)
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = 1;
            }
        }

        // Set the width and height of each brick based on the total width and height of the map
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    /**
     * Draws the map using the given Graphics2D object
     *
     * @param g Graphics2D object used for drawing the map
     */
    public void draw(Graphics2D g){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] > 0){
                    // Fill brick with white color
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    // Draw black border around brick
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    /**
     * Sets the value of the brick at a given position in the map
     *
     * @param value new value for the brick
     * @param row row position of the brick
     * @param col column position of the brick
     */
    public void setBrickValue(int value, int row, int col){
        map[row][col] = value;
    }
}