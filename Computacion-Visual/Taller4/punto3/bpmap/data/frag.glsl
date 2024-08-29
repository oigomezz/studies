#ifdef GL_ES
precision mediump float;
#endif

uniform vec2 iResolution;
uniform vec3 iCursor;
uniform float iTime;

uniform sampler2D diffuseMap;
uniform sampler2D normalMap;
uniform vec3 iSpecColor;

uniform float dizzy;
uniform float force;

void main(){
  //posiciona textura
  //gl_FragCoord contiene las coordenadas relativas de la ventana (x,y,z,1/w) para el fragment
  vec2 uv = vec2(gl_FragCoord.xy) / iResolution;
  
  //aumenta el tamaño
  uv *= 2.0;
  
  
  //deforma la imagen
  if(dizzy == 1.0){
  	uv.x += force * sin(iTime/5000. + uv.y*2.  )/3.;
		uv.y += force * sin(iTime/7000. + uv.x*2.  )/3.;
	}
	
	//mueve la pared
	uv.x += iTime/5000.;
	
	
	//replica textura
	uv = mod(uv, vec2(1.));
    
	//coordenadas del la luz del mouse
	vec2 p = vec2(gl_FragCoord);
	float mx = iCursor.x - p.x/1.;
	float my = iResolution.y - iCursor.y - p.y/1.;
	float mz = iResolution.x * .2;

	//oscurecer la imagen alrededor de la luz
	float fog =  0.2 +   1./ (length(vec3(mx, my, 0.))/40.);

	// rayo de luz en posicion del mouse
	vec3 rayOfLight = normalize(vec3(mx, my, mz));

	// Se obtiene la textura con las normales
	vec3 normal = vec3(texture2D(normalMap, uv)) - 0.5;
	normal = normalize(normal);
	
    // Se define el punto de luz y la reflexion
	float nDotL = max(0.0, dot(rayOfLight, normal));
	vec3 reflection = normal * (2.0 * (nDotL)) - rayOfLight;
	
	// Se define el color con la textura normal y se le agregan los parametros del color
	vec3 col = vec3(texture2D(diffuseMap, uv)) * nDotL * fog;

	//Si se oprime el cursor cambia la intensidad y resalta los detalles
	if(iCursor.z == 1.0){
		float specIntensity = max(0.0, dot(reflection, vec3(.0, .0, 1.)));
		float specRaised = pow(specIntensity, 30.0);
		vec3 specColor = specRaised * iSpecColor;
		col += specColor;
	}

	gl_FragColor = vec4(col, 1.0);
}