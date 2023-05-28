package GfxEngine;

import java.awt.Color;
import java.awt.Graphics;

public class SolidRectangle extends RectangleSkull {
    public RGB color;
    public SolidRectangle(double x, double y, double d1, double d2, RGB color) {
        this.relX = x;
        this.relY = y;
        this.d1 = d1;
        this.d2 = d2;
        this.color = color;
    }
    public void draw(Camera camera, Graphics graphics, double x, double y) {
        graphics.setColor(new Color(color.r, color.g, color.b));
        graphics.fillRect(camera.mapx(x+relX-d1), camera.mapy(y+relY-d2), 2 * camera.mapxscalar(d1), 2 * camera.mapyscalar(d2));
    }
}
