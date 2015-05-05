#version 330

// Input attribures.
layout (location = 0) in vec2 coord;

// Main method.
void main() {
	gl_Position = vec4(coord, 0, 1);
}