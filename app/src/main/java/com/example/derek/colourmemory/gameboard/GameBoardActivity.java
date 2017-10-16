package com.example.derek.colourmemory.gameboard;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.derek.colourmemory.R;
import com.example.derek.colourmemory.base.BaseActivity;
import com.example.derek.colourmemory.util.ActivityUtils;

import static com.example.derek.colourmemory.util.Util.checkNotNull;

/**
 * Created by derek on 15/10/17.
 */

public class GameBoardActivity extends BaseActivity implements GameBoardContract.View{
    GameTilesFragment gameTilesFragment;
    GameBoardContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameboard);

        gameTilesFragment = GameTilesFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), gameTilesFragment, R.id.game_tile_root);

        // TODO: 16/10/17 implement dependency injection
        new GameBoardPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNotNull(mPresenter).start();
    }

    @Override
    public void updateHighestScore(int score) {

    }

    @Override
    public void updateCurrentScore(int score) {

    }

    @Override
    public void updateLogo() {

    }

    @Override
    public void setUpTiles(int row, int column, int backgroundColour, int[] colours) {
        checkNotNull(gameTilesFragment).setUpTiles(row, column, backgroundColour, colours);
    }

    @Override
    public void setPresenter(GameBoardContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }
}
