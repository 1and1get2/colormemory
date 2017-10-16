package com.example.derek.colourmemory.leaderboard;

import com.example.derek.colourmemory.base.BasePresenter;
import com.example.derek.colourmemory.base.BaseView;

/**
 * Created by derek on 15/10/17.
 */

public class LeaderboardContract {

    interface View extends BaseView<Presenter> {

        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);

        void populateTask();

        boolean isDataMissing();
    }
}
