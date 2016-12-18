package com.naoto.yamaguchi.miita.presenter;

import android.content.Context;
import android.content.Intent;

import com.naoto.yamaguchi.miita.entity.User;
import com.naoto.yamaguchi.miita.model.CurrentUserModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.presenter.base.Presenter;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

/**
 * {@link com.naoto.yamaguchi.miita.activity.HomeActivity} Presenter.
 * <p>
 * Created by naoto on 2016/12/18.
 */

public final class HomePresenter extends Presenter<HomePresenter.View> {
    public interface View {
        void showLoggingProgress();

        void hideLoggingProgress();

        void showSnackbar(String message);

        void showErrorAlert(MiitaException e);
    }

    private final CurrentUserModel currentUserModel;
    private final CurrentUser currentUser;

    public HomePresenter(Context context) {
        super(context);
        this.currentUserModel = new CurrentUserModel();
        this.currentUser = CurrentUser.getInstance();
    }

    @Override
    public void onResume() {
        // NOOP
    }

    @Override
    public void onPause() {
        // NOOP
    }

    public void loadAccessToken(Intent intent) {
        if (intent == null) {
            return;
        }

        if (!this.currentUser.isAuthorize() &&
                this.currentUserModel.isExistCodeQuery(intent)) {
            this.view.showLoggingProgress();

            final String code = this.currentUserModel.getCodeQuery(intent);
            this.currentUserModel.request(code, new OnModelListener<User>() {
                @Override
                public void onSuccess(User results) {
                    view.showSnackbar("ログインしました");
                }

                @Override
                public void onError(MiitaException e) {
                    view.showErrorAlert(e);
                }

                @Override
                public void onComplete() {
                    view.hideLoggingProgress();
                }
            });
        }
    }
}
