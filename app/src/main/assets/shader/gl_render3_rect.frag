precision mediump float;

varying vec4 TexCoord;

uniform vec4 vColor;
uniform float deltaTime;
uniform vec2 resolution;

void main()
{
    gl_FragColor = vColor;

    vec2 st = TexCoord.xy / resolution.xy;
    st.x *= resolution.x / resolution.y;

    vec3 color = vec3(0.0);
    color = vec3(st.x, st.y, abs(sin(deltaTime * 3.0)));

    gl_FragColor = vec4(color, 1.0);
}