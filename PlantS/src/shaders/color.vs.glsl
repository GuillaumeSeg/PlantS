#version 330

layout(location = 0) in vec3 attribute_Position;
layout(location = 1) in vec3 attribute_Color;

out vec3 v_Color;
out vec3 v_Position;

uniform mat4 uniform_MVP = mat4(1.f);
uniform mat4 uniform_MV = mat4(1.f);
uniform mat4 uniform_P = mat4(1.f);

void main(void) {  
	mat4 MVP = uniform_P * uniform_MV;
	v_Color = attribute_Color;
	v_Position = v_Position;
	gl_Position = uniform_MVP * vec4(attribute_Position, 1.0f);
};