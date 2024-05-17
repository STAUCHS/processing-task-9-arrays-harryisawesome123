import processing.core.PApplet;

public class Sketch extends PApplet {

  // Related arrays for the (x, y) coordinated of the snowflakes
  float[] snowX = new float[42];
  float[] snowY = new float[42];
  int intsnowDiameter = 10;

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

  public void settings() {
    size(400, 400);
  }

  public void setup() {
    background(0);

    // Generate random x and y values for snowflakes
    for (int i = 0; i < snowX.length; i++) {
      snowX[i] = random(width);
      snowY[i] = random(height);
    }
  }

  public void draw() {
    if (intLives == 0) {
      background(255);
      text("Game Over", 50, 50);
    }

    else {
    background(0);

    // Moves Circle
    if (blnUpKeyCircle) {
      characterY -=2 ;
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

  // All other defined methods are written below:
  public void snow() {
    for (int i = 0; i < snowX.length; i+=2) {
      circle(snowX[i], snowY[i], intsnowDiameter);

      snowY[i] += 2;

      // Reset snowflakes
      if (snowY[i] > height) {
        snowY[i] = 0;
      }

      // Slows down for up arrow pressed and speeds for down arrow pressed
      if (keyPressed) {
        if (keyCode == DOWN) {
          snowY[i] += 1;
        }

        else if (keyCode == UP) {
          snowY[i] -= 1;
        }
      }
      // Collision detection with player
      if (dist(snowX[i], snowY[i], characterX, characterY) < intsnowDiameter / 2 + 10) {
        snowY[i] = 0;
        intLives--;
        if (intLives == 0) {
          background(255);
        }
      }
    }
  }

  // Handles multiple key inputs for character movement
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

  public void drawLives() {
    fill(255, 0, 0);
    for (int i = 0; i < intLives; i++) {
      rect(width - 30 - i * 30, 30, 25, 25);
    }
  }
}
