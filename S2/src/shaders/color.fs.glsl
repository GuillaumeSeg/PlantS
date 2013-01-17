#version 330

in vec3 vertex_Color;

void main (void) {  
	gl_FragColor = vec4(vertex_Color, 1.0f);  
} ;