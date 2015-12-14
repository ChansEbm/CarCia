package com.app.CarCia.ui.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.app.CarCia.ItemPhotoAlbum;
import com.app.CarCia.PhotoAlbumLayout;
import com.app.CarCia.R;
import com.app.CarCia.adapters.CommonBinderAdapter;
import com.app.CarCia.adapters.CommonBinderHolder;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.entity.PhotoAlbumBean;
import com.app.CarCia.tools.AppTools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 相薄
 */
public class PhotoAlbumActivity extends BaseAty<PhotoAlbumBean> {
    private PhotoAlbumLayout photoAlbumLayout;
    private RecyclerView recyclerView;
    private Intent intent;
    public final static int LATEST_IMAGE = 0x001;
    public final static int OTHER = 0x002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoAlbumLayout = (PhotoAlbumLayout) viewDataBinding;
        intent = getIntent();
        if (intent == null || !intent.hasExtra("first_image"))
            return;
        list.add(new PhotoAlbumBean("最近照片", "", intent.getStringExtra("first_image"), intent
                .getIntExtra("count", -1)));
        list.addAll(getImagePathsByContentProvider());
    }

    @Override
    protected void initViews() {
        defaultTitleBar(this).setTitle(R.string.album);
        recyclerView = photoAlbumLayout.recyclerView;
        commonBinderAdapter = new CommonBinderAdapter<PhotoAlbumBean>(this, R.layout
                .item_photo_album, list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, PhotoAlbumBean photoAlbumBean) {
                ((ItemPhotoAlbum) viewDataBinding).simpleDraweeView.setImageURI(Uri.parse
                        ("file://" + photoAlbumBean.getFirstUri()));
                ((ItemPhotoAlbum) viewDataBinding).setAlbum(photoAlbumBean);
            }
        };
    }

    @Override
    protected void initEvents() {
        commonBinderAdapter.setBinderOnItemClickListener(this);

        recyclerView.setAdapter(commonBinderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(AppTools.defaultHorizontalDecoration());
    }

    private List<PhotoAlbumBean> getImagePathsByContentProvider() {
        Cursor cursor = AppTools.getImageCursor();

        List<PhotoAlbumBean> imagePaths = new ArrayList<>();
        if (cursor != null && cursor.moveToLast()) {
            HashSet<String> cachePath = new HashSet<>();
            while (true) {
                String path = cursor.getString(0);
                File parentFile = new File(path).getParentFile();
                String parentPath = parentFile.getAbsolutePath();
                if (!cachePath.contains(parentPath)) {
                    imagePaths.add(new PhotoAlbumBean(getTitle(parentPath, getImagesCount
                            (parentFile)), parentPath, getFirstLatestImage(parentFile),
                            getImagesCount(parentFile)));
                    cachePath.add(parentPath);
                }
                if (!cursor.moveToPrevious()) {
                    break;
                }
            }
            cursor.close();
        }
        return imagePaths;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_photo_album;
    }

    @Override
    protected void onClick(int id, View view) {
        if (id == R.id.nav) {
            broughtPhotoWallFront();
        }
    }

    private int getImagesCount(File file) {
        File[] files = file.listFiles();
        int count = 0;
        for (File f : files) {
            if (isImage(f.getName())) {
                count++;
            }
        }
        return count;
    }

    private String getFirstLatestImage(File folder) {
        File[] files = folder.listFiles();
        for (int i = files.length - 1; i >= 0; i--) {
            if (isImage(files[i].getName()))
                return files[i].getAbsolutePath();
        }
        return null;
    }

    private boolean isImage(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith("png");
    }

    private String getTitle(String parentPath, int count) {
        int lastSeparator = parentPath.lastIndexOf(File.separator);
        return parentPath.substring(lastSeparator + 1) + "(" + count + ")";
    }

    @Override
    public void onBinderItemClick(View view, int pos) {
        Intent intent = new Intent();
        intent.setClass(this, PhotoWallActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("resultCode", RESULT_OK);
        if (pos == 0) {
            intent.putExtra("code", LATEST_IMAGE);
        } else {
            String parentPath = list.get(pos).getFilePath();
            intent.putExtra("code", OTHER);
            intent.putExtra("folder", parentPath);
        }
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            broughtPhotoWallFront();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void broughtPhotoWallFront() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setClass(this, PhotoWallActivity.class);
        intent.putExtra("resultCode", RESULT_CANCELED);
        startActivity(intent);
    }
}
