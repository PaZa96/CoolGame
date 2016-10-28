package com.example.paza.coolgame;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import com.example.paza.coolgame.database.DBHelper;

import java.util.ArrayList;

/**
 * Created by paza on 05.10.16.
 */
public class GameManager  extends AppCompatActivity {
    public static final int MAX_CIRCLES = 10;
    private MainCircle mainCircle;
    private CanvasView canvasView;
    private ArrayList<EnemyCircle> circles;
    private static int heigth;
    private static int width;
    private Cursor c;
    private Context mContext;
    public SQLiteDatabase db;
    public LoginActivity mLoginActivity = new LoginActivity();
    public DBHelper dbHelper;
    private long getId;

    public GameManager(CanvasView c, int w, int h) {
        this.canvasView = c;
        width = w;
        heigth = h;
        initMainCircle();
        initEnemyCircles();
    }

    private void initEnemyCircles() {
        circles = new ArrayList<EnemyCircle>();
        SimpleCircle mainCircleArea = mainCircle.getCircleArea();
        for (int i = 0; i < MAX_CIRCLES; i++){
            EnemyCircle circle;
            do {
                circle = EnemyCircle.getRandomCircle();
            } while (circle.isIntersect(mainCircleArea));
            circles.add(circle);
        }
        calculateAndSetCircleColor();
    }

    private void calculateAndSetCircleColor() {
        for (EnemyCircle circle : circles) {
            circle.setEnemyOrFoodColorDependsOn(mainCircle);
        }
    }

    public static int getWidth() {
        return width;
    }
    public static int getHeight() {
        return heigth;
    }
    private void initMainCircle() {
        mainCircle = new MainCircle(width / 2, heigth / 2);
    }

    public void onDraw() {
       canvasView.drawCircle(mainCircle);
        for (EnemyCircle circle : circles) {
            canvasView.drawCircle(circle);
        }

    }

    public void onTouchEvent(int x, int y) {
        mainCircle.moveMainCircleOnTouchAt(x,y);
        checkCollision();
        moveCircle();
    }

    private void checkCollision() {
        SimpleCircle circleForDel = null;
        for (EnemyCircle circle : circles) {
            if(mainCircle.isIntersect(circle)){
                if(circle.isSmallerThan(mainCircle)){
                    mainCircle.growRadius(circle);
                    circleForDel = circle;
                    calculateAndSetCircleColor();
                    break;
                } else {
                    gameEnd("YOU LOSE!");
                    mLoginActivity.addCount(DBHelper.COLUMN_LOSE_GAME);
                    return;
                }
            }
        }
        if(circleForDel!= null){
            circles.remove(circleForDel);
        }
        if(circles.isEmpty()){
            gameEnd("YOU WIN!");
            mLoginActivity.addCount(DBHelper.COLUMN_WIN_GAME);
        }
    }

    private void gameEnd(String text) {
        canvasView.showMessage(text);
        mainCircle.reInitRadius();
        initEnemyCircles();
        canvasView.redRaw();
    }

    private void moveCircle() {
        for (EnemyCircle circle : circles) {
            circle.moveOneStep();
        }
    }
}
