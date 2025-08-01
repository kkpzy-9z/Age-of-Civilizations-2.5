#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
    // below is a freely customizable scanline shader that is applied to the game at the end of each frame (on top of everything)
    // feel free to finetune values and effects to suit your mod better
    // if you want to change or add new effects entirely but dont know how to code here,
    // i recommend pasting this file into grok AI or chatgpt and telling it to "Change this OpenGL GLSL fragment shader and add ____ effect"!
    // good luck!

    // base texture color
    vec4 baseColor = texture2D(u_texture, v_texCoords) * v_color;

    // CRT scan lines
    float scanLine = sin(v_texCoords.y * 500.0) * 0.02; // Dense scan lines - edit 500.0 to change amount of lines, 0.02 to change opacity
    vec4 scanEffect = vec4(vec3(scanLine), 1.0);
    
    // vignette effect
    float vignette = 1.0 - dot(v_texCoords - 0.5, v_texCoords - 0.5) * 0.65;
    vignette = clamp(vignette, 0.3, 1.0);
   
    // combine above effects and return
    vec4 finalColor = baseColor + scanEffect;
    finalColor.rgb *= vignette;
    finalColor.a = 1.0;

    gl_FragColor = finalColor;
}