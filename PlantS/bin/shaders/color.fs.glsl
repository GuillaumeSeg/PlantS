#version 330

in vec3 v_Color;

struct DirectionalLight {
                vec4 dir;
                vec3 intensity;
};

void main (void) {  
	gl_FragColor = vec4(v_Color, 1.0f);
	 
} ;

vec3 lambertLight(DirectionalLight light, vec3 normal) {
    float cosA = dot(normalize(light.dir), vec4(normalize(normal), 1.0f));
    float A = 0.1 * acos(cosA);
    return A * light.intensity;
}