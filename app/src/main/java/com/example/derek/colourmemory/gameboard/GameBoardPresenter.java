package com.example.derek.colourmemory.gameboard;

import android.support.annotation.NonNull;

import static com.example.derek.colourmemory.util.Util.checkNotNull;

/**
 * Created by derek on 16/10/17.
 */

public class GameBoardPresenter implements GameBoardContract.Presenter{
    private final GameBoardContract.View mGameBoardView;

    public GameBoardPresenter(GameBoardContract.View mGameBoardView) {
        this.mGameBoardView = checkNotNull(mGameBoardView);
        this.mGameBoardView.setPresenter(this);
    }

    @Override
    public void start() {
        checkNotNull(mGameBoardView).setUpTiles(4, 4, 0, new int[]{0});
    }

    @Override
    public int getCurrentScore() {
        return 0;
    }

    @Override
    public int getHighestScore() {
        return 0;
    }

    @Override
    public void tileClicked() {

    }

    @Override
    public void highScoreClicked() {

    }

    @Override
    public void restartGame() {

    }

    @Override
    public void submitScore(@NonNull String name) {

    }
}
