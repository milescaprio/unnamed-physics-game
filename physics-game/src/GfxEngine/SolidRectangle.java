package GfxEngine;

import java.awt.Color;
import java.awt.Graphics;

public class SolidRectangle extends RectangleSkull {
    public RGB color;
    public SolidRectangle(double relX, double relY, double w, double h, RGB color) {
        data = new Double[8];
        this.relX = relX;
        this.relY = relY;
        data[0] = w;
        data[1] = h;
        this.color = color;
    }
    public void draw(Camera camera, Graphics graphics, double x, double y) {
        graphics.setColor(new Color(color.r, color.g, color.b));
        graphics.fillRect(camera.mapx(x+relX-data[0]), camera.mapy(y+relY-data[1]), 2 * camera.mapxscalar(data[0]), 2 * camera.mapyscalar(data[1]));
    }
}
