#version 330

layout(location = 0) in vec3 attribute_Position;
layout(location = 1) in vec3 attribute_Color;

out vec3 vertex_Color;

uniform mat4 uniform_MVP = mat4(1.f);

void main(void) {  
	vertex_Color = attribute_Color;
	gl_Position = uniform_MVP * vec4(attribute_Position, 1.0f);
};