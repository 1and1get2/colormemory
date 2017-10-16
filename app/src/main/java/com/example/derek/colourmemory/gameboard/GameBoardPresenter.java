package com.example.derek.colourmemory.gameboard;

import android.support.annotation.NonNull;

import com.example.derek.colourmemory.gameboard.data.Tile;

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
        restartGame();
        mGameBoardView.updateCurrentScore(0);
        mGameBoardView.updateHighestScore(0);
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
        Tile[] tiles = Tile.generateTiles(4, 4, 2);
        checkNotNull(mGameBoardView).setUpTiles(4, 6, 0, tiles);
    }

    @Override
    public void pauseGame() {

    }

    @Override
    public void resumeGame() {

    }

    @Override
    public void submitScore(@NonNull String name) {

    }
}
