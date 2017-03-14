package com.zhuliyi.mycoordinatorlayout.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import com.zhuliyi.mycoordinatorlayout.R;

/**
 * Created by Leory on 2017/3/13.
 */

public class BarBehavior extends CoordinatorLayout.Behavior<NestedScrollView> {
    int offsetTotal = 0;
    boolean scrolling = false;
    public BarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, NestedScrollView child, int layoutDirection) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if(params!=null&&params.height==CoordinatorLayout.LayoutParams.MATCH_PARENT){
            child.layout(0,0,parent.getWidth(),parent.getHeight());
//            child.setTranslationY(getHeaderHeight(parent));
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, NestedScrollView child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, NestedScrollView child, View target, int dx, int dy, int[] consumed) {
        // 在这个方法里面只处理向上滑动
        if(dy < 0){
            return;
        }

        float transY =  child.getTranslationY() - dy;
        if(transY >= 0){
            child.setTranslationY(transY);
            consumed[1]= dy;
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, NestedScrollView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
     // 在这个方法里只处理向下滑动
        if(dyUnconsumed >0){
            return;
        }else {

            float transY = child.getTranslationY() - dyUnconsumed;
            if (transY > 0 && transY < getHeaderHeight(child)) {
                child.setTranslationY(transY);
            }
        }
    }


    /**
     * 获取Header 高度
     * @return
     */
    public int getHeaderHeight(View view){
        return view.getResources().getDimensionPixelOffset(R.dimen.bar_height);
    }
}
