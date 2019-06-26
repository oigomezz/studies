#comando de ejecucion" sh script_ejecutar_todo.sh > text.txt"
g++ -ggdb `pkg-config --cflags opencv` -o `basename blur-effect-sequential.c .c` blur-effect-sequential.c `pkg-config --libs opencv` -lm -fopenmp
g++ -ggdb `pkg-config --cflags opencv` -o `basename blur-effect-posix.c .c` blur-effect-posix.c `pkg-config --libs opencv` -lm -pthread -fopenmp
g++ -ggdb `pkg-config --cflags opencv` -o `basename blur-effect-openmp.c .c` blur-effect-openmp.c `pkg-config --libs opencv` -lm -fopenmp

./blur-effect-sequential 720p.jpg 15
./blur-effect-sequential 1080p.jpg 15
./blur-effect-sequential 4k.jpg 15
./blur-effect-posix 720p.jpg 15 4
./blur-effect-posix 1080p.jpg 15 4
./blur-effect-posix 4k.jpg 15 4
./blur-effect-openmp 720p.jpg 15 4
./blur-effect-openmp 1080p.jpg 15 4
./blur-effect-openmp 4k.jpg 15 4
