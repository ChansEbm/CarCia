package com.app.CarCia.ui.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.app.CarCia.AppKeyMap;
import com.app.CarCia.MainBinding;
import com.app.CarCia.R;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.broadcast.JPushReceiver;
import com.app.CarCia.broadcast.UpdateUIBroadcast;
import com.app.CarCia.entity.HomeBean;
import com.app.CarCia.impl.JPushListener;
import com.app.CarCia.impl.UpdateUIListener;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.LogTools;
import com.app.CarCia.ui.Fragment.Main.BrandFragment;
import com.app.CarCia.ui.Fragment.Main.HomeFragment;
import com.app.CarCia.ui.Fragment.Main.MoreFragment;
import com.app.CarCia.ui.Fragment.Main.VipFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends BaseAty implements RadioGroup.OnCheckedChangeListener,
        UpdateUIListener, JPushListener {

    private FragmentTransaction fragmentTransaction;
    private List<Fragment> fragmentList = new ArrayList<>();
    private Fragment fragment;
    private MainBinding mainBinding;
    private UpdateUIBroadcast broadcast;
    private HomeBean.ListEntity listEntity;
    private com.app.CarCia.broadcast.JPushReceiver jPushReceiver = new JPushReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mainBinding = (MainBinding) viewDataBinding;
    }

    @Override
    protected void initViews() {

        broadcast = new UpdateUIBroadcast();

        if (fragmentList.isEmpty()) {
            fragmentList.add(new HomeFragment());
            fragmentList.add(new BrandFragment());
            fragmentList.add(new VipFragment());
            fragmentList.add(new MoreFragment());
            fragment = fragmentList.get(0);
        }

        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_content, fragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void initEvents() {
        mainBinding.radioGroup.setOnCheckedChangeListener(this);
        broadcast.setListener(this);
        AppTools.registerBroadcast(broadcast, AppKeyMap.ACTION_JUMP, AppKeyMap.ACTION_INTO_BRAND);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus)
//            new ShareDialog(this).show();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onClick(int id, View view) {

    }

    private void changeFragment(int position) {
        Fragment newFragment = fragmentList.get(position);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment != newFragment) {
            if (!newFragment.isAdded()) {
                if (position == 1) {
                    Bundle bundle = new Bundle();
                    if (listEntity != null)
                        bundle.putSerializable("listEntity", listEntity);
                    newFragment.setArguments(bundle);
                }
                fragmentTransaction.hide(fragment).add(R.id.fragment_content, newFragment)
                        .commitAllowingStateLoss();
            } else {
                fragmentTransaction.hide(fragment).show(newFragment).commitAllowingStateLoss();
            }
            fragment = newFragment;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                changeFragment(0);
                break;
            case R.id.rb_brand:
                changeFragment(1);
                break;
            case R.id.rb_vip:
                changeFragment(2);
                break;
            case R.id.rb_more:
                changeFragment(3);
                break;
        }
    }

    @Override
    public void uiUpData(Intent intent) {
        listEntity = (HomeBean.ListEntity) intent.getSerializableExtra(AppKeyMap.ACTION_KEY);
        mainBinding.radioGroup.check(R.id.rb_brand);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (fragment instanceof BrandFragment) {
                if (((BrandFragment) fragment).onKeyDown(keyCode)) {
                    return true;
                }
            }
            android.os.Process.killProcess(Process.myPid());
        }
        return super.onKeyDown(keyCode, event);
    }

    public void jPush(Intent intent) {
        LogTools.w(intent.getAction());
        if (TextUtils.equals(JPushInterface.ACTION_MESSAGE_RECEIVED, intent.getAction())) {
            Bundle bundle = intent.getExtras();
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (extras != null)
                try {
                    JSONObject jsonObject = new JSONObject(extras);
                    String version = jsonObject.optString("version");
                    try {
                        PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this
                                .getPackageName(), 0);
                        float nowVersion = Float.parseFloat(packageInfo.versionName);
                        float updateVersion = Float.parseFloat(version);
                        if (nowVersion < updateVersion) {
                            String url = jsonObject.optString("url");
                            showUpdateDialog(updateVersion + "", url);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }
    }

    private void showUpdateDialog(String version, final String url) {
        final MaterialDialog materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("检查到新版本" + " " + version).setMessage("检查到新版本,是否需要更新?")
                .setPositiveButton(R.string
                        .positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent1.setClassName("com.android.browser", "com.android.browser" +
                                ".BrowserActivity").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        materialDialog.dismiss();
                    }
                }).setNegativeButton(R.string.negative, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        }).setCanceledOnTouchOutside(true).show();
    }

//    public class JPushReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            LogTools.i(intent.getAction());
//            jPush(intent);
//        }
//    }
}
