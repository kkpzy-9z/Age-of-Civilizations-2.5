#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
    // Get base texture color
    vec4 baseColor = texture2D(u_texture, v_texCoords) * v_color;
    
    // Add CRT scan lines
    float scanLine = sin(v_texCoords.y * 500.0) * 0.02; // Dense scan lines
    vec4 scanEffect = vec4(vec3(scanLine), 1.0);
    
    // Add vignette effect (darker edges)
    float vignette = 1.0 - dot(v_texCoords - 0.5, v_texCoords - 0.5) * 0.65;
    vignette = clamp(vignette, 0.3, 1.0);
   
    // Combine effects with phosphor-like glow
    vec4 finalColor = baseColor + scanEffect;
    finalColor.rgb *= vignette; // Apply vignette
    finalColor.a = 1.0;   

    gl_FragColor = finalColor;
}