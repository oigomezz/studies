PImage label;
PShape sh;
float angle;
PShader texShader;

void setup() {
  size(640, 360, P3D);
  label = loadImage("rose.jpg");
  sh = createShape(RECT, 150, 0, 300, 300);
  sh.setTexture(label);
}
void draw() {
  texShader = loadShader("mascara.glsl");
  int value =int(map(mouseX,0,640,0,7));
  shader(texShader);
  background(0);
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
  println("Imagen original: "+ frameRate + ":fps");
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
  println("Imagen en filtro enfoque: "+ frameRate + ":fps");
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
  println("Imagen en filtro repujado: "+ frameRate + ":fps");
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
  println("Imagen en filtro sharpen: "+ frameRate + ":fps");
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
  println("Imagen en filtro realce de bordes: "+ frameRate + ":fps");
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
  println("Imagen en filtro direccional norte: "+ frameRate + ":fps");
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
  println("Imagen en filtro realce de bordes: "+ frameRate + ":fps");
  }
 
  shape(sh);
  
}
