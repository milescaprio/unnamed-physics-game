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
    public boolean isIntersecting(GfxShape other) {
        if (other.type() == "Circle") {
            return (xMath.in(other.relX, relX-data[0], relX+data[0]) && //relY warelY
                xMath.hypot(0, relY-other.relY) < other.data[0] + data[1]
                ||
                xMath.in(other.relY, relY-data[1], relY+data[1]) && //relX warelY
                xMath.hypot(relX-other.relX, 0) < other.data[0] + data[0]
                ||
                xMath.hypot(
                    relX+data[0]*xMath.direction(relX,other.relX)-other.relX, // if other.relX is above relX, then relX+data[0] is the right side of the rectangle
                    relY+data[1]*xMath.direction(relY,other.relY)-other.relY  //right warelY, second thing is "direction of progress", the circle
                    ) < other.data[0]
                ); 
                //todo: doc test
        }
        else if (other.type() == "Rectangle") {
            return (containsPoint(new Point(other.relX-data[0], other.relY-data[1])) ||
                    containsPoint(new Point(other.relX-data[0], other.relY+data[1])) ||
                    containsPoint(new Point(other.relX+data[0], other.relY-data[1])) ||
                    containsPoint(new Point(other.relX+data[0], other.relY+data[1])));
        }
        else {
            return false;
        }
    }
    @Override
    public boolean containsPoint(Point p) {
        return (p.x > relX-data[0] && p.x < relX+data[0] && p.y > relY-data[1] && p.y < relY+data[1]);
    }
}