package com.app.CarCia.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.app.CarCia.R;
import com.app.CarCia.databinding.DetailZoomLayout;
import com.app.CarCia.facebook.samples.zoomable.ZoomableDraweeView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;

/**
 * Created by Jons on 2015/12/10.
 */
public class DetailZoomDialog extends Dialog {

    private DetailZoomLayout detailZoomLayout;
    private String url = "";
    private DraweeController draweeController;
    private GenericDraweeHierarchy hierarchy;

    public DetailZoomDialog(Context context) {
        super(context, R.style.dialogDefaultStyle);
        detailZoomLayout = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout
                .item_detail_zoom, null, false);
        setContentView(detailZoomLayout.getRoot());

        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public void setUrl(String url) {
        this.url = url;
        draweeController = Fresco.newDraweeControllerBuilder().setUri(Uri.parse(url)).build();
        hierarchy = new GenericDraweeHierarchyBuilder(getContext().getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).build();
        ZoomableDraweeView zoomableDraweeView = detailZoomLayout.zoomableDraweeView;
        zoomableDraweeView.setController(draweeController);
        zoomableDraweeView.setHierarchy(hierarchy);
    }

    @Override
    public void show() {
        if (TextUtils.isEmpty(url) || TextUtils.equals("", url))
            return;
        super.show();

    }

    @Override
    public void dismiss() {
        draweeController.onDetach();
        hierarchy.reset();
        hierarchy = null;
        draweeController = null;
        super.dismiss();

    }
}
