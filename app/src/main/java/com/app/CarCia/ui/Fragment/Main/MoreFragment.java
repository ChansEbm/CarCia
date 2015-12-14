package com.app.CarCia.ui.Fragment.Main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.FrameLayout;

import com.app.CarCia.MoreLayout;
import com.app.CarCia.R;
import com.app.CarCia.base.BaseFgm;
import com.app.CarCia.dialog.DialDialog;
import com.app.CarCia.tools.DataCleanManager;
import com.app.CarCia.ui.Activity.UserGuideActivity;
import com.app.CarCia.ui.Activity.WebWebActivity;


public class MoreFragment extends BaseFgm {

    private MoreLayout moreLayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleBarTools(moreLayout.include.toolBar).setTitle(R.string.more);
    }

    @Override
    protected void initViews() {
        moreLayout = (MoreLayout) viewDataBinding;
    }

    @Override
    protected void initEvents() {
        moreLayout.flytCacheClear.setOnClickListener(this);
        moreLayout.flytContactCarCia.setOnClickListener(this);
        moreLayout.flytLogIn.setOnClickListener(this);
        moreLayout.flytTechnologySupport.setOnClickListener(this);
        moreLayout.flytUerGuide.setOnClickListener(this);
    }

    @Override
    protected void onClick(int id, View view) {
        switch (id) {
            case R.id.flyt_log_in:
                start(WebWebActivity.class);
                break;
            case R.id.flyt_contact_CarCia:
                new DialDialog(getActivity(), null).dial("86-757-82013800");
                break;
            case R.id.flyt_cache_clear:
                DataCleanManager.cleanApplicationData(getActivity(), new String[]{});
                showMarginSnackBar();
                break;
            case R.id.flyt_uer_guide:
                start(UserGuideActivity.class);
                break;

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_more;
    }

    @Override
    public boolean isHasOptionsMenu() {
        return false;
    }

    @Override
    public int menuRes() {
        return 0;
    }

    private void showMarginSnackBar() {
        Snackbar snackbar = Snackbar.make(moreLayout.getRoot(), "清理完成", Snackbar.LENGTH_SHORT);
        View sView = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) sView
                .getLayoutParams();
        int bottomMargin = getActivity().findViewById(R.id.radioGroup).getHeight();
        params.bottomMargin = bottomMargin;
        sView.setBackgroundColor(getActivity().getResources().getColor(R.color
                .color_snack_bar_background));
        sView.setLayoutParams(params);
        snackbar.show();
    }
}
