package com.app.CarCia.ui.Fragment.Main;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.app.CarCia.MoreLayout;
import com.app.CarCia.R;
import com.app.CarCia.adapters.JsonObjectListener;
import com.app.CarCia.base.BaseFgm;
import com.app.CarCia.dialog.DialDialog;
import com.app.CarCia.entity.UpdateBean;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.DataCleanManager;
import com.app.CarCia.tools.LogTools;
import com.app.CarCia.tools.OkHttpBuilder;
import com.app.CarCia.ui.Activity.UserGuideActivity;
import com.app.CarCia.ui.Activity.WebWebActivity;

import java.util.HashMap;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;


public class MoreFragment extends BaseFgm {

    private MoreLayout moreLayout;
    private PackageInfo packageInfo;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleBarTools(moreLayout.include.toolBar).setTitle(R.string.more);
    }

    @Override
    protected void initViews() {
        moreLayout = (MoreLayout) viewDataBinding;
        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo
                    (getActivity().getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initEvents() {
        moreLayout.flytCacheClear.setOnClickListener(this);
        moreLayout.flytContactCarCia.setOnClickListener(this);
        moreLayout.flytLogIn.setOnClickListener(this);
        moreLayout.flytTechnologySupport.setOnClickListener(this);
        moreLayout.flytUerGuide.setOnClickListener(this);
        moreLayout.flytCheckUpdate.setOnClickListener(this);
        moreLayout.tvVersion.setText(packageInfo.versionName);
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
            case R.id.flyt_check_update:
                checkUpdate();
                break;
        }
    }

    private void checkUpdate() {
        LogTools.w(packageInfo.versionName);
        Map<String, String> params = new HashMap<>();
        params.put("version", packageInfo.versionName);

        new OkHttpBuilder.GET().url("AppUpdate").params(params).entityClass(UpdateBean.class)
                .enqueue
                        (getActivity(), new JsonObjectListener<UpdateBean>() {

                            @Override
                            public void onJsonObjectResponse(UpdateBean updateBean) {
                                if (TextUtils.equals(updateBean.getStatus(), "1")) {
                                    showUpdateDialog(updateBean.getPackage());
                                } else {
                                    AppTools.showSnackBar(getActivity().findViewById(R.id
                                            .fragment_content), "目前版本为最新版本");
                                }
                            }

                        });
    }

    private void showUpdateDialog(final String apkUrl) {
        final MaterialDialog materialDialog = new MaterialDialog(getActivity());
        materialDialog.setTitle(R.string.check_update).setMessage("检查到新版本,是否需要更新?")
                .setPositiveButton(R.string.positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(apkUrl))
                                .setClassName("com.android.browser", "com.android.browser" +
                                        ".BrowserActivity");
                        startActivity(intent);
                        materialDialog.dismiss();
                    }
                }).setNegativeButton(R.string.negative, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        }).setCanceledOnTouchOutside(true)
                .show();
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
