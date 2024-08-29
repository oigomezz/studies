// http://illusionoftheyear.com/2008/05/filling-in-the-afterimage-after-the-image/

int nextTime;
int step;
int i, j;
boolean showColors;
void setup() {
  size(420, 300);
  j=0;
  step = 680;
  // wait for first frame -- aligned to step, it will occur between step and 2step-1
  nextTime = millis()-millis()%step + 2*step;

}

void draw() {
  background(255);
  fill(255);
  
  while (millis()<nextTime) {
    // do nothing while running out the 'slack' in the clock
  }
  
  strokeWeight(2);
  point(width*0.5,height*0.5);
  strokeWeight(1);
  
  switch(j){
    case 0:
            pushMatrix();
              translate(width*0.25, height*0.5);
              star(0, 0, 30, 80, 4);
            popMatrix();
            
            pushMatrix();
              translate(width*0.75, height*0.5);
              rotate(PI/4.0);
              star(0, 0, 30, 80, 4);
            popMatrix();
            
            j++;
            break;
    case 1:
            pushMatrix();
              translate(width*0.25, height*0.5);  
              rotate(PI/4.0);
              star(0, 0, 30, 80, 4); 
            popMatrix();
            
            pushMatrix();
              translate(width*0.75, height*0.5);  
              star(0, 0, 30, 80, 4); 
            popMatrix();
            
            j++;
            break;
    case 2:
            noStroke();
            pushMatrix();
              translate(width*0.25, height*0.5);
              fill(107, 195, 181); //Blue color          
              star(0, 0, 30, 80, 4);          
              
              rotate(PI/4.0);
              fill(220, 148, 152); //Pink color             
              star(0, 0, 30, 80, 4); 
              rotate(PI/2.63);
              
              fill(173,169,180);         
              polygon(0, 0, 41, 4);  // Square1
              rotate(PI/4.0);
              polygon(0, 0, 41, 4);  // Square1            
            popMatrix();
            
            pushMatrix();
              translate(width*0.75, height*0.5);
              fill(107, 195, 181); //Blue color          
              star(0, 0, 30, 80, 4);          
              
              rotate(PI/4.0);
              fill(220, 148, 152); //Pink color             
              star(0, 0, 30, 80, 4); 
              rotate(PI/2.63);
              
              
              fill(173,169,180);         
              polygon(0, 0, 41, 4);  // Square1
              rotate(PI/4.0);
              polygon(0, 0, 41, 4);  // Square1                          
            popMatrix();
            
            stroke(0);
            
            j=0;
            break;
  }  

  nextTime = nextTime + step;
}

void polygon(float x, float y, float radius, int npoints) {
  float angle = TWO_PI / npoints;
  beginShape();
  for (float a = 0; a < TWO_PI; a += angle) {
    float sx = x + cos(a) * radius;
    float sy = y + sin(a) * radius;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}

void star(float x, float y, float radius1, float radius2, int npoints) {
  float angle = TWO_PI / npoints;
  float halfAngle = angle/2.0;
  beginShape();
  for (float a = 0; a < TWO_PI; a += angle) {
    float sx = x + cos(a) * radius2;
    float sy = y + sin(a) * radius2;
    vertex(sx, sy);
    sx = x + cos(a+halfAngle) * radius1;
    sy = y + sin(a+halfAngle) * radius1;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}
