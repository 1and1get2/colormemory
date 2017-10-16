package com.example.derek.colourmemory.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * Created by derek on 17/10/17.
 */

public class ColorUtil {
    public static int manipulateColor(int color, float factor){
        int a = (color >> 24) & 0xFF;
        int r = (int) (((color >> 16) & 0xFF) * factor);
        int g = (int) (((color >> 8) & 0xFF) * factor);
        int b = (int) ((color & 0xFF) * factor);


        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static @ColorInt int[] uniqueColorGenerator(int colorsNumber) {
        @ColorInt int[] colors = new int[colorsNumber];
        if (colorsNumber > 360 || colorsNumber < 1) throw new RuntimeException("not enough colors to chose from, needed: " + colorsNumber);

        for (int i = 0; i < colorsNumber; i ++) {
            // hsv[0] is Hue [0 .. 360) hsv[1] is Saturation [0...1] hsv[2] is Value [0...1]
            float[] hsv = new float[3];
            hsv[0] = i * (360f / colorsNumber);
            hsv[1] = 0.8f + 0.2f * ((float) Math.random());
            hsv[2] = 0.8f + 0.2f * ((float) Math.random());
            // hsv[1] = (float) Math.random();
            // hsv[2] = (float) Math.random();

            colors[i] = Color.HSVToColor(hsv);
        }

        return colors;
    }


}
