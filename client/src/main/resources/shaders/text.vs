#version 330

// Input attribures.
layout (location = 0) in vec2 position;
layout (location = 1) in vec2 texCoord;

// Oputput.
out vec2 texCoord0;

// Main method.
void main() {
	texCoord0 = texCoord;
	gl_Position = vec4(position.x, position.y, 0, 1);
}