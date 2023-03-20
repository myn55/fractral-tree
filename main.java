/* Muhammad Nabil */

/*
    KEYMAP
    ------
    MOUSE MOVEMENT -> adjusts the angle of the fractal
    UP & DOWN ARROW KEYS -> INCREASE and DECREASE the end branch limit, respectively
    LEFT & RIGHT ARROW KEYS -> INCREASE the weight of the LEFT and RIGHT side of the tree, respectively (hold SHIFT to DECREASE)
    T -> RESET weights
*/

float angle, theta;
float startingLength = 200;
color treeColor = color(255);

float shrink = 2/3f; // ratio
float endBranchLimit = 5;
float rightWeight = 1;
float leftWeight = 1;
boolean mapLength = false;

boolean shiftHeld = false;

void setup() {
  size(800, 800);
}

void draw() {
  background(30);
  //angle = 40;
  angle = map(mouseX, 0, width, 0, 180);
  theta = radians(angle);
  
  fill(255);
  text(String.format("angle: %f\nebl: %f\nlength: %f\nlW, rW: %f,%f", angle, endBranchLimit, startingLength, leftWeight, rightWeight), 50, 50);
  
  stroke(treeColor);
  fill(255,0,0);
  line(width/2, height, width/2, height-startingLength);
  
  translate(width/2, height-startingLength);
  branch(startingLength);
  
  // Constant key pressing
  if (keyPressed) {
    if (key == CODED) {
      if (keyCode == UP) {
        endBranchLimit += 0.1;
      } else if (keyCode == DOWN) {
        endBranchLimit -= endBranchLimit-0.1 > 0 ? 0.1 : 0;
      } else if (keyCode == LEFT) {
        leftWeight += shiftHeld ? 0.05 : -0.05;
      } else if (keyCode == RIGHT) {
        rightWeight += shiftHeld ? 0.05 : -0.05;
      }
    } else {
      if (key == ' ') {
        mapLength = !mapLength;
      } else if (key == 't' || key == 'T') {
        println("Resetting weights");
        rightWeight = 1;
        leftWeight = 1;
      }
    }
  }
}

// Singe key pressed
void keyPressed() {
  if (key == CODED) {
    // Modifiers
    if (keyCode == SHIFT) {
      shiftHeld = true;
    }
  }
}

// Single key released
void keyReleased() {
  if (key == CODED) {
    if (keyCode == SHIFT) {
      shiftHeld = false;
    }
  }
}

void branch(float l) {
  l *= shrink;
  
  if (l > endBranchLimit) {
    push();
    rotate(theta/rightWeight);
    line(0, 0, 0, -l);
    translate(0, -l);
    branch(l);
    pop();
    
    push();
    rotate(-theta/leftWeight);
    line(0, 0, 0, -l);
    translate(0, -l);
    branch(l);
    pop();
  }
}
