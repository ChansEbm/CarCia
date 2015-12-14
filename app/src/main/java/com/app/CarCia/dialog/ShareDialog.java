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
        materialDialog = new MaterialDialog(context).setContentView(diaShareLayout.getRoot())
                .setTitle(R.string.ssdk_oks_share_to)
                .setCanceledOnTouchOutside(true);
    }

    public void show() {
        if (materialDialog != null)
            materialDialog.show();
    }

    private void initOnClickListener() {
        diaShareLayout.ibQq.setOnClickListener(this);
        diaShareLayout.ibQzone.setOnClickListener(this);
        diaShareLayout.ibShortMessage.setOnClickListener(this);
        diaShareLayout.ibTencentWeibo.setOnClickListener(this);
        diaShareLayout.ibWechat.setOnClickListener(this);
        diaShareLayout.ibWechatMoment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (onShareItemClickListener != null)
            switch (id) {
                case R.id.ib_qq:
                    onShareItemClickListener.onShareItemClick(SharePlatform.QQ);
                    break;
                case R.id.ib_qzone:
                    onShareItemClickListener.onShareItemClick(SharePlatform.Q_ZONE);
                    break;
                case R.id.ib_short_message:
                    onShareItemClickListener.onShareItemClick(SharePlatform.SHORT_MESSAGE);
                    break;
                case R.id.ib_tencent_weibo:
                    onShareItemClickListener.onShareItemClick(SharePlatform.QQ_WEIBO);
                    break;
                case R.id.ib_wechat:
                    onShareItemClickListener.onShareItemClick(SharePlatform.WECHAT);
                    break;
                case R.id.ib_wechat_moment:
                    onShareItemClickListener.onShareItemClick(SharePlatform.WECHAT_MOMENT);
                    break;
            }
        materialDialog.dismiss();
    }

    public void setOnShareItemClickListener(OnShareItemClickListener onShareItemClickListener) {
        this.onShareItemClickListener = onShareItemClickListener;
    }
}
