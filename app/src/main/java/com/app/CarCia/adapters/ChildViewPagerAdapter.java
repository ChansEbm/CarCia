package com.app.CarCia.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.CarCia.R;
import com.app.CarCia.dialog.PictureDialog;
import com.app.CarCia.entity.ItemProductBean;
import com.app.CarCia.facebook.samples.zoomable.ZoomableDraweeView;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;

/**
 * Created by ChanZeeBm on 2015/12/9.
 */
public class ChildViewPagerAdapter extends PagerAdapter implements View.OnClickListener {
    private List<ItemProductBean.ListEntity.LinkProductEntity> linkProductEntities = new
            ArrayList<>();
    private Context context;
    private SparseArray<View> views = new SparseArray<>();
    private PictureDialog dialog;

    public ChildViewPagerAdapter(Context context, List<ItemProductBean.ListEntity
            .LinkProductEntity> linkProductEntities) {
        this.linkProductEntities = linkProductEntities;
        this.context = context;
        dialog = new PictureDialog(context);
    }

    @Override
    public int getCount() {
        return linkProductEntities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_child_view_pager,
                null, false);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id
                .simpleDraweeView);
        simpleDraweeView.setImageURI(Uri.parse(this.linkProductEntities.get(position).getProImg()));
        container.addView(view);
        views.put(position, view);
        view.setTag(position);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        views.remove(position);
        container.removeView(view);
    }

    public void setLinkProductEntities(List<ItemProductBean.ListEntity.LinkProductEntity>
                                               linkProductEntities) {
        List<ItemProductBean.ListEntity.LinkProductEntity> l = linkProductEntities;
        if (l != null && !l.isEmpty()) {
            views.clear();
            this.linkProductEntities = new ArrayList<>();
            this.linkProductEntities.addAll(l);
            notifyDataSetChanged();
        }
    }

    public ItemProductBean.ListEntity.LinkProductEntity getChildEntity(int pos) {
        return linkProductEntities.get(pos);
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        dialog.setUrl(linkProductEntities.get(pos).getProImg());
        dialog.show();
    }
}
