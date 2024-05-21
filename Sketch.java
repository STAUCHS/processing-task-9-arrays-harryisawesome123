import processing.core.PApplet;

/**
 * A program Sketch.java that draws a game with a moveable blue circle. Snowflakes fall from the top.
 * each collision removes a life and once three collisions occur the game ends to a white screen.
 * 
 * @author H. Rahukulan
 */

public class Sketch extends PApplet {

  // Related arrays for the (x, y) coordinated of the snowflakes
  float[] snowX = new float[42];
  float[] snowY = new float[42];
  float[] SnowDiamter = new float[42];
  boolean[] blnHideStatus = new boolean[42];

  // Circle position variables
  float characterX = 100;
  float characterY = 100;

  // Circle movement variables
  boolean blnUpKeyCircle = false;
  boolean blnDownKeyCircle = false;
  boolean blnLeftKeyCircle = false;
  boolean blnRightKeyCircle = false;

  // Lives variable
  int intLives = 3;

  /**
   * Called once at the beginning of execution.
   * 
   * @author H. Rahukulan
   */
  public void settings() {
    size(400, 400);
  }

  /**
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   * 
   * @author H. Rahukulan
   */
  public void setup() {
    background(0);

    // Generate random x and y values for snowflakes
    for (int i = 0; i < snowX.length; i++) {
      snowX[i] = random(width);
      snowY[i] = random(-height, 0);
    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here.
   * 
   * @author H. Rahukulan
   */
  public void draw() {
    // Game over end screen
    if (intLives == 0) {
      background(255);
      textSize(50);
      text("Game Over", 85, 200);
    }

    // Draws while game is still continuing
    else {
      background(0);

      // Moves Circle
      if (blnUpKeyCircle) {
        characterY -= 2 ;
      }

      if (blnDownKeyCircle) {
        characterY += 2;
      }

      if (blnLeftKeyCircle) {
        characterX -= 2;
      }

      if (blnRightKeyCircle) {
        characterX += 2;
      }
      
      // Keeping blue player circle on the screen
      characterX = constrain (characterX, 0, width - 20);
      characterY = constrain (characterY, 0, height - 20);

      // Draw blue circle player
      fill(52, 61, 235);
      ellipse(characterX, characterY, 20, 20);
      
      // Draw Snow
      fill(255);
      snow();

      // Draw lives
      drawLives();
    }
  }

  /**
   * Checks for collisions with blue circle player and changes snowflake positions.
   * 
   * @author H. Rahukulan
   */
  public void snow() {
    for (int i = 0; i < snowX.length; i+=2) {
      if (!blnHideStatus[i]) {
        SnowDiamter[i] = 20;
        circle(snowX[i], snowY[i], SnowDiamter[i]);

        snowY[i] += 2;

        // Reset snowflakes
        if (snowY[i] > height) {
          snowY[i] = random(-height, 0);
          snowX[i] = random(width);
        }

        // Slows down for up arrow pressed and speeds for down arrow pressed
        if (keyPressed) {
          if (keyCode == DOWN) {
            snowY[i] += 1;
          }

          else if (keyCode == UP) {
            snowY[i] -= 0.5;
          }
        }

        // Collision detection with player
        if (dist(snowX[i], snowY[i], characterX, characterY) < SnowDiamter[i] / 2 + 10) {
          snowY[i] = 0;
          intLives--;
          if (intLives == 0) {
            background(255);
          }
        }
      }
    }
  }

  /**
   * Handles player movement using wasd keys.
   * 
   * @author H. Rahukulan
   */
  public void keyPressed() {

    if (key == 'w') {
      blnUpKeyCircle = true;
    }

    else if (key == 's') {
      blnDownKeyCircle = true;
    }

    else if (key == 'a') {
      blnLeftKeyCircle = true;
    }

    else if (key == 'd') {
      blnRightKeyCircle = true;
    }
  }
  
  /**
   * Handles player movement using wasd keys.
   * 
   * @author H. Rahukulan
   */
  public void keyReleased() {
    if (key == 'w') {
      blnUpKeyCircle = false;
    }

    else if (key == 's') {
      blnDownKeyCircle = false;
    }

    else if (key == 'a') {
      blnLeftKeyCircle = false;
    }

    else if (key == 'd') {
      blnRightKeyCircle = false;
    }
  }

  /**
   * Handles snowflake repositioning on mouse click.
   * 
   * @author H. Rahukulan
   */
  public void mousePressed() {
    for (int i = 0; i < snowX.length; i++) {
      if (dist(snowX[i], snowY[i], mouseX, mouseY) < SnowDiamter[i]) {
        blnHideStatus[i] = true;
      }
    }
  }

  /**
   * Handles lives of player.
   * 
   * @author H. Rahukulan
   */
  public void drawLives() {
    fill(255, 0, 0);
    for (int i = 0; i < intLives; i++) {
      rect(width - 30 - i * 30, 30, 25, 25);
    }
  }
}
