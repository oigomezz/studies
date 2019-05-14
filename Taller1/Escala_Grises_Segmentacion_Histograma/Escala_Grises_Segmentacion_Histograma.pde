    
PGraphics pg;
PGraphics pg1;
PImage img;
PImage img_grey;
PImage img_segmented;
PImage histogram;
PImage brightness;
PImage histogram_segmented;
PImage scale;
PFont f; 

void setup() {
  size(1000, 700);
  f = createFont("Arial",16,true);
  pg = createGraphics(300, 300);
  pg1 = createGraphics(310, 30);
  img = loadImage("rose.jpg");
  img_grey = loadImage("rose.jpg");
  scale = loadImage("scale.png");
  histogram = createImage(300, 300, RGB);
  histogram_segmented = createImage(300, 300, RGB);
  brightness = createImage(10, 300, RGB);
  
}

void draw() {
  background(206,215,210);
  
  pg1.beginDraw();
  pg1.image(scale,0,0);
  pg1.endDraw();
  image(pg1, 654, 310);
  image(pg1, 654, 650);
  
  textFont(f,18);                  
  fill(0); 
  
  text("Imagen Original",100,330);            
  text("Imagen a Escala de grises",370,330);                                       
  text("Imagen Segmentada",380,670); 
  println(mouseX,mouseY);
   
  //renderizamos imagen original
  pg.beginDraw();
  pg.image(img,0,0);
  pg.endDraw();
  image(pg, 10, 10);
  
 //renderizamos imagen original
   
  
  
  //renderizamos imagen en escala de grises
  pg.beginDraw();
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
  pg.image(img_grey,0,0);
  pg.endDraw();
  image(pg, 320, 10);
  
  
  
  
  
  
  
  
  
  
  // histograma de imagen gris
  pg.beginDraw();
  histogram.loadPixels();
  // ponemos el fondo de la imagen blanco
  for (int i = 0; i < histogram.pixels.length; i++) {
    histogram.pixels[i] = color(255, 255, 255); 
  }
  histogram.updatePixels();
  pg.image(histogram,0,0);
  
  int[] hist = new int[256];
  // Calculamos el histograma
  for (int i = 0; i < img_grey.width; i++) {
    for (int j = 0; j < img_grey.height; j++) {
      int bright = int(brightness(img_grey.get(i, j)));//guarda la escala de gris en el pixel
      hist[bright]++; //le suma 1 unidad al arreglo hist en la posicion "bright" que es la escala de gris de cada pixel
    }
  }
  
  // Encuentra el valor mas alto en el histograma
  int histMax = max(hist);
  //println(histMax);
  pg.stroke(0);
  
  textFont(f,16);
  text("0",640,310); 
  text(histMax,625,30); 
  text("0",640,650); 
  text(histMax,625,370);
  
  
  // Dibuje media del histograma (saltar cada segundo valor)
  for (int i = 0; i < histogram.width; i +=1) {
    // Mapea i (desde 0..hasta img.width) a una localización en el histograma (0..255)
    int which = int(map(i, 0, histogram.width, 0, 255));//mapea la imagen en las posiciones
    // Convierta el valor del histograma en una ubicación 
    // entre la parte inferior y la parte superior de la imagen
    int y = int(map(hist[which], 0, histMax, histogram.height, 0));
    pg.line(i, histogram.height, i, y);
    
  }
  pg.endDraw();
  image(pg, 660, 10);     
  
  
  
  
  
  
  
  
  
  
  
  
  
////////Segmentación de imagen/////////////
  int value =int(map(mouseX,0,1000,0,255));
  
  int start_segmented = value;
  int end_segmented = value+40;
  
  pg.beginDraw();
  img_segmented= img_grey;
  img_segmented.loadPixels();
  
  for (int i = 0; i < img_grey.width; i++) {
    for (int j = 0; j < img_grey.height; j++) {
      int loc = i + j*img_grey.width;
      int bright = int(brightness(img_grey.get(i, j)));
       if(bright>start_segmented && bright< end_segmented){
          img_segmented.pixels[loc] =  color(255,0,0);    
      }
    }
  }
  
  img_segmented.updatePixels();
  pg.image(img_segmented,0,0);
  pg.endDraw();
  image(pg, 320, 350); 








///////Histograma segmentado///////
// histograma de imagen gris
  pg.beginDraw();
  histogram_segmented.loadPixels();
  // ponemos el fondo de la imagen blanco
  for (int i = 0; i < histogram_segmented.pixels.length; i++) {
    histogram_segmented.pixels[i] = color(255, 255, 255); 
  }
  histogram_segmented.updatePixels();
  pg.image(histogram_segmented,0,0);
  

  // Dibuje media del histograma (saltar cada segundo valor)
  for (int i = 0; i < histogram_segmented.width; i +=1) {
    // Mapea i (desde 0..hasta img.width) a una localización en el histograma (0..255)
    int which = int(map(i, 0, histogram_segmented.width, 0, 255));//mapea la imagen en las posiciones
    // Convierta el valor del histograma en una ubicación 
    // entre la parte inferior y la parte superior de la imagen
    int y = int(map(hist[which], 0, histMax, histogram_segmented.height, 0));
   
      if(which>start_segmented && which< end_segmented){
        pg.stroke(255,0,0);
        pg.line(i, histogram_segmented.height, i, y);
      }else{
        pg.stroke(0);
        pg.line(i, histogram_segmented.height, i, y);
      }
      

  }
  pg.endDraw();
  image(pg, 660, 350);     


  
  
  
}
