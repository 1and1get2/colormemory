package com.example.derek.colourmemory.gameboard;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.example.derek.colourmemory.base.BasePresenter;
import com.example.derek.colourmemory.base.BaseView;
import com.example.derek.colourmemory.gameboard.data.Tile;

/**
 * Created by derek on 15/10/17.
 */

public class GameBoardContract {

    interface GameBoardView extends BaseView<Presenter>{
        void updateHighestScore(int score);
        void updateCurrentScore(int score);
        void setSubmitVisibility(boolean visibility);
        void showSubmitScoreDialog();
        void updateLogo();
        void updateTimer(long currentTimeMilliSecond);

    }
    interface GameTilesView extends BaseView<Presenter>{
        void setUpTiles(int row, int column, @ColorInt int backgroundColour, @NonNull Tile[] tiles);
        void refreshTiles(int... indexes);
        void updateTiles(@Tile.Status int status, boolean animate, int... indexes);
    }

    interface View extends GameTilesView, GameBoardView, BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        int getCurrentScore();
        int getHighestScore();
        void onTileClicked(int index);
        void highScoreClicked();

        void initializeGame();
        void startGame();
        void restartGame();
        void pauseGame();
        void resumeGame();

        void submitScore(@NonNull String name);
    }
}
