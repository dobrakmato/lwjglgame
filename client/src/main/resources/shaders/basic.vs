#version 330

// Input attribures.
layout (location = 0) in vec3 position;

// Oputput.
out vec4 color;

// Uniforms.
uniform float uniformFloat;

// Main method.
void main() {
	color = vec4(clamp(position, 0.0, uniformFloat), 1);
	gl_Position = vec4(position, 1);
}