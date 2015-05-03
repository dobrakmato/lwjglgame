#version 330

in vec2 texCoord0;

uniform sampler2D sampler;
uniform vec3 ambientColor;

void main() {
	gl_FragColor =  texture(sampler, texCoord0.xy) * vec4(ambientColor, 1);
}