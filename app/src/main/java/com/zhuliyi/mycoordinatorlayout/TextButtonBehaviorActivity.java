package com.zhuliyi.mycoordinatorlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class TextButtonBehaviorActivity extends AppCompatActivity {

    Button btnMove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_button_behavior);
        btnMove= (Button) findViewById(R.id.btn_move);
        btnMove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        view.setX(motionEvent.getRawX()-view.getWidth()/2);
                        view.setY(motionEvent.getRawY()-getStatusBarHeight()-view.getHeight()/2);
                        return true;
                }
                return false;
            }
        });
    }

    // 获取状态栏高度
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId =getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
