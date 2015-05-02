#version 330

const int MAX_POINT_LIGHTS = 4;
const int MAX_SPOT_LIGHTS = 4;
const int MAX_LIGHT_RANGE = 1024;

struct Attenuation {
	float constant;
	float linear;
	float quadratic;
};

struct BaseLight {
	vec3 color;
	float intensity;
};

struct DirectionalLight {
	BaseLight base;
	vec3 direction;
};

struct PointLight {
	BaseLight base;
	Attenuation atten;
	vec3 position;
	float range;
};

struct SpotLight {
	PointLight pointLight;
	vec3 direction;
	float cutoff;
};

in vec2 texCoord0;
in vec3 worldPos0;
in vec3 normal0;

uniform float specularIntensity;
uniform float specularPower;

uniform sampler2D sampler;
uniform sampler2D normalMap;
uniform sampler2D specularMap;

uniform vec3 eyePos;
uniform vec3 baseColor;
uniform vec3 ambientLight;

uniform mat4 model;
uniform DirectionalLight directionalLight;
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];

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
			// Get intensity from specular map.			
			float specularIntensityFromMap = texture(specularMap, texCoord0).r;
			specularColor = vec4(base.color, 1) * specularIntensity * specularFactor;
		}
	}
	
	return diffuseColor + specularColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal) {
	return calcLight(directionalLight.base, -directionalLight.direction, normal);
}

vec4 calcPointLight(PointLight pointLight, vec3 normal) {
	vec3 lightDir = worldPos0 - pointLight.position;
	float distanceToPoint = length(lightDir);

	if(distanceToPoint > pointLight.range) {
		return vec4(0, 0, 0, 0);
	}

	lightDir = normalize(lightDir);

	vec4 color = calcLight(pointLight.base, lightDir, normal);
	float attenuation = pointLight.atten.constant + pointLight.atten.linear * distanceToPoint + pointLight.atten.quadratic * distanceToPoint * distanceToPoint + 0.0001;
	return color / attenuation;
}

vec4 calcSpotLight(SpotLight spotLight, vec3 normal) {
	vec3 lightDir = normalize(worldPos0 - spotLight.pointLight.position);
	float spotFactor = dot(lightDir, spotLight.direction);
	vec4 color = vec4(0, 0, 0, 0);
	if(spotFactor > spotLight.cutoff) {
		color = calcPointLight(spotLight.pointLight, normal) * (1 - (1 - spotFactor) / (1 - spotLight.cutoff));
	}
	
	return color;
}

void main() {
	vec4 normalFromNM0 = texture(normalMap, texCoord0.xy);

	vec4 totalLight = vec4(ambientLight, 1);
	vec4 color = vec4(baseColor, 1);
	vec4 textureColor = texture(sampler, texCoord0.xy);
	if(textureColor != vec4(0, 0, 0, 0)) {
		color *= textureColor;
	}
	
	vec4 normalFromNM = model * ((normalFromNM0 * 2) - 1);
	
	totalLight += calcDirectionalLight(directionalLight, normal0); // normal0
	
	for(int i = 0; i < MAX_POINT_LIGHTS; i++) {
		if(pointLights[i].base.intensity > 0) {
			totalLight += calcPointLight(pointLights[i], normal0);
		}
	}
	
	for(int i = 0; i < MAX_SPOT_LIGHTS; i++) {
		if(spotLights[i].pointLight.base.intensity > 0) {
			totalLight += calcSpotLight(spotLights[i], normal0);
		}
	}
	
	gl_FragColor = color * totalLight;
}