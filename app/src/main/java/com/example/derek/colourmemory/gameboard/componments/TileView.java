package com.example.derek.colourmemory.gameboard.componments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.transition.Explode;
import android.support.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;

import com.example.derek.colourmemory.gameboard.data.Tile;
import com.example.derek.colourmemory.util.Util;

import static com.example.derek.colourmemory.util.Util.checkNotNull;

/**
 * Created by derek on 15/10/17.
 */

public class TileView extends View {
    private Paint paint;
    private int elevationX;
    private int elevationY;
    private int shadowStroke;
    private @ColorInt int shadowColor;
    private @ColorInt int tileColor; //todo: remove this

    private Tile mTile;


    public TileView(Context context) {
        super(context);
        init(context, null);
    }

    public TileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Explode explode = new Explode();
                explode.setDuration(3000);
                explode.setInterpolator(new AnticipateOvershootInterpolator());
                TransitionManager.beginDelayedTransition((ViewGroup)getRootView().getParent(), explode);
            }
        });
    }

    public void init(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint();
        mTile = new Tile();


        elevationX = (int) Util.dpToPx(3);
        elevationY = (int) Util.dpToPx(4);
        shadowStroke = Math.max(elevationX, elevationY);

        tileColor = Tile.TileColors.getRandomTileColors(1)[0];
        shadowColor = Util.manipulateColor(tileColor, 0.5f);

        // TODO: 16/10/17 dynamic elevation and shadow coloring using view attribute

/*
        int[] attrsArray = new int[] {
                android.R.attr.elevation
        };
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PieChart,
                0, 0);

        try {

        } finally {
            a.recycle();
        }*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Timber.d("w: %d, h: %d", w, h);
    }

    @Override
    public void onDraw(Canvas canvas) {
        checkNotNull(paint);
        // background shadow
        paint.setColor(shadowColor);
        paint.setStrokeWidth(shadowStroke);
        canvas.drawRect(elevationX + getPaddingLeft(), elevationY + getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), paint);

        // card face
        paint.setStrokeWidth(0);
        paint.setColor(tileColor);
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight() - elevationX, getHeight() - getPaddingBottom() - elevationY, paint);

    }
}
