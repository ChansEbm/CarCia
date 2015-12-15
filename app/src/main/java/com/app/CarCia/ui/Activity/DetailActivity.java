package com.app.CarCia.ui.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.CarCia.DetailLayout;
import com.app.CarCia.R;
import com.app.CarCia.adapters.ChildViewPagerAdapter;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.dialog.DetailZoomDialog;
import com.app.CarCia.dialog.ShareDialog;
import com.app.CarCia.entity.ItemProductBean;
import com.app.CarCia.eum.SharePlatform;
import com.app.CarCia.impl.DragStateChangedListener;
import com.app.CarCia.impl.OnShareItemClickListener;
import com.app.CarCia.impl.ScrollListener;
import com.app.CarCia.model.ShareModel;
import com.app.CarCia.widget.ClashViewPager;
import com.app.CarCia.widget.VHDLayout;
import com.app.CarCia.widget.VHDLayoutChild;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DetailActivity extends BaseAty<ItemProductBean.ListEntity>
        implements ScrollListener, ViewPager
        .OnPageChangeListener, DragStateChangedListener,
        OnShareItemClickListener, Toolbar.OnMenuItemClickListener {

    private VHDLayoutChild vhdChild;
    private VHDLayout vhdParent;
    private DetailLayout detailLayout;
    private ClashViewPager viewPager;
    private ViewPager childViewPager;

    private List<View> simpleDraweeViews = new ArrayList<>();
    private int currentPos = -1;
    private ChildViewPagerAdapter childViewPagerAdapter;
    private ShareModel shareModel = new ShareModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailLayout = (DetailLayout) viewDataBinding;
        if (getIntent() == null)
            throw new NullPointerException("DetailActivity getIntent null");
        else {
            list.clear();
            list.addAll((Collection<? extends ItemProductBean.ListEntity>) getIntent().getExtras
                    ().getSerializable("listEntity"));
            currentPos = getIntent().getIntExtra("pos", -1);
            detailLayout.setDetail(list.get(currentPos).getLinkProduct().get(0));
        }
    }

    @Override
    protected void initViews() {
        String title = getIntent().getStringExtra("title");
        defaultTitleBar(this).setTitle(title).setOnMenuItemClickListener(this);
        vhdChild = detailLayout.vhdChild;
        vhdParent = detailLayout.vhdParent;
        viewPager = detailLayout.viewPager;
        childViewPager = detailLayout.childViewPager;

        for (int i = 0; i < list.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_detail_view_pager, null,
                    false);
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id
                    .simpleDraweeView);
            simpleDraweeView.setImageURI(Uri.parse(list.get(i).getImage()));
            simpleDraweeViews.add(view);
        }

        if (currentPos != -1) {
            childViewPagerAdapter = new ChildViewPagerAdapter(this, list.get(currentPos)
                    .getLinkProduct());
        }
    }

    @Override
    protected void initEvents() {
        detailLayout.topView.setOnClickListener(this);
        vhdChild.setScrollListener(this);
        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.addOnPageChangeListener(this);
        vhdParent.setDragStateChangedListener(this);
        viewPager.setCurrentItem(currentPos, true);
        childViewPager.setAdapter(childViewPagerAdapter);
        childViewPager.setOffscreenPageLimit(8);

        childViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                detailLayout.setDetail(childViewPagerAdapter.getChildEntity(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onClick(int id, View view) {
        switch (id) {
            case R.id.topView:
                vhdParent.trigger();
                break;
            case R.id.nav:
                finish();
                break;
            case R.id.frameLayout:
                int pos = (int) view.getTag();
                DetailZoomDialog detailZoomDialog = new DetailZoomDialog(this);
                detailZoomDialog.setUrl(list.get(pos).getImage());
                detailZoomDialog.show();
                break;
        }
    }

    @Override
    public void scrollOrientation(int orientation) {
        switch (orientation) {
            case VHDLayoutChild.DOWN_DRAG:
                break;
            case VHDLayoutChild.UP_DRAG:
                Intent intent = new Intent().putExtra("detailUrl", detailLayout.getDetail()
                        .getDetailUrl()).setClass(this, DetailWebActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        titleBarTools.setTitle(list.get(position).getTitle());
        List<ItemProductBean.ListEntity.LinkProductEntity> childList = list.get(position)
                .getLinkProduct();
        if (!childList.isEmpty()) {
            childViewPager.setCurrentItem(0);
            childViewPagerAdapter.setLinkProductEntities(childList);
            ItemProductBean.ListEntity.LinkProductEntity productEntity = childList.get(0);
            detailLayout.setDetail(productEntity);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void stateChanged(boolean isOpen) {
        viewPager.setIsEnable(!isOpen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
//            ShareDialog shareDialog = new ShareDialog(this);
//            shareDialog.setOnShareItemClickListener(this);
//            shareDialog.show();
            return true;
        }
        return false;
    }

    @Override
    public void onShareItemClick(SharePlatform sharePlatform) {
        ShareModel.ShareParams.imageUrl = "http://e.hiphotos.baidu" +
                ".com/zhidao/pic/item/fcfaaf51f3deb48fa05af774f31f3a292df5786c.jpg";
        ShareModel.ShareParams.text = "text";
        ShareModel.ShareParams.title = "title";
        ShareModel.ShareParams.titleUrl = "www.baidu.com";
        switch (sharePlatform) {
            case Q_ZONE:
                shareModel.shareToQZone();
                break;
            case QQ:
                shareModel.shareToQQ();
                break;
            case QQ_WEIBO:
                shareModel.shareToQQWeibo();
                break;
            case SHORT_MESSAGE:
                shareModel.shareToShortMessage();
                break;
            case WECHAT:
                shareModel.shareToWechatFriends();
                break;
            case WECHAT_MOMENT:
                shareModel.shareToWechatMoment();
                break;
        }

    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return simpleDraweeViews.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView(simpleDraweeViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            simpleDraweeViews.get(position).setTag(position);
            simpleDraweeViews.get(position).setOnClickListener(DetailActivity.this);
            container.addView(simpleDraweeViews.get(position));
            return simpleDraweeViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
