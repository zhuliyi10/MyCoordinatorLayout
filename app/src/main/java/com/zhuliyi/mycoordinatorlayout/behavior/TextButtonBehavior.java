package com.zhuliyi.mycoordinatorlayout.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.zhuliyi.mycoordinatorlayout.R;

/**
 * Created by Leory on 2017/3/13.
 */

public class TextButtonBehavior extends CoordinatorLayout.Behavior<TextView> {
    public TextButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency.getId()== R.id.btn_move;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        child.setX(dependency.getX()+200);
        child.setY(dependency.getY()+200);
        child.setText(dependency.getX()+":"+dependency.getY());
        return true;
    }
}
