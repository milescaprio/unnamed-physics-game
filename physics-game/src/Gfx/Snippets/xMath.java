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
    public static double area(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs((x1 * (y2-y3) + x2 * (y3-y1)+ x3 * (y1-y2)) / 2.0);
    }
    public static boolean isInsideTriangle(double x, double y, double x1, double y1, double x2, double y2, double x3, double y3) {  
       double A = area(x1, y1, x2, y2, x3, y3);
       double A1 = area(x, y, x2, y2, x3, y3);
       double A2 = area(x1, y1, x, y, x3, y3);
       double A3 = area(x1, y1, x2, y2, x, y);

       //If the subtriangles add up to the original triangle, the point is inside
       //This is because being outside would break the absolute value
       return (A == A1 + A2 + A3);
    } 
}