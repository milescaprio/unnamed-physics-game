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
    public void draw(Camera c, Graphics g, double x, double y) {
        g.setColor(new Color(color.r, color.g, color.b));
        //FillOval assumes the x y bottom left corner of the figure.
        //Therefore, the radius must be subtracted after cast in case the negative mapped value increases the position.
        int radxpx = c.mapxscalar(data[0]);
        int radypx = c.mapyscalar(data[0]);
        g.fillOval(c.mapx(x+relX) - radxpx, c.mapy(y+relY) - radypx, 2 * radxpx, 2 * radypx);
    }
}