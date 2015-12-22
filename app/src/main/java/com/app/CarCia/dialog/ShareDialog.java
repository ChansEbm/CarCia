package com.app.CarCia.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.app.CarCia.R;
import com.app.CarCia.databinding.DiaShareLayout;
import com.app.CarCia.model.ShareModel;
import com.app.CarCia.tools.LogTools;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by ChanZeeBm on 2015/12/12.
 */
public class ShareDialog implements View.OnClickListener {
    private MaterialDialog materialDialog;
    private DiaShareLayout diaShareLayout;
    private ShareModel.ShareParams shareParams;
    private ShareModel shareModel = new ShareModel();

    public ShareDialog(Context context) {
        diaShareLayout = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout
                .dia_share, null, false);
        initOnClickListener();
        materialDialog = new MaterialDialog(context).setView(diaShareLayout.getRoot())
                .setCanceledOnTouchOutside(true);
    }

    public void show() {
        if (materialDialog != null)
            materialDialog.show();
    }

    private void initOnClickListener() {
        diaShareLayout.tvQq.setOnClickListener(this);
        diaShareLayout.tvQzone.setOnClickListener(this);
        diaShareLayout.tvShortMessage.setOnClickListener(this);
        diaShareLayout.tvWechat.setOnClickListener(this);
        diaShareLayout.tvWechatMoment.setOnClickListener(this);
    }

    public void setShareParams(ShareModel.ShareParams params) {
        this.shareParams = params;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (shareParams == null) {
            LogTools.e("shareParams == null");
            return;
        }
        shareModel.setShareParams(shareParams);
        switch (id) {
            case R.id.tv_qq:
                shareModel.shareToQQ();
                break;
            case R.id.tv_qzone:
                shareModel.shareToQZone();
                break;
            case R.id.tv_short_message:
                shareModel.shareToShortMessage();
                break;
            case R.id.tv_wechat:
                shareModel.shareToWechatFriends();
                break;
            case R.id.tv_wechat_moment:
                shareModel.shareToWechatMoment();
                break;
        }
        materialDialog.dismiss();
    }


}
