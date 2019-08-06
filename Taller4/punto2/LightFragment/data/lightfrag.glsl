#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif
varying vec4 vertColor;
varying vec3 ecNormal;
varying vec3 lightDir;
varying vec3 cameraDirection;
varying vec3 lightDirectionReflected;

void main() {  
  vec3 directionDif = normalize(lightDir);
  vec3 normal = normalize(ecNormal);
  float intensityDif = max(0.0, dot(directionDif, normal));
  vec3 directionSpec = normalize(lightDirectionReflected);
  vec3 camera = normalize(cameraDirection);
  float intensitySpec = pow(max(0.0, pow(dot(directionSpec, camera), 10)), 32);
  
  vec4 colorDif = vec4(intensityDif, intensityDif, intensityDif, 1);   
  vec4 colorSpec = vec4(intensitySpec, intensitySpec, intensitySpec, 1);  
  float ambientStrength = 0.1;
  vec4 colorAmb = vec4(ambientStrength, ambientStrength, ambientStrength, 1);  
  gl_FragColor = vec4((colorDif*0.5) + colorAmb + (colorSpec*0.5)) * vertColor;
}
