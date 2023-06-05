package gfx;

public class Camera {
    public static class Point {
        public double x;
        public double y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    public static class Pixel {
        public int x;
        public int y;
        public Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public double x1;
    public double y1;
    public double x2;
    public double y2;
    public int width;
    public int height;

    public Camera(double x1, double y1, double x2, double y2, int w, int h) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        width = w;
        height = h;
    }

    public Camera(double unitsPerPixel, int w, int h) {
        x1 = - w * unitsPerPixel / 2;
        y1 = - h * unitsPerPixel / 2;
        x2 = w * unitsPerPixel / 2;
        y2 = h * unitsPerPixel / 2;
        width = w;
        height = h;
    }

    public void zoomCenter(double coeff) {
        coeff = 1 / coeff;
        double x = (x1 + x2) / 2;
        double y = (y1 + y2) / 2;
        x1 = x - (x - x1) * coeff;
        y1 = y - (y - y1) * coeff;
        x2 = x - (x - x2) * coeff;
        y2 = y - (y - y2) * coeff;
    }
    
    public void pan(double dx, double dy) {
        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;
    }

    public Pixel map(Point p) {
        //All y operations are reversed because the first pixel is at the top.
        int x = mapx(p.x);
        int y = mapy(p.y);
        return new Pixel(x, y);
    }

    public Point reverseMap(Pixel p) {
        double x = (double) p.x / width * (x2 - x1) + x1;
        double y = (double) p.y / height * (y1 - y2) + y2;
        return new Point(x, y);
    }

    public int mapx(double x) {
        return (int) ((x - x1) / (x2 - x1) * width);
    }

    public int mapy(double y) {
        return (int) ((y - y2) / (y1 - y2) * height);
    }

    public double reverseMapx(int x) {
        return (double) x / width * (x2 - x1) + x1;
    }

    public double reverseMapy(int y) {
        return (double) y / height * (y1 - y2) + y2;
    }

    public int mapxscalar(double scalar) {
        return (int) Math.abs(Math.ceil(scalar / (x2 - x1) * width));
    }

    public int mapyscalar(double scalar) {
        return (int) Math.abs(Math.ceil(scalar / (y1 - y2) * height));
    }

    public double reverseMapxscalar(int scalar) {
        return (double) Math.abs(scalar / width * (x2 - x1));
    }

    public double reverseMapyscalar(int scalar) {
        return (double) Math.abs(scalar / height * (y1 - y2));
    }

    /**
     * x coordinate per pixel value
     */
    public double xpp() {
        return (x2 - x1) / width;
    }

    /**
     * y coordinate per pixel value, negative because upwards for points is by default downwards for pixels
     */
    public double ypp() {
        return (y1 - y2) / height;
    }

}
