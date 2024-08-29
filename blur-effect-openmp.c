#include <stdlib.h>
#include <stdio.h>
#include <cv.h>
#include <omp.h>
#include <opencv2/highgui/highgui.hpp>

#define NUM_THREADS 4

using namespace cv;

int main (int argc, char *argv[]) {
	//Vaiables
	IplImage* img = 0; 
	int height,width,step,channels,kernel,hilos;
	uchar *data;

	double delta,start = omp_get_wtime();
	int i,error;
	void *retval;

	//Validacion
	if(argc<4){
		printf("Faltan argumentos: imagen.jgp kernel #Hilos \n\7");
		exit(0);
	}

	//Cargar la imagen
	img=cvLoadImage(argv[1]);
	kernel = strtol(argv[2], NULL, 10);
	if(!img){
		printf("No se pudo cargar el archivo de imagen: %s\n",argv[1]);
		exit(0);
	}
	hilos = strtol(argv[3], NULL, 10);

	//Obtener los datos de la imagen
	height    = img->height;
	width     = img->width;
	step      = img->widthStep;
	channels  = img->nChannels;
	data      = (uchar *)img->imageData;

	#pragma omp parallel num_threads(hilos)
	{
		int x,xx,y,yy,i,pR,pG,pB,R,G,B;
		#pragma omp for schedule(static, width/hilos)
		for(xx = 0; xx < width; xx++)
		    for(yy = 0; yy < height; yy++) {
		        pB = pG = pR = 0;
		        i = 0;
		        for(x = xx; x < width && x < xx + kernel; x++) {
		            for(y = yy; y < height && y < yy + kernel; y++) {
				
		                pR += data[x*3 + y*width*3 + 2];
		                pG += data[x*3 + y*width*3 + 1];
		                pB += data[x*3 + y*width*3 + 0];
		                i++;
		            }
		        }
		        pR = pR/i;
		        pG = pG/i;
		        pB = pB/i;

		        data[xx*3 + yy*width*3 + 2] = pR;
		        data[xx*3 + yy*width*3 + 1] = pG;
		        data[xx*3 + yy*width*3 + 0] = pB;
		    }

	}

	
	delta = omp_get_wtime() - start;
	printf("Openmp %s with %d threads computed in %.4g seconds\n", argv[1], hilos, delta);

	//Imagen Borrosa
	char str[16]="blur-openmp-";
	char *str1=argv[1];
	Mat m = cvarrToMat(img);
	imwrite(strcat(str,str1), m);
	cvReleaseImage(&img);

	return 0;
}