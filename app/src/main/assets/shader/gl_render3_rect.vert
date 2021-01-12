attribute vec4 vPosition;

varying vec2 TexCoord;

void main()
{
    TexCoord = vPosition.xy;
    gl_Position = vPosition;
}