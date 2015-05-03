#version 330

in vec2 texCoord0;

uniform sampler2D renderTex;
uniform vec3 color;

void main() {
	gl_FragColor = vec4(1, 1, 1, texture(renderTex, texCoord0).r) * color;
}