package GfxEngine;

import java.awt.Color;
import java.awt.Graphics;
import GfxEngine.RGB;

public class SolidCircle extends CircleSkull {
    public RGB color;
    public SolidCircle(double relX, double relY, double d1, RGB color) {
        this.relX = relX;
        this.relY = relY;
        this.d1 = d1;
        this.color = color;
    }
    public void draw(Camera camera, Graphics graphics, double x, double y) {
        graphics.setColor(new Color(color.r, color.g, color.b));
        graphics.fillOval(camera.mapx(x+relX-d1), camera.mapy(y+relY-d1), 2 * camera.mapxscalar(d1), 2 * camera.mapyscalar(d1));
    }
}