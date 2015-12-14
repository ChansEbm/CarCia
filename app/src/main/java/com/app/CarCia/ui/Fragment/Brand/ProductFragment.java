package com.app.CarCia.ui.Fragment.Brand;


import android.app.Fragment;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.app.CarCia.AppKeyMap;
import com.app.CarCia.ItemPicWordLayout;
import com.app.CarCia.ProductLayout;
import com.app.CarCia.R;
import com.app.CarCia.adapters.CommonBinderAdapter;
import com.app.CarCia.adapters.CommonBinderHolder;
import com.app.CarCia.base.BaseFgm;
import com.app.CarCia.broadcast.UpdateUIBroadcast;
import com.app.CarCia.entity.HomeBean;
import com.app.CarCia.entity.ItemProductBean;
import com.app.CarCia.impl.FilterPerform;
import com.app.CarCia.impl.OnFilterSelectedListener;
import com.app.CarCia.impl.UpdateUIListener;
import com.app.CarCia.model.NetworkModel;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.ui.Activity.DetailActivity;
import com.app.CarCia.ui.Activity.SearchActivity;
import com.app.CarCia.widget.PopupWindow.FilterPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseFgm<ItemProductBean.ListEntity> implements
        OnFilterSelectedListener, UpdateUIListener {

    private ProductLayout productLayout;
    private LinearLayout topLayout;
    private FrameLayout[] topLayouts = new FrameLayout[3];
    private RecyclerView recyclerView;

    private List<HomeBean.ListEntity> styleList = new ArrayList<>();
    private List<HomeBean.ListEntity> spaceList = new ArrayList<>();
    private List<HomeBean.ListEntity> categoryList = new ArrayList<>();

    private FilterPopupWindow filterPopupWindow;
    private View translateView;

    private String styleId = "0";
    private String spaceId = "0";
    private String catId = "2";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateUIBroadcast broadcast = new UpdateUIBroadcast();
        broadcast.setListener(this);
        AppTools.registerBroadcast(broadcast, AppKeyMap.ACTION_JUMP);
    }

    @Override
    protected void initViews() {

        productLayout = (ProductLayout) viewDataBinding;
        recyclerView = productLayout.recyclerView;
        topLayout = productLayout.linearLayout;
        translateView = productLayout.transparentView;
        productLayout.styleLayout.setOnClickListener(this);
        productLayout.functionLayout.setOnClickListener(this);
        productLayout.classifyLayout.setOnClickListener(this);
        topLayouts[0] = productLayout.styleLayout;
        topLayouts[1] = productLayout.functionLayout;
        topLayouts[2] = productLayout.classifyLayout;

        filterPopupWindow = new FilterPopupWindow(getActivity());
        new NetworkModel(getActivity()).content(false, "Style", styleList, null);
        new NetworkModel(getActivity()).content(false, "Space", spaceList, null);
        new NetworkModel(getActivity()).content(false, "Category", categoryList, null);

        commonBinderAdapter = new CommonBinderAdapter<ItemProductBean.ListEntity>(getActivity(),
                R.layout.item_pic_word, list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, ItemProductBean.ListEntity listEntity) {
                ItemPicWordLayout itemProductLayout = (ItemPicWordLayout) viewDataBinding;
                itemProductLayout.simpleDraweeView.setImageURI(Uri.parse(listEntity.getThumb()));
                itemProductLayout.setItem(listEntity);
            }
        };


    }

    @Override
    protected void initEvents() {
        recyclerView.setAdapter(commonBinderAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        filterPopupWindow.setOnFilterSelectedListener(this);
        productLayout.search.setOnClickListener(this);

        commonBinderAdapter.setBinderOnItemClickListener(this);

        filterPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleTranslateView();
            }
        });
        if (getArguments() != null && !getArguments().isEmpty()) {
            HomeBean.ListEntity listEntity = (HomeBean.ListEntity) getArguments().getSerializable
                    ("listEntity");
            progress(listEntity);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("pageSize", "30");
            new NetworkModel(getActivity()).product(productLayout.getRoot(),params, commonBinderAdapter);
        }
    }

    @Override
    protected void onClick(int id, View view) {
        switch (id) {
            case R.id.style_layout:
                resetAllRadioButton();
                setRadioButtonChecked(0);
                filterPopupWindow.setShowData(FilterPerform.STYLE, styleList);
                filterPopupWindow.showAsDropDown(topLayout);
                toggleTranslateView();
                break;
            case R.id.function_layout:
                resetAllRadioButton();
                setRadioButtonChecked(1);
                filterPopupWindow.setShowData(FilterPerform.SPACE,
                        spaceList);
                filterPopupWindow.showAsDropDown(topLayout);
                toggleTranslateView();
                break;
            case R.id.classify_layout:
                resetAllRadioButton();
                setRadioButtonChecked(2);
                filterPopupWindow.setShowData(FilterPerform.CATEGORY,
                        categoryList);
                filterPopupWindow.showAsDropDown(topLayout);
                toggleTranslateView();
                break;
            case R.id.search:
                start(SearchActivity.class);
                break;
        }
    }

    private void resetAllRadioButton() {
        for (FrameLayout frameLayout : topLayouts) {
            RadioButton radioButton = (RadioButton) frameLayout.getChildAt(0);
            radioButton.setChecked(false);
        }
    }

    private void setRadioButtonChecked(int index) {
        FrameLayout frameLayout = topLayouts[index];
        RadioButton radioButton = (RadioButton) frameLayout.getChildAt(0);
        radioButton.setChecked(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_product;
    }

    @Override
    public boolean isHasOptionsMenu() {
        return false;
    }

    @Override
    public int menuRes() {
        return 0;
    }

    @Override
    public void onFilterSelected(HomeBean.ListEntity listEntity, FilterPerform perform) {
        Map<String, String> params = new HashMap<>();
        params.clear();
        if (listEntity != null) {
            progress(listEntity);
        } else if (perform != null) {
            switch (perform) {
                case STYLE:
                    ((RadioButton) topLayouts[0].getChildAt(0)).setText(R.string.style);
                    styleId = "0";
                    break;
                case SPACE:
                    ((RadioButton) topLayouts[1].getChildAt(0)).setText(R.string.function);
                    spaceId = "0";
                    break;
                case CATEGORY:
                    ((RadioButton) topLayouts[2].getChildAt(0)).setText(R.string.classify);
                    catId = "2";
                    break;
            }
            params.put("styleid", styleId);
            params.put("spaceid", spaceId);
            params.put("cateid", catId);
            new NetworkModel(getActivity()).product(productLayout.getRoot(),params, commonBinderAdapter);
        }
    }

    @Override
    public void onBinderItemClick(View view, int pos) {
        ArrayList<ItemProductBean.ListEntity> listEntities = (ArrayList<ItemProductBean
                .ListEntity>) list;
        Intent intent = new Intent().putExtra("listEntity", listEntities).putExtra("pos", pos)
                .setClass
                        (getActivity(), DetailActivity.class).putExtra("title", listEntities.get
                        (pos).getTitle());
        startActivity(intent);
    }

    @Override
    public void uiUpData(Intent intent) {
        HomeBean.ListEntity listEntity = (HomeBean.ListEntity) intent.getSerializableExtra
                (AppKeyMap.ACTION_KEY);
        progress(listEntity);
    }

    private void progress(HomeBean.ListEntity listEntity) {
        if (listEntity == null)
            return;
        Map<String, String> params = new HashMap<>();
        params.clear();
        resetAllRadioButton();
        if (listEntity.getStyleID() != null) {
            this.styleId = listEntity.getStyleID();
            RadioButton radioButton = ((RadioButton) topLayouts[0].getChildAt(0));
            radioButton.setText(listEntity.getTitle());
            radioButton.setChecked(true);
        } else if (listEntity.getCatID() != null) {
            this.catId = listEntity.getCatID();
            RadioButton radioButton = ((RadioButton) topLayouts[2].getChildAt(0));
            radioButton.setText(listEntity.getTitle());
            radioButton.setChecked(true);
        } else if (listEntity.getSpaceID() != null) {
            this.spaceId = listEntity.getSpaceID();
            RadioButton radioButton = ((RadioButton) topLayouts[1].getChildAt(0));
            radioButton.setText(listEntity.getTitle());
            radioButton.setChecked(true);
        }
        params.put("styleid", styleId);
        params.put("spaceid", spaceId);
        params.put("catid", catId);
        new NetworkModel(getActivity()).product(productLayout.getRoot(),params, commonBinderAdapter);
    }

    private void toggleTranslateView() {
        translateView.setVisibility(translateView.getVisibility() == View.GONE ? View.VISIBLE :
                View.GONE);
    }
}
