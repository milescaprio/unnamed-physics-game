package gfx;

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
        //FillRect assumes the x y bottom left corner of the figure.
        //Therefore, the radius must be subtracted after cast in case the negative mapped value increases the position.
        int radxpx = camera.mapxscalar(data[0]);
        int radypx = camera.mapyscalar(data[1]);
        graphics.fillRect(camera.mapx(x+relX) - radxpx, camera.mapy(y+relY) - radypx, 2 * radxpx, 2 * radypx);
    }
}
