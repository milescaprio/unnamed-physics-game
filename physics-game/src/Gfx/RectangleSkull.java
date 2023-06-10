package gfx;

import gfx.Camera.Point;
import gfx.Snippets.xMath;


/**
 * Base type for a rectangle shape
 * A rectangle, with data[0] the radius width and data[1] the radius height
 * type name "Rectangle"
 */
public abstract class RectangleSkull extends GfxShape {
    public String type() {
        return "Rectangle";
    }
    @Override
    public boolean isIntersecting(GfxShape other, boolean dontRecurse) {
        if (other.type() == "Circle") {
            return xMath.in(other.relX, getXl(), getXr()) && //relY warelY
                xMath.hypot(0, relY-other.relY) < other.data[0] + data[1]
                || xMath.in(other.relY, getYb(), getYt()) && //relX warelY
                xMath.hypot(relX-other.relX, 0) < other.data[0] + data[0]
                || xMath.hypot(
                    relX+data[0]*xMath.direction(relX,other.relX)-other.relX, // if other.relX is above relX, then relX+data[0] is the right side of the rectangle
                    relY+data[1]*xMath.direction(relY,other.relY)-other.relY  //right warelY, second thing is "direction of progress", the circle
                    ) < other.data[0]; 
        }
        else if (other.type() == "Rectangle") {
            return (other.containsPoint(new Point(getXl(), getYb())) ||
                    other.containsPoint(new Point(getXl(), getYt())) ||
                    other.containsPoint(new Point(getXr(), getYb())) ||
                    other.containsPoint(new Point(getXr(), getYt())));
        }
        else if (dontRecurse) {
            throw new IllegalArgumentException("RectangleSkull.isIntersecting() unknown type");
        } else {
            return other.isIntersecting(this, true);
        }
    }
    @Override
    public boolean containsPoint(Point p) {
        return (p.x > getXl() && p.x < getXr() && p.y > getYb() && p.y < getYt());
    }
    public double getXl() {
        return relX-data[0];
    }
    public double getXr() {
        return relX+data[0];
    }
    public double getYb() {
        return relY-data[1];
    }
    public double getYt() {
        return relY+data[1];
    }
}