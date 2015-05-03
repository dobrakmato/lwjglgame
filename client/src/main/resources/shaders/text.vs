#version 330

// Input attribures.
layout (location = 0) in vec4 coord;

// Oputput.
out vec2 texCoord0;

// Main method.
void main() {
	texCoord0 = coord.zw;
	gl_Position = vec4(coord.xy, 0, 1);
}