#version 330

in vec2 texCoord0;

uniform sampler2D renderTex;

void main() {
	gl_FragColor = texture(renderTex, texCoord0.xy);
}