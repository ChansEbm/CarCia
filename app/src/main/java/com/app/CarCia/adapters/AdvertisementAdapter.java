package com.app.CarCia.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.CarCia.R;
import com.app.CarCia.entity.AdvertisementBean;
import com.app.CarCia.impl.OnPagerClickListener;
import com.app.CarCia.tools.LogTools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanZeeBm on 2015/12/4.
 */
public class AdvertisementAdapter extends PagerAdapter implements View.OnClickListener {
    private List<View> views = new ArrayList<>();
    private List<AdvertisementBean.ListEntity> advertisementBeans = new ArrayList<>();
    private OnPagerClickListener onPagerClickListener;
    private Context context;

    public AdvertisementAdapter(Context context, List<AdvertisementBean.ListEntity>
            advertisementBeans) {
        this.context = context;
        for (int i = 0; i < advertisementBeans.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, null, false);
            views.add(view);
        }
        this.advertisementBeans = advertisementBeans;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id
                .simpleDraweeView);
        simpleDraweeView.setImageURI(Uri.parse(advertisementBeans.get(position).getImage()));
        container.addView(views.get(position));
        view.setTag(R.id.tag_view, container);
        view.setTag(R.id.tag_pos, position);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag(R.id.tag_pos);
        ViewGroup viewGroup = (ViewGroup) v.getTag(R.id.tag_view);
        if (onPagerClickListener != null) {
            onPagerClickListener.onPagerClick(viewGroup, pos);
        }
    }


    public void setOnPagerClickListener(OnPagerClickListener onPagerClickListener) {
        this.onPagerClickListener = onPagerClickListener;
    }
}
