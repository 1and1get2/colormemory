package com.example.derek.colourmemory.gameboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.derek.colourmemory.R;
import com.example.derek.colourmemory.base.BaseFragment;
import com.example.derek.colourmemory.gameboard.componments.TileView;
import com.example.derek.colourmemory.util.Util;

import static com.example.derek.colourmemory.util.Util.checkNotNull;

/**
 * Created by derek on 15/10/17.
 */

public class GameTilesFragment extends BaseFragment implements GameBoardContract.GameTilesView {
    private GridLayout root;


    public static GameTilesFragment newInstance() {
        Bundle args = new Bundle();
        GameTilesFragment fragment = new GameTilesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (GridLayout) inflater.inflate(R.layout.frag_gameboard, container, false);
        return root;
    }

    @Override
    public void setUpTiles(int row, int column, int backgroundColour, int[] colours) {
        checkNotNull(root);
        root.setRowCount(row);
        root.setColumnCount(column);


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                TileView tileView = new TileView(getActivity());

                GridLayout.Spec rowSpec = GridLayout.spec(i, 1, 1);
                GridLayout.Spec colSpec = GridLayout.spec(j, 1, 1);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec) ;
                layoutParams.width = 0;
                layoutParams.height = 0;
                layoutParams.topMargin = layoutParams.bottomMargin = layoutParams.leftMargin = layoutParams.rightMargin = (int) Util.dpToPx(5);
                root.addView(tileView, layoutParams);
            }
        }
    }
}
