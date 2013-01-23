#version 330

layout(location = 0) in vec4 attribute_PositionC;
layout(location = 1) in vec4 attribute_PositionP;
layout(location = 2) in vec4 attribute_PositionB;

uniform mat4 uniform_MV = mat4(1.f);
uniform mat4 uniform_P = mat4(1.f);

void main(void) {

	mat4 MVP = uniform_P * uniform_MV;
	
	vec3 positionC = vec3(uniform_MV * vec4(attribute_PositionC.xyz, 1.f));
	vec3 positionP = vec3(uniform_MV * vec4(attribute_PositionP.xyz, 1.f));
	vec3 positionB = vec3(uniform_MV * vec4(attribute_PositionB.xyz, 1.f));
	
	gl_Position.x = attribute_PositionC.w * positionC.x + attribute_PositionP.w * positionP.x + attribute_PositionB.w * positionB.x;
	gl_Position.y = attribute_PositionC.w * positionC.y + attribute_PositionP.w * positionP.y + attribute_PositionB.w * positionB.y;
	gl_Position.z = attribute_PositionC.w * positionC.z + attribute_PositionP.w * positionP.z + attribute_PositionB.w * positionB.z;
	gl_Position.w = 1.f;
};