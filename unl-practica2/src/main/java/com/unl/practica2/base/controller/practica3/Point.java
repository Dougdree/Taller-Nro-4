package com.unl.practica2.base.controller.practica3;

public class Point {
    public Integer r;
    public Integer c;
    public Point parent;

    public Point(int x, int y, Point p) {
        r = x;
        c = y;
        parent = p;
    }

    public Point opposite() {
        if (this.r.compareTo(parent.r) != 0) {
            return new Point(this.r + this.r.compareTo(parent.r), this.c, this);
        }
        if (this.c.compareTo(parent.c) != 0) {
            return new Point(this.r, this.c + this.c.compareTo(parent.c), this);
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return r.equals(point.r) && c.equals(point.c);
    }
}

