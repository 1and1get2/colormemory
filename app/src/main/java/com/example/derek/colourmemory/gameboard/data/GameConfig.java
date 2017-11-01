package com.example.derek.colourmemory.gameboard.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * stores current game info/status, as a parcelable object, it can be saved to resume an interrupted game
 * Created by derek on 17/10/17.
 */

public class GameConfig implements Parcelable {
    public static final int INITIAL_SCORE = 100;
    public static final int TIMER_UPDATE_INTERVAL = 5000;


    private Tile[] mTiles;
    private int tilesPerColor;

    private int row, column, count;
    private int status;
    private int mCurrentScore;

    private long elapsedTime, currentStartTime;

    public static final int GAME_INITIALIZED = 0b1;
    public static final int GAME_STATUS_MASK = 0b1111_0000;
    public static final int GAME_STATUS_STARTED = 0b0001_0000;
    public static final int GAME_STATUS_PAUSED = 0b0010_0000;

    public int getTileCount() {
        return (row != 0 && column != 0) ? row * column : count ;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void initialize() {
        mTiles = Tile.generateTiles(getTileCount(), 2, mTiles);
        status = GAME_INITIALIZED;
        mCurrentScore = INITIAL_SCORE;
    }

    public boolean isInitialized() {
        return (status & GAME_INITIALIZED) == GAME_INITIALIZED;
    }

    public Tile[] getTiles() {
        return mTiles;
    }

    public int getCurrentScore() {
        return mCurrentScore;
    }


    public void start() {
        currentStartTime = System.currentTimeMillis();
    }

    public boolean isStarted() {
        return (status & GAME_STATUS_STARTED) == GAME_STATUS_STARTED;
    }



    public void pause() {
        if ((status & GAME_STATUS_MASK) == GAME_STATUS_PAUSED) {
            throw new RuntimeException("Game is already paused");
        }
        if (currentStartTime == 0) {
            throw new RuntimeException("Game start time is unknown");
        }
        elapsedTime += (System.currentTimeMillis() - currentStartTime);
        status = status ^ GAME_STATUS_MASK | GAME_STATUS_PAUSED;
    }

    public void resume() {
        if ((status & GAME_STATUS_MASK) != GAME_STATUS_PAUSED) {
            throw new RuntimeException("Game is not paused");
        }
        currentStartTime = System.currentTimeMillis();
        status = status ^ GAME_STATUS_MASK | GAME_STATUS_STARTED;

    }

    public void finish() {
        status = 0;
    }
    public long getElapsedTime() {
        return elapsedTime += (System.currentTimeMillis() - currentStartTime);
    }

    // hard coded game config
    public static GameConfig getGameConfig() {
        GameConfig gameConfig = new GameConfig();
        gameConfig.row = 4;
        gameConfig.column = 4;
        gameConfig.tilesPerColor = 2;
        return gameConfig;
    }


    /* Parcelable */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.mTiles, flags);
        dest.writeInt(this.tilesPerColor);
        dest.writeInt(this.row);
        dest.writeInt(this.column);
        dest.writeInt(this.count);
        dest.writeInt(this.status);
        dest.writeInt(this.mCurrentScore);
        dest.writeLong(this.elapsedTime);
        dest.writeLong(this.currentStartTime);
    }

    public GameConfig() {
    }

    protected GameConfig(Parcel in) {
        this.mTiles = in.createTypedArray(Tile.CREATOR);
        this.tilesPerColor = in.readInt();
        this.row = in.readInt();
        this.column = in.readInt();
        this.count = in.readInt();
        this.status = in.readInt();
        this.mCurrentScore = in.readInt();
        this.elapsedTime = in.readLong();
        this.currentStartTime = in.readLong();
    }

    public static final Parcelable.Creator<GameConfig> CREATOR = new Parcelable.Creator<GameConfig>() {
        @Override
        public GameConfig createFromParcel(Parcel source) {
            return new GameConfig(source);
        }

        @Override
        public GameConfig[] newArray(int size) {
            return new GameConfig[size];
        }
    };
}
