#version 330

layout (location = 0) in vec3 position;

// Uniforms.
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

out vec3 texCoord0;

void main() {
	vec4 mvp_pos = projection * view * model * vec4(position, 1);
	gl_Position = mvp_pos.xyww;
	texCoord0 = position;
}