package gfx;

import gfx.Camera.Point;
import gfx.Snippets.xMath;

/**
 * Base class for circle shapes
 * contains radius in data[0]
 * type name is "Circle"
 */
public abstract class CircleSkull extends GfxShape {
    public String type() {
        return "Circle";
    }
    @Override
    public boolean isIntersecting(GfxShape other, boolean dontRecurse) {
        if (other.type() == "Circle") {
            return xMath.hypot(this.relX - other.relX, this.relY - other.relY) < this.data[0] + other.data[0];
        }
        else if (dontRecurse) {
            throw new IllegalArgumentException("Circle.isIntersecting() unknown type");
        } else {
            return other.isIntersecting(this, true);
        }
    }
    @Override
    public boolean containsPoint(Point p) {
        return xMath.hypot(this.relX - p.x, this.relY - p.y) < this.data[0];
    }
}