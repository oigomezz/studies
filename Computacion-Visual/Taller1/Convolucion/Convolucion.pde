PGraphics pg;
PImage img;
PImage convolution;
int xend = 300;
int yend = 300;
int matrixsize = 3;

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

void setup() {
  size(940, 630);
  pg = createGraphics(300, 300);
  img = loadImage("rose.jpg");
  convolution = loadImage("rose.jpg");
}

void draw() {
  //renderizamos imagen original
  pg.beginDraw();
  pg.image(img,0,0);
  pg.endDraw();
  image(pg, 10, 10);
  
  pg.beginDraw();
  // Calculate the small rectangle we will process
  xend = 300;
  yend = 300;
  matrixsize = 3;
  img.loadPixels();
  // Begin our loop for every pixel in the image
  for (int x = 0; x < xend; x++) {
    for (int y = 0; y < yend; y++ ) {
      color c = convolution(x, y, focus, matrixsize, img);
      int loc = x + y*img.width;
      convolution.pixels[loc] = c;
    }
  }
  
  convolution.updatePixels();
  //convolution.save("focus.jpg");
  pg.image(convolution,0,0);
  pg.endDraw();
  image(pg, 320, 10);
  
  pg.beginDraw();
  // Calculate the small rectangle we will process
  xend = 300;
  yend = 300;
  matrixsize = 3;
  img.loadPixels();
  // Begin our loop for every pixel in the image
  for (int x = 0; x < xend; x++) {
    for (int y = 0; y < yend; y++ ) {
      color c = convolution(x, y, embossment, matrixsize, img);
      int loc = x + y*img.width;
      convolution.pixels[loc] = c;
    }
  }
  
  convolution.updatePixels();
  //convolution.save("embossment.jpg");
  pg.image(convolution,0,0);
  pg.endDraw();
  image(pg, 630, 10);
  
  
  pg.beginDraw();
  // Calculate the small rectangle we will process
  xend = 300;
  yend = 300;
  matrixsize = 3;
  img.loadPixels();
  // Begin our loop for every pixel in the image
  for (int x = 0; x < xend; x++) {
    for (int y = 0; y < yend; y++ ) {
      color c = convolution(x, y, sharpen , matrixsize, img);
      int loc = x + y*img.width;
      convolution.pixels[loc] = c;
    }
  }
  
  convolution.updatePixels();
  //convolution.save("sharpen.jpg");
  pg.image(convolution,0,0);
  pg.endDraw();
  image(pg, 10, 320);
  
  pg.beginDraw();
  // Calculate the small rectangle we will process
  xend = 300;
  yend = 300;
  matrixsize = 3;
  img.loadPixels();
  // Begin our loop for every pixel in the image
  for (int x = 0; x < xend; x++) {
    for (int y = 0; y < yend; y++ ) {
      color c = convolution(x, y, edgeEnhancement , matrixsize, img);
      int loc = x + y*img.width;
      convolution.pixels[loc] = c;
    }
  }
  
  convolution.updatePixels();
  //convolution.save("edgeEnhancement.jpg");
  pg.image(convolution,0,0);
  pg.endDraw();
  image(pg, 320, 320);
  
  pg.beginDraw();
  // Calculate the small rectangle we will process
  xend = 300;
  yend = 300;
  matrixsize = 3;
  img.loadPixels();
  // Begin our loop for every pixel in the image
  for (int x = 0; x < xend; x++) {
    for (int y = 0; y < yend; y++ ) {
      color c = convolution(x, y, north, matrixsize, img);
      int loc = x + y*img.width;
      convolution.pixels[loc] = c;
    }
  }
  
  convolution.updatePixels();
  //convolution.save("north.jpg");
  pg.image(convolution,0,0);
  pg.endDraw();
  image(pg, 630, 320);
  
}

color convolution(int x, int y, float[][] matrix, int matrixsize, PImage img)
{
  float rtotal = 0.0;
  float gtotal = 0.0;
  float btotal = 0.0;
  int offset = matrixsize / 2;
  for (int i = 0; i < matrixsize; i++){
    for (int j= 0; j < matrixsize; j++){
      // What pixel are we testing
      int xloc = x+i-offset;
      int yloc = y+j-offset;
      int loc = xloc + img.width*yloc;
      // Make sure we haven't walked off our image, we could do better here
      loc = constrain(loc,0,img.pixels.length-1);
      // Calculate the convolution
      rtotal += (red(img.pixels[loc]) * matrix[i][j]);
      gtotal += (green(img.pixels[loc]) * matrix[i][j]);
      btotal += (blue(img.pixels[loc]) * matrix[i][j]);
    }
  }
  // Make sure RGB is within range
  rtotal = constrain(rtotal, 0, 255);
  gtotal = constrain(gtotal, 0, 255);
  btotal = constrain(btotal, 0, 255);
  // Return the resulting color
  return color(rtotal, gtotal, btotal);
}
