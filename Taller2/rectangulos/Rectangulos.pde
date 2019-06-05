float x;

void setup() {
  size(800, 250);
  background(255);
  smooth();
}

void draw() {
  background(color(241,241,241));

  for (int i = 0; i < 30; i++) {
    noStroke();
    fill(color(6,6,6));
    rect(i * 30, 0, 15, height);
  }

  if (mousePressed == true) {
    background(150);
  }

  for (int q = 0; q < 3; q++) {
    if (q % 3 == 0) {
      fill(color(243,246,0));
    }
    if (q % 3 == 1) {
      fill(color(255,0,0));
    }
    if (q % 3 == 2) {
      fill(color(0,0,168));
    }
    rect(x, q * 80 + 30, 60, 25);
  }

  if (keyPressed == true) {
    stroke(255, 0, 0);
    strokeWeight(3);
    noFill();
    rect(x-1, 49, 62, 157);
  }
  
  x += 0.8;
 
  if (x > width + 10) {
    x = 0;
  }
}
