#version 330

in vec2 texCoord0;
in vec3 worldPos0;
in vec3 normal0;
in vec3 tangent0;

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
uniform sampler2D normalMap;
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

vec3 calcBumpedNormal() {
	vec3 normal = normalize(normal0);
	vec3 tangent = normalize(tangent0);
	tangent = normalize(tangent - dot(tangent, normal) * normal);
	vec3 bitangent = cross(tangent, normal);
	vec3 bumpMapNormal = texture(normalMap, texCoord0).xyz;
	bumpMapNormal = 2.0 * bumpMapNormal - vec3(1, 1, 1);
	mat3 TBN = mat3(tangent, bitangent, normal0);
	vec3 newNormal = TBN * bumpMapNormal;
	newNormal = normalize(newNormal);
	return newNormal;
}

void main() {
	gl_FragColor = texture(diffuse, texCoord0.xy) * calcDirectionalLight(directionalLight, calcBumpedNormal());
}