package com.app.baseapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedCornerImageView extends ImageView {

    private Path path;

    public RoundedCornerImageView(Context context) {
        super(context);
        init();
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        float radius = 18.0f;
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);

//        float radius = this.getWidth() / 2;
//        float x = 0 + radius;
//        float y = 0 + radius;
//        path.addCircle(x, y, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}