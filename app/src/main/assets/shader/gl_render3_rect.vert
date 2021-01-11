attribute vec4 vPosition;

varying vec4 TexCoord;

void main()
{
    TexCoord = vPosition;
    gl_Position = vPosition;
}