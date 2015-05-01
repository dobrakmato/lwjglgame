#version 330

// Input attribures.
layout (location = 0) in vec2 position;

// Oputput.
out vec2 texCoord0;

// Main method.
void main() {
	texCoord0 = vec2((position.x + 1) / 2, (position.y + 1) / 2);
	gl_Position = vec4(position.x, position.y, 0, 1);
}