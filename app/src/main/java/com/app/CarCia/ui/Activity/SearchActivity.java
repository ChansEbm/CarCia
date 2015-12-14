package com.app.CarCia.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.app.CarCia.BR;
import com.app.CarCia.ItemPicWordLayout;
import com.app.CarCia.R;
import com.app.CarCia.SearchLayout;
import com.app.CarCia.adapters.CommonBinderAdapter;
import com.app.CarCia.adapters.CommonBinderHolder;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.entity.ItemProductBean;
import com.app.CarCia.model.NetworkModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SearchActivity extends BaseAty<ItemProductBean.ListEntity> implements TextView
        .OnEditorActionListener {
    private SearchLayout search;
    private RecyclerView recyclerView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        search = (SearchLayout) viewDataBinding;
    }

    @Override
    protected void initViews() {
        defaultTitleBar(this).setTitle(R.string.search);
        recyclerView = search.recyclerView;
        editText = search.editText;
        commonBinderAdapter = new CommonBinderAdapter<ItemProductBean.ListEntity>(this,
                R.layout.item_pic_word, list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, ItemProductBean.ListEntity listEntity) {
                ItemPicWordLayout itemProduct = (ItemPicWordLayout) viewDataBinding;
                itemProduct.simpleDraweeView.setImageURI(Uri.parse(listEntity.getThumb()));
                viewDataBinding.setVariable(BR.item, listEntity);
            }
        };

    }

    @Override
    protected void initEvents() {
        recyclerView.setAdapter(commonBinderAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        editText.setOnEditorActionListener(this);
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        commonBinderAdapter.setBinderOnItemClickListener(this);
        new NetworkModel(this).search(editText, null, commonBinderAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void onClick(int id, View view) {
        if (id == R.id.nav) {
            finish();
        }
    }

    @Override
    public void onBinderItemClick(View view, int pos) {
        ArrayList<ItemProductBean.ListEntity> listEntities = (ArrayList<ItemProductBean
                .ListEntity>) list;
        Intent intent = new Intent().putExtra("listEntity", listEntities).putExtra("pos", pos)
                .setClass(this, DetailActivity.class).putExtra("title", listEntities.get(pos)
                        .getTitle());
        startActivity(intent);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String keyWord = editText.getText().toString();
            if (!TextUtils.isEmpty(keyWord)) {
                Map<String, String> params = new HashMap<>();
                params.put("keywords", keyWord);
                new NetworkModel(this).search(editText, params, commonBinderAdapter);
            }
            InputMethodManager manager = (InputMethodManager) getSystemService(Context
                    .INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            return true;
        }
        return false;
    }
}
