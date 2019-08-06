
# Taller de shaders

## Propósito

Estudiar los [patrones de diseño de shaders](http://visualcomputing.github.io/Shaders/#/4).

## Tarea

1. Hacer un _benchmark_ entre la implementación por software y la de shaders de varias máscaras de convolución aplicadas a imágenes y vídeo.
2. Implementar un modelo de iluminación que combine luz ambiental con varias fuentes puntuales de luz especular y difusa. Tener presente _factores de atenuación_ para las fuentes de iluminación puntuales.
3. (grupos de dos o más) Implementar el [bump mapping](https://en.wikipedia.org/wiki/Bump_mapping).

## Integrantes

Complete la tabla:

|       Integrante      |                 github nick                   |
|-----------------------|-----------------------------------------------|
| Diego Alejandro Garcia| [diagarciaar](https://github.com/diagarciaar) |
| Yesid Alberto Ochoa   | [yaochoal](https://github.com/yaochoal)       |
| Oscar Ivan Gomez      | [oigomezz](https://github.com/oigomezz)       |

## Informe

Para esta entrega se implementaron varias técnicas para el avance de los requerimientos que se colocaron:

## Shaders
En el proceso de aplicación de mascaras de convolución implemento shaders con GLSL en la GPU, en donde para enviar la matriz de convolución al fichero.glsl se utilizaron variables de tipo uniforme, y para su implementación se uso utilizo la guía previa de mascaras de convolución mostrada en clase.


Ademas para su implementación ademas de imágenes se aplico la convolución a vídeo mostrando este en un cilindro donde la mejora de rendimiento fue de un 80%, donde en filtros que se ejecutaban a 15fps en un computador de especificaciones medias por software al aplicar el filtro por hardware este alcanzo unos 60-58fps estables sin mayores problema.

![Video en Cilindro](/Taller%204/img/textura11.png )
![Video en Cilindro](/Taller%204/img/textura1.png )
## Light shaders

La iluminación de una escena 3D implica colocar una o más fuentes de luz en el espacio y definir sus parámetros.

En el momento implementar luz proveniente de una fuente natural o una fuente puntual, procedimos a implementar al momento de colocar una luz difusa, un enfoque por vértice o por pixel de la luz sobre el objeto, dependiendo de donde si existe rugosidad sobre el objeto. iluminar, ademas de lightdir que nos provee la dirección de donde proviene la luz.

La posibilidad de configurar sombreadores personalizados nos permite cambiar los algoritmos de renderización predeterminados para aquellos que son más sofisticados o generan estilos visuales específicos.
![Cilindro](/Taller%204/img/textura22.png )
![Cilindro](/Taller%204/img/textura21.png )
## Bump mapping

Es una técnica de gráficos computacionales 3D, que consiste en dar un aspecto rugoso a las superficies de los objetos. Esta técnica modifica las normales de la superficie sin cambiar su geometría, esta técnica se utiliza para evitar tener muchos detalles en la geometría real del objeto ya que esto reduce el rendimiento de la GPU. 

El bump mapping cambia la perpendicularidad por otras normales por medio de una textura que modela como la luz se refleja de la superficie para lograr el efecto deseado, todo ello sin modificar la topología ni la geometría del objeto y maximizando el rendimiento.

La implementación se aplico usando una textura de una pared de ladrillos donde usando una textura para la pared y otra para modificar la reflexión de la luz en su superficie se consiguió el efecto de Bump mapping oprimiendo el clic del ratón de manera sostenida.

![Pared Bump Mapping](/Taller%204/img/textura3.png)
## Referencias
  * [bump mapping](https://docs.unity3d.com/es/current/Manual/StandardShaderMaterialParameterNormalMap.html).

## Entrega

Fecha límite ~~Lunes 1/7/19~~ Domingo 7/7/19 a las 24h. Sustentaciones: 10/7/19 y 11/7/19.

