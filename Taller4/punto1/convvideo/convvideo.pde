import processing.video.*; 
PImage img;
Movie colorMovie;
PShape can;
float angle;

PShader texShader;

void setup() {
  size(640, 640, P3D);  
  colorMovie = new Movie(this, "video.mov");
  colorMovie.loop();
  img = colorMovie;
  can = createCan(200, 400, 32, img);
}

void draw() {    
  texShader = loadShader("mascara.glsl");
  int value =int(map(mouseX,0,640,0,7));
  background(0);
  shader(texShader);
  if(value==0){
  texShader.set("c0",0);
  texShader.set("c1",0);
  texShader.set("c2",0);
  texShader.set("c3",0);
  texShader.set("c4",1.0);
  texShader.set("c5",0);
  texShader.set("c6",0);
  texShader.set("c7",0);
  texShader.set("c8",0);
  println("Video original: "+ frameRate + ":fps");
  }
  if(value==1){
  texShader.set("c0", 0.0);
  texShader.set("c1",-1.0);
  texShader.set("c2", 0.0);
  texShader.set("c3",-1.0);
  texShader.set("c4", 5.0);
  texShader.set("c5",-1.0);
  texShader.set("c6", 0.0);
  texShader.set("c7",-1.0);
  texShader.set("c8", 0.0);
  println("Video en filtro enfoque: "+ frameRate + ":fps");
  }
  if(value==2){
  texShader.set("c0",-2.0);
  texShader.set("c1",-1.0);
  texShader.set("c2", 0.0);
  texShader.set("c3",-1.0);
  texShader.set("c4", 1.0);
  texShader.set("c5", 1.0);
  texShader.set("c6", 0.0);
  texShader.set("c7", 1.0);
  texShader.set("c8", 2.0);
  println("Video en filtro repujado: "+ frameRate + ":fps");
  }
  if(value==3){
  texShader.set("c0", 1.0);
  texShader.set("c1",-2.0);
  texShader.set("c2", 1.0);
  texShader.set("c3",-2.0);
  texShader.set("c4", 5.0);
  texShader.set("c5",-2.0);
  texShader.set("c6", 1.0);
  texShader.set("c7",-2.0);
  texShader.set("c8", 1.0);
  println("Video en filtro sharpen: "+ frameRate + ":fps");
  }
  if(value==4){
  texShader.set("c0", 0.0);
  texShader.set("c1", 0.0);
  texShader.set("c2", 0.0);
  texShader.set("c3",-1.0);
  texShader.set("c4", 1.0);
  texShader.set("c5", 0.0);
  texShader.set("c6", 0.0);
  texShader.set("c7", 0.0);
  texShader.set("c8", 0.0);
  println("Video en filtro realce de bordes: "+ frameRate + ":fps");
  }
  if(value==5){
  texShader.set("c0", 1.0);
  texShader.set("c1", 1.0);
  texShader.set("c2", 1.0);
  texShader.set("c3", 1.0);
  texShader.set("c4",-2.0);
  texShader.set("c5", 1.0);
  texShader.set("c6",-1.0);
  texShader.set("c7",-1.0);
  texShader.set("c8",-1.0);
  println("Video en filtro direccional norte: "+ frameRate + ":fps");
  }
  if(value==6){
  texShader.set("c0",-1.0);
  texShader.set("c1",-1.0);
  texShader.set("c2",-1.0);
  texShader.set("c3",-1.0);
  texShader.set("c4",8.0);
  texShader.set("c5",-1.0);
  texShader.set("c6",-1.0);
  texShader.set("c7",-1.0);
  texShader.set("c8",-1.0);
  println("Video en filtro realce de bordes: "+ frameRate + ":fps");
  }
 
  translate(width/2, height/2);
  rotateY(angle);  
  shape(can);  
  angle += 0.01;
}

PShape createCan(float r, float h, int detail, PImage tex) {
  textureMode(NORMAL);
  PShape sh = createShape();
  sh.beginShape(QUAD_STRIP);
  sh.noStroke();
  sh.texture(tex);
  for (int i = 0; i <= detail; i++) {
    float angle = TWO_PI / detail;
    float x = sin(i * angle);
    float z = cos(i * angle);
    float u = float(i) / detail;
    sh.normal(x, 0, z);
    sh.vertex(x * r, -h/2, z * r, u, 0);
    sh.vertex(x * r, +h/2, z * r, u, 1);    
  }
  sh.endShape(); 
  return sh;
}

void movieEvent(Movie m) {
  m.read();
}
