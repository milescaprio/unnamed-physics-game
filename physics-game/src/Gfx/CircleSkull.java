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
    public boolean isIntersecting(GfxShape otherShape) {
        if (otherShape.type() == "Circle") {
            return xMath.hypot(this.relX - otherShape.relX, this.relY - otherShape.relY) < this.data[0] + otherShape.data[0];
        }
        else if (otherShape.type() == "Rectangle") {
            return otherShape.isIntersecting(this);
        }
        else {
            return false;
        }
    }
    @Override
    public boolean containsPoint(Point p) {
        return xMath.hypot(this.relX - p.x, this.relY - p.y) < this.data[0];
    }
}