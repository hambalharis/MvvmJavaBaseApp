package com.app.baseapp.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

/**
 * Created by Vinod Kumar (Aug 2019).
 */
public class SquareWidthMajorCardView extends CardView {

    public SquareWidthMajorCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        this.setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());

    }
}
