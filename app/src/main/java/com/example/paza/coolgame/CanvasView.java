package com.example.paza.coolgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.*;
import android.widget.Toast;
//import com.example.paza.coolgame.database.CGBaseHelper;


public class CanvasView extends View implements ICanvasView {
    private GameManager gameManager;
    private static int width;
    private static int height;
    private Paint paint;
    private Canvas canvas;
    private Toast toast;


    public CanvasView (Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        initWidthAndHeigth(context);
        intiPaint();
        gameManager = new GameManager(this, width, height);

    }

    private void intiPaint() {
        paint = new Paint();
        paint.setAntiAlias(true); //сглаживание
        paint.setStyle(Paint.Style.FILL); //Заполнение кружков
    }

    private void initWidthAndHeigth(Context context) {
        WindowManager windowsManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowsManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        width = point.x;
        height = point.y;
    }

    @Override
    public void showMessage(String text) {
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void redRaw() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
       // gameManager.onDraw(canvas);
        this.canvas = canvas;
        gameManager.onDraw();
    }

    @Override
    public void drawCircle(SimpleCircle circle) {
        paint.setColor(circle.getColor());
        canvas.drawCircle(circle.getX(), circle.getY(), circle.getRadius(), paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            gameManager.onTouchEvent(x,y);
        }
        invalidate();
        return true;
    }

}
