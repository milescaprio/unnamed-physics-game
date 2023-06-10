package gfx;

import java.awt.Graphics;

import gfx.Camera.Point;

/**
 * A shape instance, to compose Figure's.
 * Will only use the dimensions that make sense with the type implementation. As a rule of thumb, work your way up the significant dimensions,
 * and if you need further clarificaiton, look at the doc of the extended class. The data[] array contains the dimensions, and should be guaranteed
 * to be at least 8 elements long on initialization
 * ShapeType is implemented and "hard" coded.
 * If the visual that a shape creates needs to be changed, the ShapeType extension class can be extended and override the draw() method.
 * Implmented Skull Shapes (for contains point, intersection, and type implementations)
 * 
 * CircleSkull
 * RectangleSkull
 * TriangleSkull
 * 
 * Implemented in this order, with each only relying on intersection implementations of the next,
 * because first has the most precedence.
 */

public abstract class GfxShape {
    public double relX;
    public double relY;
    public Double[] data;

    public boolean isIntersecting(GfxShape otherShape) {
        return isIntersecting(otherShape, false);
    }
    public abstract boolean isIntersecting(GfxShape otherShape, boolean dontRecurse);
    public abstract boolean containsPoint(Point p);
    public abstract String type();
    public abstract void draw(Camera camera, Graphics g, double x, double y);
}