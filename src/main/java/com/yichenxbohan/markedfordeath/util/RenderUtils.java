package com.yichenxbohan.markedfordeath.util;

public class RenderUtils {
    // H: 0.0 ~ 360.0, S: 0.0 ~ 1.0, L: 0.0 ~ 1.0 (RGB: 0.0 ~ 1.0)
    public static float[] hslToRgb(float h, float s, float l) {
        // Normalize hue to 0-360 range
        h = h % 360;
        if (h < 0) h += 360;

        if (s <= 0.0f) {
            return new float[]{l, l, l};
        }

        float c = (1 - Math.abs(2 * l - 1)) * s;
        float hPrime = h / 60.0f;
        float x = c * (1 - Math.abs(hPrime % 2 - 1));

        float r, g, b;
        if (hPrime < 1) {
            r = c; g = x; b = 0;
        } else if (hPrime < 2) {
            r = x; g = c; b = 0;
        } else if (hPrime < 3) {
            r = 0; g = c; b = x;
        } else if (hPrime < 4) {
            r = 0; g = x; b = c;
        } else if (hPrime < 5) {
            r = x; g = 0; b = c;
        } else {
            r = c; g = 0; b = x;
        }

        float m = l - c / 2;
        return new float[]{
                Math.max(0, Math.min(1, r + m)),
                Math.max(0, Math.min(1, g + m)),
                Math.max(0, Math.min(1, b + m))
        };
    }
}
