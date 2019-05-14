boolean dibujar_cuadrados = true;
void setup()
{
     size(793, 793);
}
void mouseClicked() {
  if(dibujar_cuadrados){
    dibujar_cuadrados = false;
  }else{
    dibujar_cuadrados = true;
  }
}
void draw()
{   
  int grado = 61;
  background(255);
  //ajedrezado inicial
    for(int i = 0; i < height; i += grado){
      for(int j = 0; j < height; j += grado){
        fill(0);
        if(i % 2 == 0){
          rect(j*2, i, grado, grado);
        }
        else{
          rect(j*2+grado, i, grado, grado);
        }
      }
    }
   //Cuadrados internos 
   if(dibujar_cuadrados){
     for(int i = 0,m= grado*7; i < grado*6; i += grado, m+=grado){
        for(int j = 0, n = grado*7; j < grado*6; j += grado,n += grado){
          cuadradosEsquinasD(grado,i,j);
          cuadradosEsquinasD(grado,m,n);
          cuadradosEsquinasI(grado,i,n);
          cuadradosEsquinasI(grado,m,j);
        }
        cuadradosCentroVerticales(grado,i);
        cuadradosCentroHorizontales(grado,m);
      }
    }
} 

int size = 15;
int size2 = 3;
void cuadradosEsquinasD(int grado, int i, int j){
  for(int n = 0; n<=1; n++){
    for(int m = 0; m<=1;m++){
      if(i % 2 == n && j % 2 == m){
        if(n==m)fill(255);else fill(0);
        rect(j+grado-size-size2,i+size2,size,size);
        rect(j+size2,i+grado-size2-size,size,size);
      }
    }
  }
}
void cuadradosEsquinasI(int grado, int i, int j){
  for(int n = 0; n<=1; n++){
    for(int m = 0; m<=1;m++){
      if(i % 2 == n && j % 2 == m){
        if(n==m)fill(255);else fill(0);
          rect(j+size2,i+size2,size,size);
          rect(j-size2+grado-size,i+grado-size2-size,size,size);
      }
    }
  }
}
void cuadradosCentroVerticales(int grado, int i){
     for(int j= 0; j <=1;j++){
     if(j==0){fill(255);}else{fill(0);}
     if(i % 2 == j){
        rect(grado*6-size2+grado-size,i+grado-size-size2,size,size);
        rect(grado*6+size2,i+grado-size-size2,size,size);
        rect(i+grado-size-size2,grado*6-size2+grado-size,size,size);
        rect(i+grado-size-size2,grado*6+size2,size,size);
      }
    }
}

void cuadradosCentroHorizontales(int grado, int i){
     for(int j= 0; j <=1;j++){
     if(j==0){fill(255);}else{fill(0);}
     if(i % 2 == j){
        rect(grado*6-size2+grado-size,i+size2,size,size);
        rect(grado*6+size2,i+size2,size,size);
        rect(i+size2,grado*6-size2+grado-size,size,size);
        rect(i+size2,grado*6+size2,size,size);
      }
    }
}
