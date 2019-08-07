# Taller raster

## Propósito

Comprender algunos aspectos fundamentales del paradigma de rasterización.

## Tareas

Emplee coordenadas baricéntricas para:

1. Rasterizar un triángulo.
2. Sombrear su superficie a partir de los colores de sus vértices.
3. (opcional para grupos menores de dos) Implementar un [algoritmo de anti-aliasing](https://www.scratchapixel.com/lessons/3d-basic-rendering/rasterization-practical-implementation/rasterization-practical-implementation) para sus aristas.

Referencias:

* [The barycentric conspiracy](https://fgiesen.wordpress.com/2013/02/06/the-barycentric-conspirac/)
* [Rasterization stage](https://www.scratchapixel.com/lessons/3d-basic-rendering/rasterization-practical-implementation/rasterization-stage)

Implemente la función ```triangleRaster()``` del sketch adjunto para tal efecto, requiere la librería [nub](https://github.com/nakednous/nub/releases).

## Integrantes

Complete la tabla:

|       Integrante      |                 github nick                   |
|-----------------------|-----------------------------------------------|
| Diego Alejandro Garcia| [diagarciaar](https://github.com/diagarciaar) |
| Yesid Alberto Ochoa   | [yaochoal](https://github.com/yaochoal)       |
| Oscar Ivan Gomez      | [oigomezz](https://github.com/oigomezz)       |

## Instrucciones de uso

Al ejecutar este programa, podemos visualizar el shading oprimiendo la tecla “s”, y podemos visualizar el anti-aliasing oprimiendo la tecla “a”.


## Discusión

Se logró rasterizar el triángulo generado aleatoriamente haciendo uso de la librería nub. Luego se realizó la implementación de dos técnicas de anti-aliasing conocidas como SSAA. Por último se usó el modelo de iluminación shading con el objetivo de interpolar los colores de los vértices del triángulo sobre la grilla de modo tal que la transición de los colores fuese suave.

## Anti-aliasing

Las técnicas usadas para la implementación del anti-aliasing fueron las siguientes:

SSAA SuperSampling Anti-Aliasing

Se implemento la técnica del SuperSampling Anti-Aliasing técnica que suaviza los bordes dentados y pixelados, en una escena a renderizar. Y en este caso en particular se intentó aplicar el mejoramiento de la calidad a partir de la reducción de espaciado. Tras implementar la partición del pixel, este aumento la calidad de la imagen que es n-veces más grande (dos, cuatro u ocho) según sea definido. 

## Sin Anti-Aliasing
![Sin Anti-Aliasing](/Taller3/images/NoAA.png)

## Con Anti-Aliasing
![Con Anti-Aliasing](/Taller3/images/SSAA.png)

## Shading

La forma en la que se logró este efecto con una interpolación donde se lograron los colores de acuerdo a su posición dentro del triángulo y su cercanía a los vértices, aplicándolo en este caso sobre los tres colores aplicados a este. Adicionalmente se hizo la conversión del espacio cartesiano a baricéntrico con la función de darnos los tres ejes nuevos (alpha, beta, gamma) que fueron utilizados para determinar los colores según la posición del pixel.

![](/Taller3/images/Final.png)


## Dificultades 

A la hora de la implementación, y debido a que los vértices del triángulo se generaban de manera aleatoria, no siempre se rasterizaba el triángulo, esto ocurría porque el algoritmo dependía del orden en que se encontraran estos vértices, esto se solucionó modificando el algoritmo.

## Referencias
* [Supersampling](https://en.wikipedia.org/wiki/Supersampling)

## Entrega

* Plazo: 2/6/19 a las 24h.
