package com.zhuliyi.mycoordinatorlayout.behavior;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.zhuliyi.mycoordinatorlayout.R;

import java.lang.ref.WeakReference;

/**
 * Created by Leory on 2017/3/13.
 */

public class FixBarBehavior extends CoordinatorLayout.Behavior<ImageView> {
    int offsetTotal = 0;
    boolean scrolling = false;
    private WeakReference<View>weakReference;
    public FixBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, ImageView child, int layoutDirection) {
        View dependencyView=getDependencyView();
        int headerHeight=child.getHeight();
        dependencyView.layout(0,0,parent.getWidth(),parent.getHeight());
        dependencyView.setTranslationY(headerHeight);
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        if(dependency!=null&&dependency.getId()==R.id.ll_first){
            weakReference=new WeakReference<>(dependency);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        float rate=dependency.getTranslationY()/child.getHeight();
//        child.setAlpha(rate);
        float scale= (float) (1+0.4*(1-rate));
        child.setScaleX(scale);
        child.setScaleY(scale);
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ImageView child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, ImageView child, View target, int dx, int dy, int[] consumed) {
        // 在这个方法里面只处理向上滑动
        if(dy < 0){
            return;
        }

        View dependencyView=getDependencyView();
        float newTransY =  dependencyView.getTranslationY() - dy;
        if(newTransY >0){
            dependencyView.setTranslationY(newTransY);
            consumed[1]= dy;
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, ImageView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
     // 在这个方法里只处理向下滑动
        if(dyUnconsumed >0){
            return;
        }
        View dependencyView=getDependencyView();
            float newTransY = dependencyView.getTranslationY() - dyUnconsumed;
            if (newTransY > 0 && newTransY < child.getHeight()) {
                dependencyView.setTranslationY(newTransY);
            }

    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, ImageView child, View target, float velocityX, float velocityY) {
        return onStopDragging(child,velocityY);
    }



    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, ImageView child, View target) {
        onStopDragging(child,0);
    }

    /**
     * 获取Header 高度
     * @return
     */
    public int getHeaderHeight(View view){
        return view.getResources().getDimensionPixelOffset(R.dimen.bar_height);
    }

    private View getDependencyView(){
        if(weakReference!=null){
           return weakReference.get();
        }
        return null;
    }

    private boolean onStopDragging(View child,float velocity){
        View dependencyView=getDependencyView();
        if(dependencyView.getTranslationY()==0||dependencyView.getTranslationY()==child.getHeight())return false;
        boolean isShow;
        if(Math.abs(velocity)<800){
            if(dependencyView.getTranslationY()<child.getHeight()/2){
                isShow=false;
            }else {
                isShow=true;
            }
            velocity=800;
        }else {
            if(velocity>0){
                isShow=false;
            }else {
                isShow=false;
            }
        }
        float endTranY=isShow?child.getHeight():0;
        startAnmintion(dependencyView,dependencyView.getTranslationY(),endTranY,velocity);
        return true;
    }
    private void startAnmintion(final View view, float startTranY, float endTranY,float velocity){
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(startTranY,endTranY);
        valueAnimator.setDuration((long) (200000/(Math.abs(velocity))));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setTranslationY((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }
}
