#version 330

in vec3 texCoord0;

uniform samplerCube cubeMap;

void main() {
	gl_FragColor = texture(cubeMap, texCoord0);
}