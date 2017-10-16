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

    interface GameBoardView {
        void updateHighestScore(int score);
        void updateCurrentScore(int score);
        void setSubmitVisibility(boolean visibility);
        void showSubmitScoreDialog();
        void updateLogo();

    }
    interface GameTilesView {
        void setUpTiles(int row, int column, @ColorInt int backgroundColour, @NonNull Tile[] tiles);
    }

    interface View extends GameTilesView, GameBoardView, BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        int getCurrentScore();
        int getHighestScore();
        void tileClicked();
        void highScoreClicked();
        void restartGame();
        void pauseGame();
        void resumeGame();

        void submitScore(@NonNull String name);
    }
}
