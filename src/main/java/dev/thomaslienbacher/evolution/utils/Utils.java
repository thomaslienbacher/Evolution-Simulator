package dev.thomaslienbacher.evolution.utils;

import java.util.Arrays;

public class Utils {

    public static int randInt(int max) {
        return (int) Math.floor(Math.random() * (max));
    }

    public static int randInt(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min)) + min;
    }

    //TODO: test if starts at zero
    public static int randIntIncl(int max) {
        return (int) Math.floor(Math.random() * (max + 1));
    }

    public static int randIntIncl(int min, int max) {
        return (int) Math.floor(Math.random() * (max + 1 - min)) + min;
    }

    /**
     * @return random number in the range [-1; 1]
     */
    public static double randMut() {
        return (Math.random() * 0.5) - 1.0;
    }

    public static int wrap(int at, int max) {
        return Math.abs(at) % max;
    }

    public static byte[][] copy2dArray(byte[][] src) {
        byte[][] dest = src.clone();

        for(int i = 0; i < src.length; i++) {
            dest[i] = Arrays.copyOf(src[i], src[i].length);
        }

        return dest;
    }

    public static int clamp(int val, int min, int max) {
        return Math.min(max, Math.max(val, min));
    }
}
