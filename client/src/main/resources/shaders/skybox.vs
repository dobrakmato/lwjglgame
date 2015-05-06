#version 330

layout (location = 0) in vec3 position;

uniform mat4 WVP;

out vec3 texCoord0;

void main() {
	vec4 wvp_pos = WVP * vec4(position, 1);
	gl_Position = wvp_pos.xyww;
	texCoord0 = position;
}