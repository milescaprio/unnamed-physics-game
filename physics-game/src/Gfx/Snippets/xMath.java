package gfx.Snippets;

import java.lang.Math;

public class xMath {
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    public static double hypot(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }
    public static boolean in(double x, double min, double max) {
        return x >= min && x <= max;
    }
    public static int sign(double x) {
        return x < 0 ? -1 : 1;
    }
    public static int direction(double a, double b) {
        return sign(b - a);
    }
}
