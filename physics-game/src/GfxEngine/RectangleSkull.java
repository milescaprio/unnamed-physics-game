package GfxEngine;

import GfxEngine.Camera.Point;
import Snippets.xMath;


/**
 * A rectangle, with d1 the radius width and d2 the radius height;
 * relX, relY at center.
 */
public abstract class RectangleSkull extends GfxShape {
    public String type() {
        return "Rectangle";
    }
    @Override
    public boolean isIntersecting(GfxShape other) {
        if (other.type() == "Circle") {
            return (xMath.in(other.relX, relX-d1, relX+d1) && //relY warelY
                xMath.hypot(0, relY-other.relY) < other.d1 + d2
                ||
                xMath.in(other.relY, relY-d2, relY+d2) && //relX warelY
                xMath.hypot(relX-other.relX, 0) < other.d1 + d1
                ||
                xMath.hypot(
                    relX+d1*xMath.direction(relX,other.relX)-other.relX, // if other.relX is above relX, then relX+d1 is the right side of the rectangle
                    relY+d2*xMath.direction(relY,other.relY)-other.relY  //right warelY, second thing is "direction of progress", the circle
                    ) < other.d1
                ); 
                //todo: doc test
        }
        else if (other.type() == "Rectangle") {
            return (containsPoint(new Point(other.relX-d1, other.relY-d2)) ||
                    containsPoint(new Point(other.relX-d1, other.relY+d2)) ||
                    containsPoint(new Point(other.relX+d1, other.relY-d2)) ||
                    containsPoint(new Point(other.relX+d1, other.relY+d2)));
        }
        else {
            return false;
        }
    }
    @Override
    public boolean containsPoint(Point p) {
        return (p.x > relX-d1 && p.x < relX+d1 && p.y > relY-d2 && p.y < relY+d2);
    }
}