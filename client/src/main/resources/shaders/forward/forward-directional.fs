#version 330

in vec2 texCoord0;
in vec3 worldPos0;
in vec3 normal0;

struct BaseLight {
	vec3 color;
	float intensity;
};

struct DirectionalLight {
	BaseLight base;
	vec3 direction;
};

uniform float specularIntensity;
uniform float specularPower;
uniform sampler2D diffuse;
uniform vec3 eyePos;
uniform mat4 model;
uniform DirectionalLight directionalLight;

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal) {
	float diffuseFactor = dot(normal, -direction);
	vec4 diffuseColor = vec4(0, 0, 0, 0);
	vec4 specularColor = vec4(0, 0, 0, 0);
	if(diffuseFactor > 0) {
		diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;

		vec3 directionToEye = normalize(eyePos - worldPos0);
		vec3 reflectDirection = normalize(reflect(direction, normal));

		float specularFactor = dot(directionToEye, reflectDirection);
		specularFactor = pow(specularFactor, specularPower);
		if(specularFactor > 0) {
			specularColor = vec4(base.color, 1) * specularIntensity * specularFactor;
		}
	}
	
	return diffuseColor + specularColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal) {
	return calcLight(directionalLight.base, -directionalLight.direction, normal);
}

void main() {
	gl_FragColor = texture(diffuse, texCoord0.xy) * calcDirectionalLight(directionalLight, normal0);
}