package com.example.derek.colourmemory.gameboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.derek.colourmemory.R;
import com.example.derek.colourmemory.base.BaseFragment;
import com.example.derek.colourmemory.gameboard.componments.TileView;
import com.example.derek.colourmemory.gameboard.data.Tile;
import com.example.derek.colourmemory.util.Util;

import junit.framework.Assert;

import timber.log.Timber;

import static com.example.derek.colourmemory.util.Util.checkNotNull;

/**
 * Created by derek on 15/10/17.
 */

public class GameTilesFragment extends BaseFragment implements GameBoardContract.GameTilesView {
    private GameBoardContract.Presenter mPresenter;
    private OnTileClickedListener mOnTileClickedListener;
    private int currentRow, currentCol;


    private final View.OnClickListener mTileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof TileView && mOnTileClickedListener != null) {
                int index = ((TileView) v).getTile().getIndex();
                mOnTileClickedListener.onTileClicked(index);
                root.invalidate();
//                for(TileView tileView : mTileViews) {
//                    tileView.invalidate();
//                }
            } else {
                Timber.e("view is not of type TileView or mOnTileClickedListener is null");
            }
        }
    };

    private GridLayout root;
    private TileView[] mTileViews;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Timber.d("onAttach, root is null: %s, mTileViews: %s", root == null, mTileViews == null);
        if (getActivity() instanceof OnTileClickedListener) {
            mOnTileClickedListener = ((OnTileClickedListener) getActivity());
            Timber.d("onTileClickedListener set");
        } else {
            Timber.e("parent activity does not confirm to type: OnTileClickedListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Timber.d("onDetach, removing mOnTileClickedListener");
        mOnTileClickedListener = null;
        mTileViews = null;
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
    public void onDestroyView() {
        super.onDestroyView();
        Timber.d("onDestroyView");
//        root = null; // release reference for GC
    }

    @Override
    public void setPresenter(GameBoardContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void updateTiles(int status, boolean animate, int... indexes) {
        checkNotNull(mTileViews);
        for(int index : indexes) {
            TileView tileView = mTileViews[index];
            tileView.updateTiles(status, animate);
        }

    }

    @Override
    public void setUpTiles(int row, int column, int backgroundColour, @NonNull Tile[] tiles) {
        checkNotNull(root);
        checkNotNull(tiles);
        Assert.assertEquals(row * column, tiles.length);

        // force re-setup if col or row doesn't match
        if (mTileViews == null || mTileViews.length != row * column || currentRow != row || currentCol != column) {
            mTileViews = new TileView[row * column];
            currentCol = column;
            currentRow = row;
        }

        root.setRowCount(row);
        root.setColumnCount(column);


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int index = j + i * column;

                TileView tileView;

                // reuse existing tileView if possible
                if (mTileViews[index] != null) {
                    tileView = mTileViews[index];
                } else {
                    tileView = new TileView(getActivity());
                    mTileViews[index] = tileView;
                    GridLayout.Spec rowSpec = GridLayout.spec(i, 1, 1);
                    GridLayout.Spec colSpec = GridLayout.spec(j, 1, 1);
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
                    layoutParams.width = 0;
                    layoutParams.height = 0;
                    layoutParams.topMargin = layoutParams.bottomMargin = layoutParams.leftMargin = layoutParams.rightMargin = (int) Util.dpToPx(5);
                    root.addView(tileView, layoutParams);
                }
//                Timber.d("Getting tile at index: %d, row: %d, col: %d", index, i, j);
                tileView.setTile(tiles[index]);
                tileView.setOnClickListener(mTileOnClickListener);
            }
        }
    }

    @Override
    public void refreshTiles(int... indexes) {
        // TODO: 17/10/17
        root.invalidate();
        for (TileView tileView : mTileViews) {
            tileView.invalidate();
        }
    }



    public static GameTilesFragment newInstance() {
        Bundle args = new Bundle();
        GameTilesFragment fragment = new GameTilesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnTileClickedListener {
        void onTileClicked(int index);
    }
}
