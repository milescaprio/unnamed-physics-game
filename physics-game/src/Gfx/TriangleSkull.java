package gfx;

import gfx.Camera.Point;
import gfx.Snippets.xMath;

/**
 * Base class for triangle shapes
 * contains relative X points of the triangle in data[0] [2] [4]
 * contains relative Y points of the triangle in data[1] [3] [5]
 * type name is "Triangle"
 */
public abstract class TriangleSkull extends GfxShape {
    public String type() {
        return "Triangle";
    }
    @Override
    public boolean isIntersecting(GfxShape other, boolean dontRecurse) {
        if (other.type() == "Circle") {
            throw new IllegalArgumentException("Unknown Intersection Method: Triangle - Circle");
        }
        else if (other.type() == "Rectangle") {
            return other.containsPoint(getPoint(0)) 
            || other.containsPoint(getPoint(1)) 
            || other.containsPoint(getPoint(2))
            || containsPoint(new Point(other.relX-data[0], other.relY-data[1]))
            || containsPoint(new Point(other.relX-data[0], other.relY+data[1]))
            || containsPoint(new Point(other.relX+data[0], other.relY-data[1]))
            || containsPoint(new Point(other.relX+data[0], other.relY+data[1]));
        }
        else if (other.type() == "Triangle") {
            boolean inOther = other.containsPoint(getPoint(0))
            || other.containsPoint(getPoint(1))
            || other.containsPoint(getPoint(2));
            if (!dontRecurse) {
                return inOther || other.isIntersecting(this, true);
            } else {
                return inOther;
            }
        } else if (dontRecurse) {
            throw new IllegalArgumentException("TriangleSkull.isIntersecting() unknown type");
        } else {
            return other.isIntersecting(this, true);
        }
    }
    @Override
    public boolean containsPoint(Point p) {
        return xMath.isInsideTriangle(p.x, p.y, getPointX(0), getPointY(0), getPointX(1), getPointY(1), getPointX(2), getPointY(2));
    }
    public Point getPoint(int index) {
        if (index > 2) {
            throw new IndexOutOfBoundsException("TriangleSkull.getPoint() index out of bounds");
         }
        return new Point(this.relX + this.data[index*2], this.relY + this.data[index*2+1]);
    }
    public double getPointX(int index) {
        if (index > 2) {
            throw new IndexOutOfBoundsException("TriangleSkull.getPointX() index out of bounds");
        }
        return this.relX + this.data[index*2];
    }
    public double getPointY(int index) {
        if (index > 2) {
            throw new IndexOutOfBoundsException("TriangleSkull.getPointY() index out of bounds");
        }
        return this.relY + this.data[index*2+1];
    }

}
