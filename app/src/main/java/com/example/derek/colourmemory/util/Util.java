package com.example.derek.colourmemory.util;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.util.Random;

/**
 * Created by derek on 15/10/17.
 */

public class Util {

    /** checker */
    public static @NonNull <T> T checkNotNull(final T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static @NonNull <T> T checkNotNull(final T reference, final Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /** colors */
    public static int manipulateColor(int color, float factor){
        int a = (color >> 24) & 0xFF;
        int r = (int) (((color >> 16) & 0xFF) * factor);
        int g = (int) (((color >> 8) & 0xFF) * factor);
        int b = (int) ((color & 0xFF) * factor);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }


    /** arrays */
    public static <T> void shuffleArray(T[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    public static void shuffleArray(int[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    private static <T> void swap(T[] a, int i, int change) {
        T helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }

    private static void swap(int[] a, int i, int change) {
        int helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }

    public static float dpToPx(float dp) {
        return (float) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float pxToDp(float px) {
        return (float) (px / Resources.getSystem().getDisplayMetrics().density);
    }

}
