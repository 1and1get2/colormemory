package com.example.derek.colourmemory.gameboard;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.example.derek.colourmemory.base.BasePresenter;
import com.example.derek.colourmemory.base.BaseView;

/**
 * Created by derek on 15/10/17.
 */

public class GameBoardContract {

    interface GameBoardView {
        void updateHighestScore(int score);
        void updateCurrentScore(int score);
        void updateLogo();
    }
    interface GameTilesView {
        void setUpTiles(int row, int column, @ColorInt int backgroundColour, @ColorInt int[] colours);
    }

    interface View extends GameBoardView, GameTilesView, BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        int getCurrentScore();
        int getHighestScore();
        void tileClicked();
        void highScoreClicked();
        void restartGame();
        void submitScore(@NonNull String name);
    }
}
