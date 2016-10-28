package com.example.paza.coolgame;

import android.graphics.Color;

/**
 * Created by paza on 05.10.16.
 */
public class MainCircle extends SimpleCircle {

    public static final int INT_RADIUS = 50;
    public static final int MAIN_SPEED = 30;
    public static final int OUR_COLOR = Color.BLUE;

    public MainCircle(int x, int y) {
        super(x, y, INT_RADIUS);
        setColor(OUR_COLOR);
    }


    public void moveMainCircleOnTouchAt(int x1, int y1) {
        int dx = (x1 - x) * MAIN_SPEED / GameManager.getWidth();
        int dy = (y1 - y) * MAIN_SPEED / GameManager.getHeight();
        x+=dx;
        y+=dy;
    }

    public void reInitRadius() {
        radius = INT_RADIUS;
    }

    public void growRadius(SimpleCircle circle) { // увеличиваем радиус свой радиус + радиус съеденного круга
        radius = (int) Math.sqrt(Math.pow(radius, 2) + Math.pow(circle.radius, 2));
    }
}
