# Practica Hilos

##Implementación del efecto borroso de una imagen

Con esta práctica se va a comparar el rendimiento de un algoritmo para la generación del efecto borroso de una imagen, bajo las siguientes condiciones de paralelización:

1. CPU - secuencial.
2. CPU - Hilos POSIX
3. CPU - Hilos OpenMP

El algoritmo y tipo de filtrado de imágenes usado es a través de una matriz de convolución. Es el tratamiento de una
matriz por otra que se llama kernel. El filtro de matriz de convolución usa la primera matriz que es la imagen que se va
a tratar. La imagen es una colección bidimensional de pı́xeles en coordenada rectangular. El kernel usado depende del efecto deseado.

###Comando de ejecucion 

g++ -ggdb `pkg-config --cflags opencv` -o `basename blur-effect-sequential.c .c` blur-effect-sequential.c `pkg-config --libs opencv` -lm -fopenmp
./blur-effect-sequential 720p.jpg 15
./blur-effect-sequential 1080p.jpg 15
./blur-effect-sequential 4k.jpg 15


g++ -ggdb `pkg-config --cflags opencv` -o `basename blur-effect-posix.c .c` blur-effect-posix.c `pkg-config --libs opencv` -lm -pthread -fopenmp
./blur-effect-posix 720p.jpg 15 4
./blur-effect-posix 1080p.jpg 15 4
./blur-effect-posix 4k.jpg 15 4


g++ -ggdb `pkg-config --cflags opencv` -o `basename blur-effect-openmp.c .c` blur-effect-openmp.c `pkg-config --libs opencv` -lm -fopenmp
./blur-effect-openmp 720p.jpg 15 4
./blur-effect-openmp 1080p.jpg 15 4
./blur-effect-openmp 4k.jpg 15 4
