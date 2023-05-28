package GfxEngine;

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
        this.width = w;
        this.height = h;
    }

    public Camera(double unitsPerPixel, int w, int h) {
        this.x1 = - w * unitsPerPixel / 2;
        this.y1 = - h * unitsPerPixel / 2;
        this.x2 = w * unitsPerPixel / 2;
        this.y2 = h * unitsPerPixel / 2;
        this.width = w;
        this.height = h;
    }

    public void zoomCenter(double coeff) {
        coeff = 1 / coeff;
        double x = (this.x1 + this.x2) / 2;
        double y = (this.y1 + this.y2) / 2;
        this.x1 = x - (x - this.x1) * coeff;
        this.y1 = y - (y - this.y1) * coeff;
        this.x2 = x - (x - this.x2) * coeff;
        this.y2 = y - (y - this.y2) * coeff;
    }
    
    public void pan(double dx, double dy) {
        this.x1 += dx;
        this.x2 += dx;
        this.y1 += dy;
        this.y2 += dy;
    }

    public Pixel map(Point p) {
        int x = (int) ((p.x - this.x1) / (this.x2 - this.x1) * this.width);
        int y = (int) ((p.y - this.y1) / (this.y2 - this.y1) * this.height);
        return new Pixel(x, y);
    }

    public Point reverseMap(Pixel p) {
        double x = (double) p.x / this.width * (this.x2 - this.x1) + this.x1;
        double y = (double) p.y / this.height * (this.y2 - this.y1) + this.y1;
        return new Point(x, y);
    }

    public int mapx(double x) {
        return (int) ((x - this.x1) / (this.x2 - this.x1) * this.width);
    }

    public int mapy(double y) {
        return (int) ((y - this.y1) / (this.y2 - this.y1) * this.height);
    }

    public double reverseMapx(int x) {
        return (double) x / this.width * (this.x2 - this.x1) + this.x1;
    }

    public double reverseMapy(int y) {
        return (double) y / this.height * (this.y2 - this.y1) + this.y1;
    }

    public int mapxscalar(double scalar) {
        return (int) (scalar / (this.x2 - this.x1) * this.width);
    }

    public int mapyscalar(double scalar) {
        return (int) (scalar / (this.y2 - this.y1) * this.height);
    }

    public double reverseMapxscalar(int scalar) {
        return (double) scalar / this.width * (this.x2 - this.x1);
    }

    public double reverseMapyscalar(int scalar) {
        return (double) scalar / this.height * (this.y2 - this.y1);
    }

}
