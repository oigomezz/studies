
# Taller de análisis de imágenes por software

## Propósito

Introducir el análisis de imágenes/video en el lenguaje de [Processing](https://processing.org/).

## Tareas

Implementar las siguientes operaciones de análisis para imágenes/video:

* Conversión a escala de grises.
* Aplicación de algunas [máscaras de convolución](https://en.wikipedia.org/wiki/Kernel_(image_processing)).
* (solo para imágenes) Despliegue del histograma.
* (solo para imágenes) Segmentación de la imagen a partir del histograma.
* (solo para video) Medición de la [eficiencia computacional](https://processing.org/reference/frameRate.html) para las operaciones realizadas.

Emplear dos [canvas](https://processing.org/reference/PGraphics.html), uno para desplegar la imagen/video original y el otro para el resultado del análisis.

## Integrantes

Complete la tabla:

|       Integrante      |                 github nick                   |
|-----------------------|-----------------------------------------------|
| Diego Alejandro Garcia| [diagarciaar](https://github.com/diagarciaar) |
| Yesid Alberto Ochoa   | [yaochoal](https://github.com/yaochoal)       |
| Oscar Ivan Gomez      | [oigomezz](https://github.com/oigomezz)       |

## Discusión

*  **Conversión a escala de grises**: Se aplico la escala de grises a una imagen donde para conseguir este resultado se recorre la imagen pixel a pixel tomando el promedio ponderado de los 3 colores RGB del mismo y reemplazando este nuevo valor promedio por el nuevo pixel obteniendo el resultado final una vez promediados todos los pixeles.
+ ![Escala de grises](/Taller%201/Escala_Grises_Segmentacion_Histograma/images/grises.png)
*  **Aplicación de algunas máscaras de convolución**: Se realizó la aplicación de 5 mascaras de convolución,recorriendo la imagen pixel a pixel y aplicando a cada uno la matriz de convolución respectiva, esto con el fin de lograr el filtro o mascara deseado donde los resultados fueron los siguientes:
    -  **Enfoque**: Esta mascara potencia los detalles aparentes de una imagen mediante la manipulación informática.
    + ![Enfoque](/Taller%201/Convolucion/images/focus.jpg)
    -  **Realce de bordes**:  El principal objetivo del realce es resaltar aquellas características de la imagen que por causa del mecanismo de captación o por error hayan quedados emborronados en la imagen. Este tipo de filtros es muy usado como método directo de mejorar una imagen cara a su presentación a un observador humano. Con mucha frecuencia la característica más importante a realzar son las fronteras que definen los objetos presentes en la imagen. 
    + ![RealceBordes](/Taller%201/Convolucion/images/edgeEnhancement.jpg)
    -  **Repujado**:  Técnica de computacion grafica en la que cada píxel de una imagen se reemplaza por un resaltado o una sombra, según los límites claros / oscuros de la imagen original. 
    + ![Repujado](/Taller%201/Convolucion/images/embossment.jpg)
    -  **Filtro de Sharpen**: ayuda a resaltar los detalles y mejorar los bordes de los objetos en una imagen. 
    + ![Sharpen](/Taller%201/Convolucion/images/sharpen.jpg)
    -  **Fitro direccional norte**: Se utilizan para detectar estructuras que siguen una determinada dirección en el espacio resaltando el contraste entre los píxeles situados a ambos lados de la estructura.
    + ![Norte](/Taller%201/Convolucion/images/north.jpg)
* **Despliegue del histograma**:  Se realizo el histograma de la imagen donde se almacenaron que frecuencias se repetían los diferentes tonalidades en la imagen en escala de grises "pixeles" y luego se gráfica estas frecuencias mostrando información sobre el brillo y contraste de la imagen.
+ ![Histograma](/Taller%201/Escala_Grises_Segmentacion_Histograma/images/histograma.png)
*  **Segmentación de la imagen a partir del histograma**: Se realizo una segmentación de la imagen a partir del histograma, donde deslizando con la posición del mouse sobre el canvas se visualiza cada sección respectiva del histograma sobre la imagen resaltando esta de color rojo para realizar un análisis mas detallado del mismo.
+ ![Segmentación de Histograma](/Taller%201/Escala_Grises_Segmentacion_Histograma/images/segmentado.png)
*  **Medición de la eficiencia computacional**:  En el proceso de medir la eficiencia computacional de los vídeos se tomó la decisión de incluir un vídeo de extensión .mov, en seis tipos distintos de situaciones, en donde en cada una de ellas se aplicaron los respectivos filtros.

* Los resultados de este proceso mostraron una disminución  de frames a medida que aplica los filtros debido a la carga computacional que estos implican. Adicional mente se nota que en la parte de establecer los frames limites, el vídeo tiende a congelar las imágenes en algunos estados.


## Entrega

* Hacer [fork](https://help.github.com/articles/fork-a-repo/) de la plantilla. Plazo: 28/4/19 a las 24h.
* (todos los integrantes) Presentar el trabajo presencialmente en la siguiente sesión de taller.
