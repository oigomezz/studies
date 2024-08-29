float velocidad_animacion = 10.0f;
float radio_circulo = 80.0f;
int ccolor = 255;
boolean dibujar_circulo = false;
void setup()
{
     size(800, 800);
}
void mouseClicked() {
  if(dibujar_circulo){
    dibujar_circulo = false;
  }else{
    dibujar_circulo = true;
  }
 
}
void draw()
{   
  velocidad_animacion += 0.1f;
  background(0);
  //ciclos para dibujar en la pantalla
  for (int x = 0; x < (width/radio_circulo*2.0) + 1; x++) {
    for (int y = 0; y < height/radio_circulo*2.0 + 1; y++) {
      pushMatrix();
      //clona los circulos
      translate(x * radio_circulo/2, y * radio_circulo/2);
      if (dibujar_circulo){//dibuja los ejes de movimiento de los circulos
        noFill();
        stroke(ccolor);
        ellipse(0, 0, radio_circulo/2, radio_circulo/2);
      }
      //movimiento del circulo
      rotate((x/radio_circulo * TWO_PI * 10) + velocidad_animacion + (y/radio_circulo * radio_circulo));
      translate(radio_circulo/4.0, 0);
      fill(ccolor);
      ellipse(0, 0, radio_circulo/8, radio_circulo/8);
      popMatrix();
    }
  }
} 
