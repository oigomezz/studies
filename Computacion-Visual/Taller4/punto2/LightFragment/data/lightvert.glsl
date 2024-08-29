
uniform mat4 modelview;
uniform mat4 transform;
uniform mat3 normalMatrix;

uniform vec4 lightPosition;
uniform vec3 lightNormal;

attribute vec4 position;
attribute vec4 color;
attribute vec3 normal;

varying vec4 vertColor;
varying vec3 ecNormal;
varying vec3 lightDir;
varying vec3 cameraDirection;
varying vec3 lightDirectionReflected;

void main() {
  gl_Position = transform * position;    
  vec3 ecPosition = vec3(modelview * position);  
  
  ecNormal = normalize(normalMatrix * normal);
  lightDir = normalize(lightPosition.xyz - ecPosition);  

  vec3 lightDirection = normalize(lightPosition.xyz - ecPosition);
  cameraDirection = normalize(0 - ecPosition);
  lightDirectionReflected = reflect(-lightDirection, ecNormal);

  vertColor = color;
}
