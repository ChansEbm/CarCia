package com.app.CarCia.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.app.CarCia.DiaImageLayout;
import com.app.CarCia.R;
import com.app.CarCia.facebook.samples.zoomable.ZoomableDraweeView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by ChanZeeBm on 2015/12/10.
 */
public class PictureDialog extends Dialog {

    private DiaImageLayout diaImageLayout;
    private String url = "";

    public PictureDialog(Context context) {
        super(context, R.style.dialogDefaultStyle);
        diaImageLayout = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout
                .dia_image, null, false);
        setContentView(diaImageLayout.getRoot());

        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public void setUrl(String url) {
        this.url = url;
        DraweeController draweeController = Fresco.newDraweeControllerBuilder().setUri(Uri.parse(url)).build();
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getContext().getResources()).setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).build();
        ZoomableDraweeView zoomableDraweeView = diaImageLayout.simpleDraweeView;
        zoomableDraweeView.setController(draweeController);
        zoomableDraweeView.setHierarchy(hierarchy);
    }

    @Override
    public void show() {
        if (TextUtils.isEmpty(url) || TextUtils.equals("", url))
            return;
        super.show();

    }
}
