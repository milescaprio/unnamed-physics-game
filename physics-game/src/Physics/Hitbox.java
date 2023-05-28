package Physics;
/**
 * A shape instance, to compose Figure's.
 * Will only use the dimensions that make sense with the type implementation. As a rule of thumb, work your way up the significant dimensions,
 * and if you need further clarificaiton, look at the doc of the extended class.
 * ShapeType is implemented and "hard" coded.
 * If the visual that a shape creates needs to be changed, the ShapeType extension class can be extended and override the draw() method.
 */

public abstract class Hitbox {
    public double x;
    public double y;
    public double d1;
    public double d2;
    public double d3;

    public abstract boolean isColliding(Hitbox other);
    public abstract double collisionDegree(Hitbox other);
    public abstract String type();
}