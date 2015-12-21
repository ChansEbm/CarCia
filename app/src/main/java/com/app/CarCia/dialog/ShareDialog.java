package com.app.CarCia.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.app.CarCia.R;
import com.app.CarCia.databinding.DiaShareLayout;
import com.app.CarCia.eum.SharePlatform;
import com.app.CarCia.impl.OnShareItemClickListener;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by ChanZeeBm on 2015/12/12.
 */
public class ShareDialog implements View.OnClickListener {
    private MaterialDialog materialDialog;
    private DiaShareLayout diaShareLayout;
    private OnShareItemClickListener onShareItemClickListener;

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (onShareItemClickListener != null)
            switch (id) {
                case R.id.tv_qq:
                    onShareItemClickListener.onShareItemClick(SharePlatform.QQ);
                    break;
                case R.id.tv_qzone:
                    onShareItemClickListener.onShareItemClick(SharePlatform.Q_ZONE);
                    break;
                case R.id.tv_short_message:
                    onShareItemClickListener.onShareItemClick(SharePlatform.SHORT_MESSAGE);
                    break;

                case R.id.tv_wechat:
                    onShareItemClickListener.onShareItemClick(SharePlatform.WECHAT);
                    break;
                case R.id.tv_wechat_moment:
                    onShareItemClickListener.onShareItemClick(SharePlatform.WECHAT_MOMENT);
                    break;
            }
        materialDialog.dismiss();
    }

    public void setOnShareItemClickListener(OnShareItemClickListener onShareItemClickListener) {
        this.onShareItemClickListener = onShareItemClickListener;
    }
}
