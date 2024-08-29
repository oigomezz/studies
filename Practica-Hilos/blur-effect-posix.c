#include <stdlib.h>
#include <stdio.h>
#include <cv.h>
#include <omp.h>
#include <opencv2/highgui/highgui.hpp>
#include <pthread.h>

using namespace cv;
//Vaiables
IplImage* img = 0; 
int height,width,step,channels,kernel,NUM_THREADS;
uchar *data;

void *Blur(void *threadid) {
	//Vaiables
	int x,xx,y,yy,i,pR,pG,pB,lim1,lim2,tid;
   	tid = (intptr_t) threadid;
	lim1= tid*width/NUM_THREADS;
	lim2= (tid+1)*width/NUM_THREADS;

	for(xx = 0 + lim1; xx < lim2; xx++) {
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

	return 0;
}

int main (int argc, char *argv[]) {
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
	NUM_THREADS = strtol(argv[3], NULL, 10);
	pthread_t threads[NUM_THREADS];

	//Obtener los datos de la imagen
	height    = img->height;
	width     = img->width;
	step      = img->widthStep;
	channels  = img->nChannels;
	data      = (uchar *)img->imageData;

	for( i = 0; i < NUM_THREADS; i++ ){
		error = pthread_create(&threads[i], NULL, Blur, (void *) (intptr_t) i);
		if(error != 0){
			perror("\nNo se pudo crear el hilo");
			exit(-1);
		}
	}

	for(i = 0; i < NUM_THREADS; i++){
		error = pthread_join(threads[i], &retval);
		if(error != 0){
			perror("\nNo se pudo esperar finalizacion del hilo");
			exit(-1);
		}
	}

	delta = omp_get_wtime() - start;
	printf("Posix %s with %d threads computed in %.4g seconds\n", argv[1], NUM_THREADS, delta);

	//Imagen Borrosa
	char str[16]="blur-posix-";
	char *str1=argv[1];
	Mat m = cvarrToMat(img);
	imwrite(strcat(str,str1), m);
	cvReleaseImage(&img);

	return 0;
}