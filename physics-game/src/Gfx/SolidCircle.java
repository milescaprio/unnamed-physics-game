package gfx;

import java.awt.Color;
import java.awt.Graphics;

public class SolidCircle extends CircleSkull {
    public RGB color;
    public SolidCircle(double relX, double relY, double radius, RGB color) {
        data = new Double[8];
        this.relX = relX;
        this.relY = relY;
        data[0] = radius;
        this.color = color;
    }
    public void draw(Camera camera, Graphics graphics, double x, double y) {
        graphics.setColor(new Color(color.r, color.g, color.b));
        graphics.fillOval(camera.mapx(x+relX-data[0]), camera.mapy(y+relY-data[0]), 2 * camera.mapxscalar(data[0]), 2 * camera.mapyscalar(data[0]));
    }
}