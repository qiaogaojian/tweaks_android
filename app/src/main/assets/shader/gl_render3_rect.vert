attribute vec4 vPosition;

varying vec2 TexCoord;
uniform mat4 mvp;

void main()
{
    TexCoord = vPosition.xy;
    gl_Position =  mvp * vPosition;
}