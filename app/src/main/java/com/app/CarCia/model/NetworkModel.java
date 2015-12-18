package com.app.CarCia.model;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.app.CarCia.R;
import com.app.CarCia.adapters.AdvertisementAdapter;
import com.app.CarCia.adapters.CommonBinderAdapter;
import com.app.CarCia.adapters.JsonObjectListener;
import com.app.CarCia.entity.AdvertisementBean;
import com.app.CarCia.entity.HomeBean;
import com.app.CarCia.entity.ItemProductBean;
import com.app.CarCia.entity.OrderCateBean;
import com.app.CarCia.entity.UploadBean;
import com.app.CarCia.impl.OkHttpResponseListener;
import com.app.CarCia.impl.OnPagerClickListener;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.OkHttpBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChanZeeBm on 2015/12/4.
 */
public class NetworkModel {

    private FragmentActivity fragmentActivity;
    private AdvertisementAdapter topAdvertisementAdapter;
    private AdvertisementAdapter bottomAdvertisementAdapter;

    public NetworkModel(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    /**
     * 头部广告
     *
     * @param viewPager 显示头部广告的控件
     */
    public void topAdvertisement(final ViewPager viewPager, final OnPagerClickListener
            onPagerClickListener) {
        Map<String, String> params = new HashMap<>();
        params.put("id", "11");
        new OkHttpBuilder.GET().url("Ad").entityClass(AdvertisementBean.class).params(params)
                .enqueue
                        (fragmentActivity, new JsonObjectListener<AdvertisementBean>() {
                            @Override
                            public void onJsonObjectResponse(AdvertisementBean advertisementBean) {
                                topAdvertisementAdapter = new AdvertisementAdapter(fragmentActivity,
                                        advertisementBean
                                                .getList());
                                viewPager.setAdapter(topAdvertisementAdapter);
                                topAdvertisementAdapter.setOnPagerClickListener
                                        (onPagerClickListener);
                                dismissLoadingDialog();
                            }

                        });
    }

    /**
     * 底部广告
     *
     * @param viewPager 显示底部广告的控件
     */
    public void bottomAdvertisement(final ViewPager viewPager, final OnPagerClickListener
            onPagerClickListener) {
        Map<String, String> params = new HashMap<>();
        params.put("id", "12");
        new OkHttpBuilder.GET().url("Ad").entityClass(AdvertisementBean.class).params(params)
                .enqueue
                        (fragmentActivity, new JsonObjectListener<AdvertisementBean>() {
                            @Override
                            public void onJsonObjectResponse(AdvertisementBean advertisementBean) {
                                bottomAdvertisementAdapter = new AdvertisementAdapter
                                        (fragmentActivity,
                                                advertisementBean
                                                        .getList());
                                viewPager.setAdapter(bottomAdvertisementAdapter);
                                bottomAdvertisementAdapter.setOnPagerClickListener
                                        (onPagerClickListener);
                                dismissLoadingDialog();
                            }
                        });
    }

    /**
     * 获取风格 空间 功能列表
     *
     * @param isNeedSecond 是否需要双层布局
     * @param url          地址
     * @param list         获取完毕后需要添加数据的list
     */
    public void content(boolean isNeedSecond, String url, final List<HomeBean.ListEntity> list,
                        final CommonBinderAdapter adapter) {
        list.clear();
        if (isNeedSecond)
            list.add(new HomeBean.ListEntity(true));

        new OkHttpBuilder.GET().url(url).entityClass(HomeBean.class).enqueue
                (fragmentActivity, new JsonObjectListener<HomeBean>() {

                    @Override
                    public void onJsonObjectResponse(HomeBean homeBean) {
                        list.addAll(homeBean.getList());
                        if (adapter != null)
                            adapter.notifyDataSetChanged();
                        dismissLoadingDialog();
                    }
                });
    }


    /**
     * 添加产品数据
     *
     * @param params  需要提交的参数
     * @param adapter 适配器
     */
    public void product(final View snackView, Map<String, String> params, final CommonBinderAdapter
            adapter) {
        new OkHttpBuilder.GET().url("Case").entityClass(ItemProductBean.class).params(params)
                .enqueue(fragmentActivity, new JsonObjectListener<ItemProductBean>() {
                    @Override
                    public void onJsonObjectResponse(ItemProductBean itemProductBean) {
                        if (itemProductBean.getList().isEmpty()) {
                            showMarginSnackBar(snackView);
                        }
                        if (adapter != null) {
                            adapter.getList().clear();
                            adapter.getList().addAll(itemProductBean.getList());
                            adapter.notifyDataSetChanged();
                            dismissLoadingDialog();
                        }
                    }
                });
    }


    public void orderCate(final CommonBinderAdapter adapter) {
        new OkHttpBuilder.GET().entityClass(OrderCateBean.class).url1("getOrderCate").enqueue
                (fragmentActivity, new JsonObjectListener<OrderCateBean>() {
                    @Override
                    public void onJsonObjectResponse(OrderCateBean orderCateBean) {
                        if (adapter != null) {
                            adapter.getList().clear();
                            adapter.getList().addAll(orderCateBean.getList());
                            adapter.notifyDataSetChanged();
                            dismissLoadingDialog();
                        } else {
                        }
                    }
                });
    }

    public void uploadPicAndRecord(Map<String, String> params,
                                   List<List<String>> files,
                                   String... fileKey) {
        new OkHttpBuilder.POST().entityClass(UploadBean.class).params(params, files, fileKey).url1
                ("saveOrder")
                .enqueue(fragmentActivity, new OkHttpResponseListener<UploadBean>() {

                    @Override
                    public void onJsonObjectResponse(UploadBean uploadBean) {
                        if (uploadBean.getStatus() == 1) {
                            Toast.makeText(fragmentActivity, "上传成功", Toast.LENGTH_LONG).show();
                            AppTools.removeSingleActivity(fragmentActivity);
                        } else {
                            Toast.makeText(fragmentActivity, "上传失败,请检查", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onJsonArrayResponse(List<UploadBean> t) {

                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(fragmentActivity, "由于网络原因,上传失败!", Toast
                                .LENGTH_LONG).show();
                    }
                });
    }

    public void search(final EditText editText, Map<String, String> params, final
    CommonBinderAdapter
            adapter) {
        new OkHttpBuilder.GET().params(params).entityClass(ItemProductBean.class).url
                ("Case").enqueue(fragmentActivity, new JsonObjectListener<ItemProductBean>() {

            @Override
            public void onJsonObjectResponse(ItemProductBean listEntity) {
                if (listEntity.getList().isEmpty()) {
                    AppTools.showSnackBar(editText, "暂无数据");
                    dismissLoadingDialog();
                    return;
                }
                if (adapter != null) {
                    adapter.getList().clear();
                    adapter.getList().addAll(listEntity.getList());
                    adapter.notifyDataSetChanged();
                }
                dismissLoadingDialog();
            }
        });
    }

    private void showMarginSnackBar(View view) {
        Snackbar snackbar = Snackbar.make(view, "SORRY，没有找到您要的产品！", Snackbar.LENGTH_SHORT);
        View sView = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) sView
                .getLayoutParams();
        int bottomMargin = fragmentActivity.findViewById(R.id.radioGroup).getHeight();
        params.bottomMargin = bottomMargin;
        sView.setBackgroundColor(fragmentActivity.getResources().getColor(R.color
                .color_snack_bar_background));
        sView.setLayoutParams(params);
        snackbar.show();
    }

    private void dismissLoadingDialog() {
        AppTools.dismissLoadingDialog();
    }
}
