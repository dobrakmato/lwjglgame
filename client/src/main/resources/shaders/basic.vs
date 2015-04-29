#version 330

// Input attribures.
layout (location = 0) in vec3 position;

// Oputput.
out vec4 color;

// Uniforms.
uniform mat4 transform;

// Main method.
void main() {
	color = transform * vec4(clamp(position, 0.0, 1.0), 1);
	gl_Position = transform * vec4(position, 1);
}