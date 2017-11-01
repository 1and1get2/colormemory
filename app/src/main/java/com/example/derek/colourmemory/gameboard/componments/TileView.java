package com.example.derek.colourmemory.gameboard.componments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.derek.colourmemory.gameboard.data.Tile;
import com.example.derek.colourmemory.util.ColorUtil;
import com.example.derek.colourmemory.util.Util;

import static com.example.derek.colourmemory.util.Util.checkNotNull;

/**
 * Created by derek on 15/10/17.
 */

public class TileView extends View {
    private static final int FLIP_ANIMATION_HALF_DURATION = 250;

    // TODO: 16/10/17 tweak rounded shadow background
    // https://stackoverflow.com/questions/36121995/custom-view-build-outline-with-rounded-corners-for-its-shadow
    private Paint paint, maskPaint;
    private boolean animating;
    private int elevationX;
    private int elevationY;
    private int shadowStroke;
    private @ColorInt int shadowColor, hiddenShadowColor;

    private Tile mTile;


    public TileView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(Context context, @Nullable AttributeSet attrs, int defStyle) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTile = new Tile();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTile.getCurrentStatus() == Tile.STATUS_HIDDEN) {
                    mTile.setCurrentStatus(Tile.STATUS_REVEALED);
                } else {
                    mTile.setCurrentStatus(Tile.STATUS_HIDDEN);
                }
                invalidate();
            }
        });


        elevationX = (int) Util.dpToPx(3);
        elevationY = (int) Util.dpToPx(4);
        shadowStroke = Math.max(elevationX, elevationY);

        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        setWillNotDraw(false);



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

    public Tile getTile() {
        return mTile;
    }

    public void setTile(Tile tile) {
        this.mTile = checkNotNull(tile);
        shadowColor = ColorUtil.manipulateColor(tile.getColor(), 0.5f);
        hiddenShadowColor = ColorUtil.manipulateColor(tile.getBackgroundColour(), 0.5f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Timber.d("w: %d, h: %d", w, h);
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new CustomOutline(getWidth(), getHeight()));
        }*/
    }

    @Override
    public void onDraw(Canvas canvas) {
        checkNotNull(paint);
        checkNotNull(mTile);

        if (mTile.getCurrentStatus() == Tile.STATUS_REMOVED) {
            return;
        }

        // background shadow
        paint.setColor(mTile.getCurrentStatus() == Tile.STATUS_REVEALED ? shadowColor : hiddenShadowColor);
        // TODO: 17/10/17 remove
        paint.setColor(shadowColor);
        paint.setStrokeWidth(shadowStroke);
        canvas.drawRect(elevationX + getPaddingLeft(), elevationY + getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), paint);

        // card face
        paint.setStrokeWidth(0);
        paint.setColor(mTile.getCurrentStatus() == Tile.STATUS_REVEALED ? mTile.getColor() : mTile.getBackgroundColour());
        // TODO: 17/10/17 remove the next line
//        paint.setColor(mTile.getColor());
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight() - elevationX, getHeight() - getPaddingBottom() - elevationY, paint);

    }

    public void updateTiles(final int status, boolean animate) {
//        ObjectAnimator animation = ObjectAnimator.ofFloat(this, "rotationY", 0.0f, 360f);
//        animation.setDuration(FLIP_ANIMATION_HALF_DURATION);
//        animation.setRepeatCount(ObjectAnimator.INFINITE);
//        animation.setInterpolator(new AccelerateDecelerateInterpolator());
//        animation.start();
        if (status == Tile.STATUS_REMOVED) {

        } else {

            animate().cancel();
            clearAnimation();

            animate().rotationY(90)
                    .setDuration(FLIP_ANIMATION_HALF_DURATION)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            getTile().setCurrentStatus(status);
                            animate()
                                    .rotationY(status == Tile.STATUS_REVEALED ? 180 : 0).
                                    setDuration(FLIP_ANIMATION_HALF_DURATION)
                                    .setInterpolator(new DecelerateInterpolator())
                                    .start();
                        }
                    })
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
        }
    }

/*    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class CustomOutline extends ViewOutlineProvider {
        int width;
        int height;

        CustomOutline(int width, int height) {

            this.width = width;
            this.height = height;
        }

        @Override
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, width, height, 20);
        }
    }*/
}
