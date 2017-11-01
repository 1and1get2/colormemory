package com.example.derek.colourmemory.gameboard;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.example.derek.colourmemory.gameboard.data.GameConfig;
import com.example.derek.colourmemory.gameboard.data.Tile;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.example.derek.colourmemory.util.Util.checkNotNull;

/**
 * Created by derek on 16/10/17.
 */

public class GameBoardPresenter implements GameBoardContract.Presenter {
    private final GameBoardContract.GameBoardView mGameBoardView;
    private final GameBoardContract.GameTilesView mGameTilesView;
    private final List<Tile> mCurrentFlippedTileList = new ArrayList<>();

    private GameConfig mGameConfig;
    private Handler mTimerHandler;
    private int mHighestScore;


    private final Runnable gameTimeUpdater = new Runnable() {
        @Override
        public void run() {
            if (mGameBoardView != null && checkNotNull(mGameConfig).isStarted()) {
                mGameBoardView.updateTimer(mGameConfig.getElapsedTime());
                mTimerHandler.postDelayed(gameTimeUpdater, GameConfig.TIMER_UPDATE_INTERVAL);
            }
        }
    };

    public GameBoardPresenter(GameBoardContract.GameBoardView mGameBoardView, GameBoardContract.GameTilesView mGameTilesView) {
        this.mGameBoardView = checkNotNull(mGameBoardView);
        this.mGameBoardView.setPresenter(this);
        this.mGameTilesView = checkNotNull(mGameTilesView);
        this.mGameTilesView.setPresenter(this);
    }

    public void applyGameConfig(GameConfig gameConfig) {
        this.mGameConfig = checkNotNull(gameConfig);
        initializeGame();
    }

    @Override
    public void start() {
        mTimerHandler = new Handler(Looper.getMainLooper());
        applyGameConfig(GameConfig.getGameConfig());
        initializeGame();
    }

    @Override
    public int getCurrentScore() {
        return mGameConfig.getCurrentScore();
    }

    @Override
    public int getHighestScore() {
        return mHighestScore;
    }

    private int fetchHighestScore() {
        // TODO: 17/10/17
        return 100;
    }

    @Override
    public void onTileClicked(int index) {
        // TODO: 17/10/17 update score
//        Timber.d("Clicked on tile index: %d", index);
        if (checkNotNull(mGameConfig).isInitialized()) {
            if (!checkNotNull(mGameConfig).isStarted()) {
                startGame();
            }
            // TODO: 17/10/17 update score
            Tile tile = checkNotNull(mGameConfig.getTiles())[index];
//            if (tile.getCurrentStatus() == Tile.STATUS_REMOVED) return;

            if (true) {
                mGameTilesView.updateTiles(tile.getCurrentStatus() == Tile.STATUS_HIDDEN ? Tile.STATUS_REVEALED : Tile.STATUS_HIDDEN, true, index);
                return;
            }


            if (mCurrentFlippedTileList.contains(tile)) {
                mCurrentFlippedTileList.remove(tile);
                mGameTilesView.updateTiles(Tile.STATUS_HIDDEN, true, tile.getIndex());
//                tile.setCurrentStatus(Tile.STATUS_HIDDEN);
            } else {
                boolean allMatches = true;
                mCurrentFlippedTileList.add(tile);

                if (mCurrentFlippedTileList.size() == 2) {
                    for (Tile t : mCurrentFlippedTileList) {
                        if (t.getColor() != tile.getColor()) {
                            allMatches = false;
                            break;
                        }
                    }
                } else {
                    allMatches = false;
                }

                if (allMatches) {
                    // remove all the tiles
                    for (Tile t : mCurrentFlippedTileList) {
                        t.setCurrentStatus(Tile.STATUS_REMOVED);
                        mGameTilesView.updateTiles(Tile.STATUS_REMOVED, true, t.getIndex());
                    }
                    mCurrentFlippedTileList.clear();
//                    tile.setCurrentStatus(Tile.STATUS_REMOVED);
                } else {
                    if (mCurrentFlippedTileList.size() == 2) {
                        // TODO: 17/10/17 wait for a second and turn over all the tiles
                        // remove all the tiles
                        for (Tile t : mCurrentFlippedTileList) {
//                            t.setCurrentStatus(Tile.STATUS_HIDDEN);
                            mGameTilesView.updateTiles(Tile.STATUS_HIDDEN, true, t.getIndex());
                        }
                        mCurrentFlippedTileList.clear();
//                    tile.setCurrentStatus(Tile.STATUS_HIDDEN);
                    }

                }
            }
            for (Tile t : mCurrentFlippedTileList) {
                t.setCurrentStatus(Tile.STATUS_REVEALED);
            }
//            mGameTilesView.refreshTiles(0);
            mGameBoardView.updateCurrentScore(getCurrentScore());
        } else {
            // ignore unless restart pressed
        }
    }



    @Override
    public void highScoreClicked() {
        // TODO: 17/10/17 bring up leader board
    }

    @Override
    public void initializeGame() {
        checkNotNull(mGameBoardView);
        checkNotNull(mGameConfig).initialize();


        mGameTilesView.setUpTiles(mGameConfig.getRow(), mGameConfig.getColumn(), 0, mGameConfig.getTiles());

        mHighestScore = fetchHighestScore();
        mGameBoardView.updateHighestScore(mHighestScore);
        mGameBoardView.updateCurrentScore(mGameConfig.getCurrentScore());
    }

    @Override
    public void startGame() {
        if (!checkNotNull(mGameConfig).isInitialized()) throw new RuntimeException("Game has not been initialized");
        mGameConfig.start();
        gameTimeUpdater.run();
    }

    @Override
    public void restartGame() {
        initializeGame();
    }

    @Override
    public void pauseGame() {

    }

    @Override
    public void resumeGame() {

    }

    private void gameFinished() {
        checkNotNull(mGameConfig).finish();
        mHighestScore = fetchHighestScore();
        mTimerHandler.removeCallbacks(gameTimeUpdater);
        mGameBoardView.updateTimer(mGameConfig.getElapsedTime());
        checkNotNull(mGameBoardView).updateHighestScore(mHighestScore);
    }

    @Override
    public void submitScore(@NonNull String name) {
        // TODO: 17/10/17
        Timber.d("submitting score %d, with name: %d", getCurrentScore(), name);
        if (checkNotNull(mGameConfig).isStarted()) {
            gameFinished();
        } else {
            initializeGame();
            startGame();
        }
    }
}
