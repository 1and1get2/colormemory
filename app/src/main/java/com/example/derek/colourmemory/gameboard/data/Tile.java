package com.example.derek.colourmemory.gameboard.data;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.derek.colourmemory.util.ColorUtil;
import com.example.derek.colourmemory.util.Util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

;

/**
 * Created by derek on 16/10/17.
 */

public class Tile implements Parcelable {
    private final @ColorInt int DEFAULT_BACKGROUND_COLOR = Color.GRAY;

    private @ColorInt int color;
    private @ColorInt int backgroundColour = DEFAULT_BACKGROUND_COLOR;
    private @Status int currentStatus;
    private int index;


    public static final int STATUS_HIDDEN = 0;
    public static final int STATUS_REVEALED = 1;
    public static final int STATUS_REMOVED = 2;
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_HIDDEN, STATUS_REVEALED, STATUS_REMOVED})
    public @interface Status{}


    public Tile() {}

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public @ColorInt int getColor() {
        return color;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    public int getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(int backgroundColour) {
        this.backgroundColour = backgroundColour;
    }


    /** generator */
    public static @NonNull Tile[] generateTiles(int count, int tilesPerColor, @Nullable Tile[] oldTiles) {
        if (count <= 1 || tilesPerColor <= 1) throw new RuntimeException("Invalid parameter");

        Tile[] tiles;
        if (oldTiles != null && oldTiles.length == count) {
            // reuse old tiles
            tiles = oldTiles;
        } else {
            tiles = new Tile[count];
        }

        int colorsNeeded = (int) Math.round(count / ((double) tilesPerColor));
//        Timber.d("generating %d tiles, tilesPerColor: %d, colorsNeeded: %d", count, tilesPerColor, colorsNeeded);

        @ColorInt int[] colors = ColorUtil.uniqueColorGenerator(colorsNeeded);
        for (int i = 0; i < colorsNeeded; i++) {
            @ColorInt int color = colors[i];
            for (int j = 0; j < tilesPerColor; j++) {
                if (i * tilesPerColor + j > count - 1) break;
                tiles[i * tilesPerColor + j] = new Tile();
                tiles[i * tilesPerColor + j].setColor(color);
            }
        }

        Util.shuffleArray(tiles);

        // resort index after shuffle
        for (int i = 0; i < tiles.length; i++) {
            tiles[i].setIndex(i);
        }
        return tiles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /** Parcelable */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.color);
        dest.writeInt(this.backgroundColour);
        dest.writeInt(this.currentStatus);
        dest.writeInt(this.index);
    }

    protected Tile(Parcel in) {
        this.color = in.readInt();
        this.backgroundColour = in.readInt();
        this.currentStatus = in.readInt();
        this.index = in.readInt();
    }

    public static final Parcelable.Creator<Tile> CREATOR = new Parcelable.Creator<Tile>() {
        @Override
        public Tile createFromParcel(Parcel source) {
            return new Tile(source);
        }

        @Override
        public Tile[] newArray(int size) {
            return new Tile[size];
        }
    };
}
