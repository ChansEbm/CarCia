package com.app.CarCia.model;

import android.view.View;

import com.app.CarCia.R;
import com.app.CarCia.tools.AppTools;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by ChanZeeBm on 2015/12/14.
 */
public class ShareModel implements PlatformActionListener {
    private Wechat.ShareParams wechatFirendsParams = new Wechat.ShareParams();
    private WechatMoments.ShareParams wechatMomentParams = new WechatMoments.ShareParams();
    private QZone.ShareParams qZoneShareParams = new QZone.ShareParams();
    private QQ.ShareParams qqParams = new QQ.ShareParams();
    private TencentWeibo.ShareParams weiboParams = new TencentWeibo.ShareParams();
    private ShortMessage.ShareParams shortMessageParams = new ShortMessage.ShareParams();
    private Platform platform;
    private View snackView;

    public void shareToWechatFriends() {
        wechatFirendsParams.setTitle(ShareParams.title);
        wechatFirendsParams.setImageUrl(ShareParams.imageUrl);
        wechatFirendsParams.setText(ShareParams.text);
        wechatFirendsParams.setUrl(ShareParams.titleUrl);
        wechatFirendsParams.setShareType(Platform.SHARE_WEBPAGE);
        platform = ShareSDK.getPlatform(Wechat.NAME);
        platform.setPlatformActionListener(this);
        platform.share(wechatFirendsParams);
    }

    public void shareToWechatMoment() {
        wechatMomentParams.setTitle(ShareParams.title);
        wechatMomentParams.setImageUrl(ShareParams.imageUrl);
        wechatMomentParams.setText(ShareParams.text);
        wechatMomentParams.setUrl(ShareParams.titleUrl);
        wechatMomentParams.setShareType(Platform.SHARE_WEBPAGE);
        platform = ShareSDK.getPlatform(WechatMoments.NAME);
        platform.setPlatformActionListener(this);
        platform.share(wechatMomentParams);
    }

    public void shareToQZone() {
        qZoneShareParams.setTitle(ShareParams.title);
        qZoneShareParams.setTitleUrl(ShareParams.titleUrl);
        qZoneShareParams.setSiteUrl(ShareParams.siteUrl);
        qZoneShareParams.setSite(ShareParams.site);
        qZoneShareParams.setImageUrl(ShareParams.imageUrl);
        qZoneShareParams.setText(ShareParams.text);
        platform = ShareSDK.getPlatform(QZone.NAME);
        platform.setPlatformActionListener(this);
        platform.share(qZoneShareParams);
    }

    public void shareToQQ() {
        qqParams.setTitle(ShareParams.title);
        qqParams.setTitleUrl(ShareParams.titleUrl);
        qqParams.setSiteUrl(ShareParams.siteUrl);
        qqParams.setSite(ShareParams.site);
        qqParams.setImageUrl(ShareParams.imageUrl);
        qqParams.setText(ShareParams.text);
        platform = ShareSDK.getPlatform(QQ.NAME);
        platform.setPlatformActionListener(this);
        platform.share(qqParams);
    }

    public void shareToQQWeibo() {
        weiboParams.setTitle(ShareParams.title);
        weiboParams.setTitleUrl(ShareParams.titleUrl);
        weiboParams.setSiteUrl(ShareParams.siteUrl);
        weiboParams.setSite(ShareParams.site);
        weiboParams.setImageUrl(ShareParams.imageUrl);
        weiboParams.setText(ShareParams.text);
        platform = ShareSDK.getPlatform(TencentWeibo.NAME);
        platform.setPlatformActionListener(this);
        platform.share(weiboParams);
    }

    public void shareToShortMessage() {
        shortMessageParams.setTitle(ShareParams.title);
        shortMessageParams.setTitleUrl(ShareParams.titleUrl);
        shortMessageParams.setSiteUrl(ShareParams.siteUrl);
        shortMessageParams.setSite(ShareParams.site);
        shortMessageParams.setImageUrl(ShareParams.imageUrl);
        shortMessageParams.setText(ShareParams.text);
        platform = ShareSDK.getPlatform(ShortMessage.NAME);
        platform.setPlatformActionListener(this);
        platform.share(shortMessageParams);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (snackView != null) {
            AppTools.showSnackBar(snackView, "分享成功");
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        if (snackView != null) {
            AppTools.showSnackBar(snackView, "取消分享");
        }
    }

    public void setSnackView(View snackView) {
        this.snackView = snackView;
    }

    public static class ShareParams {
        public static String title = "";
        public static String text = "";
        public static String imageUrl = "";
        public static String titleUrl = "";
        private static String site = "加西亚";
        private static String siteUrl = "http://m.garcia.net.cn/";
    }
}
