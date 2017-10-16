package com.example.derek.colourmemory.gameboard.data;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import com.example.derek.colourmemory.util.Util;

import java.util.Arrays;

/**
 * Created by derek on 16/10/17.
 */

public class Tile {
    private @ColorInt int forgroundColour;
    private @ColorInt int backgorundColour;
    private int status;
    private int row;
    private int column;


    public static class TileColors {
        // TODO: 16/10/17 refactor this into a color generator or move into a xml resource file
        public static final @ColorInt int Red = Color.argb(255, 255, 38, 61);
        public static final @ColorInt int Pink = Color.argb(255, 255, 0, 102);
        public static final @ColorInt int Purple = Color.argb(255, 172, 36, 176);
        public static final @ColorInt int DeepPurple = Color.argb(255, 108, 63, 183);
        public static final @ColorInt int Indigo = Color.argb(255, 47, 86, 181);
        public static final @ColorInt int Blue = Color.argb(255, 0, 156, 242);
        public static final @ColorInt int Light_Blue = Color.argb(255, 0, 175, 243);
        public static final @ColorInt int Cyan = Color.argb(255, 0, 192, 211);
        public static final @ColorInt int Teal = Color.argb(255, 0, 152, 135);
        public static final @ColorInt int Green = Color.argb(255, 0, 176, 79);
        public static final @ColorInt int Lime = Color.argb(255, 203, 219, 59);
        public static final @ColorInt int Yellow = Color.argb(255, 255, 232, 63);
        public static final @ColorInt int Amber = Color.argb(255, 255, 188, 27);
        public static final @ColorInt int Orange = Color.argb(255, 255, 143, 26);
        public static final @ColorInt int Deep_Orange = Color.argb(255, 255, 66, 46);

        public static final @ColorInt int[] TileColors = new int[]{Red, Pink, Purple, DeepPurple, Indigo, Blue, Light_Blue, Cyan, Teal, Green, Lime, Yellow, Amber, Orange, Deep_Orange};

        public static @ColorInt int[] getRandomTileColors(int i) {
            if (i > TileColors.length) throw new IndexOutOfBoundsException("not enough colors to chose from");
            if (i <= 0) throw new RuntimeException("Invalid color count: " + i);

            int[] candidates = Arrays.copyOf(TileColors, TileColors.length);
            Util.shuffleArray(candidates);
            return Arrays.copyOf(candidates, i);
        }
    }
}
