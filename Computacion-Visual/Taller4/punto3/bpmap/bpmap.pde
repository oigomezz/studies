PShader myShader;
PShape sh;
float[] mouse ={0,0,0};
PImage  normalMap,diffuseMap;
float[] spec ={0,0,0};
float dizzy=0;
float force = 0;
float doSpec = 0;

void setup(){
    size(640, 500, P3D);
    myShader = loadShader("frag.glsl","vert.glsl");
    sh = createShape(RECT, 0, 0, 640, 500);
    diffuseMap = loadImage("d.png");
    normalMap = loadImage("n.png");
}  
void mousePressed() {
  doSpec = 1;
  PVector v = new PVector(random(0,0.5), random(0,0.5), random(0,.5));
  v.normalize();
  spec[0] = v.x;
  spec[1] = v.y;
  spec[2] = v.z;
}

void mouseReleased() {
  doSpec = 0;
}

void keyPressed(){
  dizzy = dizzy == 1 ? 0 : 1;
  if(dizzy == 1){
    force = 1;
  }
}

void draw() {
  if(dizzy == 1){
    force *= 0.98;
  }
  myShader.set("iTime", float(millis()));
  myShader.set("iResolution", float(640), float(500));
  myShader.set("iCursor", float(mouseX), float(mouseY), doSpec);

  myShader.set("diffuseMap", diffuseMap);
  myShader.set("normalMap", normalMap);

  myShader.set("iSpecColor", spec[0],spec[1],spec[2]);
  myShader.set("dizzy", dizzy);
  myShader.set("force", 1-force);
  
  background(0);
  shader(myShader);
  shape(sh);
}
