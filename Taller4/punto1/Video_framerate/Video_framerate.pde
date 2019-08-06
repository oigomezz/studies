  
import processing.video.*;
Movie myMovie;
PGraphics img,img_convolution,img_grey;

float[][] focus = { { 0, -1, 0 },
                     { -1,  5, -1 },
                     { 0, -1, 0 } };
float[][] embossment = { { -2, -1, 0 },
                     { -1,  1, 1 },
                     { 0, 1, 2 } };
float[][] sharpen = { { 1, -2, 1 },
                     { -2,  5, -2 },
                     { 1, -2, 1 } };
float[][] edgeEnhancement = { { 0, 0, 0 },
                     { -1,  1, 0 },
                     { 0, 0, 0 } };
float[][] north = { { 1, 1, 1 },
                     { 1,  -2, 1 },
                     { -1, -1, -1 } };
PFont f;                   
                     
void setup() {
  size(640, 520);
  img = createGraphics(640, 480);
  img_convolution = createGraphics(640, 480);
  img_grey = createGraphics(640, 480);
  myMovie = new Movie(this, "video.mov");
  myMovie.loop();
  f = createFont("Arial",16,true);
 // Se establecen los framerate no superiores al valor dado
 // frameRate(30);
}

void draw() {
  int value =int(map(mouseX,0,640,0,7));
  textFont(f,18);                  
  fill(0);
  background(255);
  
  text("FPS: "+frameRate,30,500);
  if(value==0){
    img.beginDraw();
    img.image(myMovie, 0, 0);
    img.endDraw();
    image(img, 0, 0);
    text("Video Original",240,500);
  }
  if(value==1){
    img_grey.beginDraw();
     img_grey.image(myMovie, 0, 0);
     img_grey.loadPixels();
     img_grey=grey_scale(img);
     img_grey.updatePixels();
     img_grey.endDraw();
     image(img_grey, 0, 0);
     text("Video en escala de grises",240,500);
  }
  if(value==2){
     img_convolution.beginDraw();
     img_convolution.image(myMovie, 0, 0);
     img_convolution.loadPixels();
     img_convolution = img;
     img_convolution.pixels=convolutionApp(img,img_convolution,focus);
     img_convolution.updatePixels();  
     img_convolution.endDraw();
     image(img_convolution, 0, 0);
     text("Video en filtro enfoque",240,500);
  }
  if(value==3){
     img_convolution.beginDraw();
     img_convolution.image(myMovie, 0, 0);
     img_convolution.loadPixels();
     img_convolution = img;
     img_convolution.pixels=convolutionApp(img,img_convolution,embossment);
     img_convolution.updatePixels();  
     img_convolution.endDraw();
     image(img_convolution, 0, 0);
     text("Video en filtro repujado",240,500);
  }
  if(value==4){
     img_convolution.beginDraw();
     img_convolution.image(myMovie, 0, 0);
     img_convolution.loadPixels();
     img_convolution = img;
     img_convolution.pixels=convolutionApp(img,img_convolution,sharpen);
     img_convolution.updatePixels();  
     img_convolution.endDraw();
     image(img_convolution, 0, 0);
     text("Video en filtro sharpen",240,500);
  }
  if(value==5){
     img_convolution.beginDraw();
     img_convolution.image(myMovie, 0, 0);
     img_convolution.loadPixels();
     img_convolution = img;
     img_convolution.pixels=convolutionApp(img,img_convolution,edgeEnhancement);
     img_convolution.updatePixels();  
     img_convolution.endDraw();
     image(img_convolution, 0, 0);
     text("Video en filtro realce de bordes",240,500);
  }
  if(value==6){
     img_convolution.beginDraw();
     img_convolution.image(myMovie, 0, 0);
     img_convolution.loadPixels();
     img_convolution = img;
     img_convolution.pixels=convolutionApp(img,img_convolution,north);
     img_convolution.updatePixels();  
     img_convolution.endDraw();
     image(img_convolution, 0, 0);
     text("Video en filtro direccional norte",240,500);
  }
  println(frameRate);
}

PGraphics grey_scale(PGraphics img){
  PGraphics img_grey=img;
  img.loadPixels(); 
  for (int y = 0; y < img.height; y++) {
    for (int x = 0; x < img.width; x++) {
      int loc = x + y*img.width;
      
      float r = (red(img.pixels[loc])+green(img.pixels[loc])+blue(img.pixels[loc]))/3;
      float g = (red(img.pixels[loc])+green(img.pixels[loc])+blue(img.pixels[loc]))/3;
      float b = (red(img.pixels[loc])+green(img.pixels[loc])+blue(img.pixels[loc]))/3;
      
      img_grey.pixels[loc] =  color(r,g,b);          
    }
  }
  img_grey.updatePixels();
  return img_grey;
}

void movieEvent(Movie m) {
  m.read();
}

color[] convolutionApp(PGraphics img, PGraphics img_convolution,float[][] matrix){
  color [] result = new color[img_convolution.pixels.length];
  for (int x = 0; x < img.width; x++) {
    for (int y = 0; y < img.height; y++ ) {
      int loc = x + y*img.width;
      result[loc] = convolution(x, y, matrix, 3, img);
    }
  }
  return result;
}

color convolution(int x, int y, float[][] matrix, int matrixsize, PImage img)
{
  float rtotal = 0.0;
  float gtotal = 0.0;
  float btotal = 0.0;
  int offset = matrixsize / 2;
  for (int i = 0; i < matrixsize; i++){
    for (int j= 0; j < matrixsize; j++){
      
      int xloc = x+i-offset;
      int yloc = y+j-offset;
      int loc = xloc + img.width*yloc;
      
      loc = constrain(loc,0,img.pixels.length-1);
     
      rtotal += (red(img.pixels[loc]) * matrix[i][j]);
      gtotal += (green(img.pixels[loc]) * matrix[i][j]);
      btotal += (blue(img.pixels[loc]) * matrix[i][j]);
    }
  }
 
  rtotal = constrain(rtotal, 0, 255);
  gtotal = constrain(gtotal, 0, 255);
  btotal = constrain(btotal, 0, 255);
  
  return color(rtotal, gtotal, btotal);
}
