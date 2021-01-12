precision mediump float;

varying vec2 TexCoord;

uniform vec4 vColor;
uniform float deltaTime;
uniform vec2 resolution;

void main()
{
    float factorTime = 0.6;
    float factorCoord = 1.0 - factorTime;
    float timeR = abs(sin(deltaTime + 0.0)) * factorTime + TexCoord.x * factorCoord ;
    float timeG = abs(sin(deltaTime + 1.0)) * factorTime + TexCoord.y * factorCoord ;
    float timeB = abs(sin(deltaTime + 2.0));

    gl_FragColor = vec4(timeR, timeG, timeB, 1.0);
}