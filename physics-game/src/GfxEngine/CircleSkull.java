package GfxEngine;

import GfxEngine.Camera.Point;
import Snippets.xMath;

public abstract class CircleSkull extends GfxShape {
    public String type() {
        return "Circle";
    }
    @Override
    public boolean isIntersecting(GfxShape otherShape) {
        if (otherShape.type() == "Circle") {
            return xMath.hypot(this.relX - otherShape.relX, this.relY - otherShape.relY) < this.d1 + otherShape.d1;
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
        return xMath.hypot(this.relX - p.x, this.relY - p.y) < this.d1;
    }
}