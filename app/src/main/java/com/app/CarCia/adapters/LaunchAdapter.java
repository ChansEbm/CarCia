package com.app.CarCia.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.CarCia.R;

/**
 * Created by Jons on 2015/12/16.
 */
public class LaunchAdapter extends PagerAdapter {

    private Integer[] imgs = new Integer[]{R.mipmap.avd1, R.mipmap.avd2, R.mipmap.avd3};

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setImageResource(imgs[position]);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
