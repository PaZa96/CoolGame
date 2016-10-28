package com.example.paza.coolgame;

/**
 * Created by paza on 10.10.16.
 */
public class SimpleCircle {
    protected int x;
    protected int y;
    protected int radius;
    private int color;


    public SimpleCircle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color){
        this.color = color;
    }

    public SimpleCircle getCircleArea() {
        return new SimpleCircle(x, y, radius * 2);
    }

    public boolean isIntersect(SimpleCircle mainCircleArea) {
        return radius + mainCircleArea.radius >= Math.sqrt(Math.pow(x - mainCircleArea.x, 2) + Math.pow(y - mainCircleArea.y, 2));
    }
}
