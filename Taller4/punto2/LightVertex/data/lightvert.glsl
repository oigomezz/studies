
uniform mat4 modelview;
uniform mat4 transform;
uniform mat3 normalMatrix;
uniform vec4 lightPosition;

attribute vec4 position;
attribute vec4 color;
attribute vec3 normal;

varying vec4 vertColor;

void main() {
  gl_Position = transform * position;    
  vec3 ecPosition = vec3(modelview * position);  
  vec3 ecNormal = normalize(normalMatrix * normal);
  
  vec3 lightDirection = normalize(lightPosition.xyz - ecPosition);
  vec3 cameraDirection = normalize(0 - ecPosition);
  vec3 lightDirectionReflected = reflect(-lightDirection, ecNormal);
  float intensitySpec = pow(max(0.0, pow(dot(lightDirectionReflected, cameraDirection), 4)), 8);
  vec4 vertColorSpec = vec4(intensitySpec, intensitySpec, intensitySpec, 1); 

  float ambientStrength = 0.1;
  vec4 vertColorAmb = vec4(ambientStrength, ambientStrength, ambientStrength, 1);

  vec3 direction = normalize(lightPosition.xyz - ecPosition);    
  float intensityDif = max(0.0, dot(direction, ecNormal));
  vec4 vertColorDif = vec4(intensityDif, intensityDif, intensityDif, 1); 

  vertColor = (vertColorAmb + (vertColorSpec) + (vertColorDif)) * color ;
}
