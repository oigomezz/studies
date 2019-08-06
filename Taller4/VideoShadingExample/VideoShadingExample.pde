import processing.video.*;
import peasy.*;

PShader sobelShader;
PGraphics buffer;

Movie mov;

PeasyCam cam;

float canvasWidth = 480;
float canvasHeight = 360;

void setup()
{
  size(500, 500, P3D);
  cam = new PeasyCam(this, 500);

  // load movie
  mov = new Movie(this, "city.mov");
  mov.loop();

  sobelShader = loadShader("sobelFrag.glsl");
  buffer = createGraphics(mov.width, mov.height, P2D);
  buffer.shader(sobelShader);
}

void draw()
{
  // shade the incoming movie image
  buffer.beginDraw();
  buffer.background(0, 0);
  buffer.image(mov, 0, 0);
  buffer.endDraw();

  // create 3d scene
  background(0);

  pushMatrix();

  // rotate for more interesting 3d magic
  rotateX(radians(frameCount % 360));
  rotateZ(radians(frameCount % 360));

  // use shaded video as texture in the 3d scene
  rectMode(CENTER);
  beginShape();
  textureMode(IMAGE);
  texture(buffer);
  vertex(canvasWidth / -2f, canvasHeight / -2f, 0, 0);
  vertex(canvasWidth / 2f, canvasHeight / -2f, buffer.width, 0);
  vertex(canvasWidth / 2f, canvasHeight / 2f, buffer.width, buffer.height);
  vertex(canvasWidth / -2f, canvasHeight / 2f, 0, buffer.height);
  endShape();

  popMatrix();
}

void movieEvent(Movie m) {
  m.read();
}