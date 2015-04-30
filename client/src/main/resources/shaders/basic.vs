#version 330

// Input attribures.
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;

// Oputput.
out vec2 texCoord0;

// Uniforms.
uniform mat4 transform;

// Main method.
void main() {
	texCoord0 = texCoord;
	gl_Position = transform * vec4(position, 1);
}