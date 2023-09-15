package gfx;

import java.awt.Color;
import java.awt.Graphics;

public class SolidTriangle extends TriangleSkull {
    public RGB color;
    public SolidTriangle(double relX, double relY, double x1, double y1, double x2, double y2, double x3, double y3, RGB color) {
        data = new Double[8];
        this.relX = relX;
        this.relY = relY;
        this.color = color;
        data[0] = x1;
        data[1] = y1;
        data[2] = x2;
        data[3] = y2;
        data[4] = x3;
        data[5] = y3;
    }
    public void draw(Camera c, Graphics g, double x, double y) {
        g.setColor(new Color(color.r, color.g, color.b));
        int x1 = c.mapx(x+relX+data[0]);
        int y1 = c.mapy(y+relY+data[1]);
        int x2 = c.mapx(x+relX+data[2]);
        int y2 = c.mapy(y+relY+data[3]);
        int x3 = c.mapx(x+relX+data[4]);
        int y3 = c.mapy(y+relY+data[5]);
        g.fillPolygon(new int[]{x1,x2,x3}, new int[]{y1,y2,y3}, 3);

    }
}
