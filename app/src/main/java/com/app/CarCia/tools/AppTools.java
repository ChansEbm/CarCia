package com.app.CarCia.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.app.CarCia.dialog.LoadingDialog;
import com.app.CarCia.impl.ImageLoadComplete;
import com.baidu.location.BDLocationListener;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.List;

/**
 * Created by ChanZeeBm on 2015/9/10.
 */
public class AppTools {
    private static Context context;

    public static void init(Context context) {
        AppTools.context = context;
    }

    /**
     * 开启选择图片(系统)
     *
     * @return
     */
    public static void PICTURE(AppCompatActivity appCompatActivity, int requestCode) {
        appCompatActivity.startActivityForResult(SystemTools.PICTURE(), requestCode);
    }

    /**
     * 拨号
     *
     * @param phoneNum
     * @return
     */
    public static void CALL(String phoneNum) {
        context.startActivity(SystemTools.CALL(phoneNum).addFlags(Intent
                .FLAG_ACTIVITY_NEW_TASK));
    }

    public static void CAMERA(AppCompatActivity appCompatActivity, int requestCode) {
        appCompatActivity.startActivityForResult(SystemTools.CAMERA(), requestCode);
    }

    /**
     * 设置
     *
     * @return
     */
    public static void SETTING() {
        context.startActivity(SystemTools.SETTING().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * WIFI
     *
     * @return
     */
    public static Intent WIFI() {
        return SystemTools.WIFI();
    }

    /**
     * 加载图像
     *
     * @param uri
     * @param simpleDraweeView
     * @param imageLoadComplete
     */
    public static void disPlayImage(String uri, SimpleDraweeView simpleDraweeView,
                                    ImageLoadComplete imageLoadComplete) {
        FrescoTools.getInstance().displayImage(uri, simpleDraweeView, imageLoadComplete);
    }

    //转换毫秒到时分秒
    public static String convertMillions(long millions) {
        return Formatter.convertMillions(millions);
    }

    /**
     * 格式化时间 年月日时分秒(默认格式)
     *
     * @param date
     * @return
     */
    public static String formatTime(java.util.Date date) {
        return Formatter.formatTime(date);
    }

    /**
     * 格式化时间 (自定义格式)
     *
     * @param date  date
     * @param regex 格式
     * @return 格式化后的时间
     */
    public static String fotmatTime(Date date, String regex) {
        return Formatter.formatTime(date, regex);
    }


    /**
     * string转换为html输出
     *
     * @param html
     * @return
     */
    public static Spanned fromHtml(String html) {
        return Html.fromHtml(html);
    }

    /**
     * px 2 dip
     *
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        return DensityTools.px2dip(context, px);
    }

    /**
     * dip 2 px
     *
     * @param dip
     * @return
     */
    public static int dip2px(int dip) {
        return DensityTools.dip2px(context, dip);
    }

    public static int getStatusBarHeight() {
        return DensityTools.getStatusHeight(context);
    }

    public static void removeAllActivitys() {
        UITools.removeAllActivities();
    }

    public static void removeSingleActivity(FragmentActivity fragmentActivity) {
        UITools.removeSingleActivity(fragmentActivity);
    }

    public static void addActivity(FragmentActivity fragmentActivity) {
        UITools.addActivity(fragmentActivity);
    }

    /**
     * 打开ExpandableListView
     *
     * @param expandableListView
     */
    public static void expandGroup(ExpandableListView expandableListView, boolean isExpand) {
        ArrayListViewTools.expandGroup(expandableListView, isExpand);
    }

    /**
     * 计算高度
     *
     * @param absListView
     */
    public static void measureExpandableListViewHeight(AbsListView absListView) {
        ArrayListViewTools.measureAbsListViewHeight(absListView);
    }

    /**
     * 普通SnackBar
     *
     * @param view
     * @param charSequence
     */
    public static void showSnackBar(View view, CharSequence charSequence) {
        SnakeTools.getInstance().showSnackBar(context, view, charSequence);
    }

    /**
     * 带action的SnackBar
     *
     * @param view
     * @param charSequence
     * @param actionCharSequence
     * @param listener
     */
    public static void showSnackbar(View view, CharSequence charSequence, CharSequence
            actionCharSequence, View.OnClickListener listener) {
        SnakeTools.getInstance().showSnackBarWithAction(context, view, charSequence,
                actionCharSequence, listener);
    }


    /**
     * 从Assets里面获取文件
     *
     * @param fileName
     * @return
     */
    public static String getFromAssets(@NonNull String fileName) {
        return ParserTools.getFromAssets(context, fileName);
    }

    /**
     * 解析JsonObject s
     *
     * @param jsonArray
     * @return
     */
    public static List<JSONObject> parseJson(@NonNull JSONArray jsonArray) {
        return ParserTools.parseJson(jsonArray);
    }

    /**
     * 解析JsonObject s
     *
     * @param jsonObject
     * @return
     */
    public static List<JSONObject> parseJson(@NonNull JSONObject jsonObject) {
        return ParserTools.parseJson(jsonObject);
    }

    /**
     * 初始化JsonObject
     *
     * @param jsonObject
     */
    public static void initializeJsonObject(@NonNull JSONObject jsonObject) {
        ParserTools.initializeJsonObject(jsonObject);
    }

    /**
     * 获取JsonObject的字符
     *
     * @param key
     * @return
     */
    public static String optStringValue(@NonNull String key) {
        return ParserTools.optStringValue(key);
    }

    private static LocationTools locationTools;

    /**
     * 开始定位
     *
     * @param listener 定位完后的回调
     */
    public static void locate(BDLocationListener listener) {
        if (locationTools == null)
            locationTools = new LocationTools(context, listener);
        locationTools.start();
    }

    /**
     * 停止定位
     */
    public static void stopLocate() {

        if (locationTools == null)
            return;
        locationTools.stop();
    }

    /**
     * 发送广播
     *
     * @param bundle
     * @param action
     */
    public static void sendBroadcast(@NonNull Bundle bundle, @NonNull String action) {
        BroadcastTools.sendBroadcast(context, bundle, action);
    }

    /**
     * 注册广播
     *
     * @param receiver 广播
     * @param actions  拦截的广播s
     */
    public static void registerBroadcast(@NonNull BroadcastReceiver receiver, @NonNull String...
            actions) {
        BroadcastTools.registerReceiver(context, receiver, actions);
    }

    /**
     * 取消广播
     *
     * @param receiver
     */
    public static void unregisterBroadcast(@NonNull BroadcastReceiver receiver) {
        BroadcastTools.unRegisterReceiver(context, receiver);
    }

    /**
     * 默认横向Decoration (view是纵向)
     *
     * @return
     */
    public static RecyclerView.ItemDecoration defaultHorizontalDecoration() {
        return DefaultDecorationTools.defaultHorizontalDecoration(context);
    }

    /**
     * 默认纵向Decoration(view是横向)
     *
     * @return
     */
    public static RecyclerView.ItemDecoration defaultVerticalDecoration() {
        return DefaultDecorationTools.defaultVerticalDecoration(context);
    }

    /**
     * 设置窗体背景颜色(多用于PopupWindow弹出时)
     *
     * @param appCompatActivity
     * @param alpha
     */
    public static void setWindowBackground(AppCompatActivity appCompatActivity, float alpha) {
        WindowManagerTools.setWindowBackground(appCompatActivity, alpha);
    }


    /**
     * 加载Dialog
     *
     * @param fragmentActivity
     */
    public static LoadingDialog loadingDialog;

    public static void showLoadingDialog(FragmentActivity fragmentActivity) {
        if (loadingDialog == null)
            loadingDialog = new LoadingDialog(fragmentActivity);
        loadingDialog.show();
    }

    public static void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }


    /**
     * 获得汉语转化为拼音
     *
     * @param allName
     * @return
     */
    public static String getCharacterSpell(String allName) {
        ConvertSpell spell = new ConvertSpell();
        return spell.getCharacterSpell(allName);
    }

    private static FileSaveTools fileSaveTools = FileSaveTools.getInstance();

    public static String getPictureCacheDir() {
        return fileSaveTools.getPictureCacheDir();
    }

    public static String getRecordDir() {
        return fileSaveTools.getRecordDir();
    }

    public static String stringToMD5(String str) {
        return StringFormatTools.stringToMD5(str);
    }

    public static Cursor getImageCursor() {
        return SystemTools.getImageCursor(context);
    }
}
