int numeropuntos;
int FN;
float amplitud;
boolean revelacion=false;

void setup() {
  size(300, 300);
  frameRate(50);
  numeropuntos=16;
  FN=0;
  amplitud=1;
  noStroke();
  colorMode(HSB, 360, 255, 255);
}

void draw() {
  background(0, 0, 0);
  pushMatrix();
  translate(width/2, height/2);
  if (revelacion) {
    revelacio();
  }
  for (int i=0; i<numeropuntos; i++) {
    rotate(TWO_PI/numeropuntos);
    //sin colores
    fill(255);
    //colores para notar los ejes
    //fill(360*i/N, 255, 255);
    ellipse(50*(1.1+amplitud*cos(.075*FN+PI*6*i/numeropuntos)), 0, 8, 8);
  }
  popMatrix();
  FN++;
}

void revelacio() {
  stroke(100);
  for (int i=0; i<numeropuntos; i++) {
    rotate(TWO_PI/numeropuntos);  
    line(150, 0, 5, 0);
  }
}

void mousePressed() {
  revelacion=!revelacion;
}
