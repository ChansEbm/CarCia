package com.app.CarCia.ui.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.CarCia.ItemPhotoWall;
import com.app.CarCia.PhotoWall;
import com.app.CarCia.R;
import com.app.CarCia.adapters.CommonBinderAdapter;
import com.app.CarCia.adapters.CommonBinderHolder;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.entity.PhotoWallBean;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.LogTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PhotoWallActivity extends BaseAty<PhotoWallBean> {

    private PhotoWall photoWall;
    private RecyclerView recyclerView;
    private ArrayList<String> selectorImages = new ArrayList<>();
    private String currentPath = null;
    private boolean isLatest = true;

    public final static String ALBUM_KEY = "album_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoWall = (PhotoWall) viewDataBinding;
    }

    @Override
    protected void initViews() {
        defaultTitleBar(this).setTitle(R.string.gallery);
        recyclerView = photoWall.recyclerView;
        list = getLatestImagePaths(20);
        commonBinderAdapter = new CommonBinderAdapter<PhotoWallBean>(this, R.layout.item_photo_wall,
                list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, PhotoWallBean photoWallBean) {
                ItemPhotoWall itemImage = (ItemPhotoWall) viewDataBinding;
                itemImage.simpleDraweeView.setImageURI(Uri.parse("file://" + photoWallBean
                        .getFileUrl()));
                itemImage.ivTick.setVisibility(photoWallBean.isChoice() ? View.VISIBLE : View
                        .GONE);
            }
        };
    }

    @Override
    protected void initEvents() {
        commonBinderAdapter.setBinderOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(commonBinderAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_photo_wall;
    }

    @Override
    protected void onClick(int id, View view) {
        switch (id) {
            case R.id.button:
                resentResult(RESULT_OK);
                break;
            case R.id.nav:
                if (list.size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("count", list.size());
                    intent.putExtra("first_image", list.get(0).getFileUrl());
                    startActivityForResult(intent.setClass(this, PhotoAlbumActivity.class), 10086);
                }
                break;
        }
    }

    /**
     * get latest images
     *
     * @param maxPhoto the max photo counts
     * @return latest images list
     */
    private List<PhotoWallBean> getLatestImagePaths(int maxPhoto) {
        Cursor cursor = AppTools.getImageCursor();

        List<PhotoWallBean> latestImages = new ArrayList<>();

        if (cursor != null && cursor.moveToLast()) {
            while (true) {
                String path = cursor.getString(0);
                if (!latestImages.contains(path)) {
                    latestImages.add(new PhotoWallBean(path, false));
                }
                if (latestImages.size() > maxPhoto || !cursor.moveToPrevious()) {
                    break;
                }
            }
            cursor.close();
        }
        return latestImages;
    }

    @Override
    public void onBinderItemClick(View view, int pos) {
        int totalCount = 0;
        for (PhotoWallBean bean : list) {
            if (bean.isChoice())
                totalCount++;
            if (totalCount >= 5) {
                AppTools.showSnackBar(photoWall.getRoot(), "一次最多只能添加5张图片");
                return;
            }
        }
        list.get(pos).setIsChoice(!list.get(pos).isChoice());
        PhotoWallBean photoWallBean = list.get(pos);
        boolean isChoice = photoWallBean.isChoice();
        String filePath = photoWallBean.getFileUrl();
        if (isChoice && !selectorImages.contains(filePath)) {
            selectorImages.add(filePath);
        } else if (!isChoice && selectorImages.contains(filePath)) {
            selectorImages.remove(filePath);
        }
        commonBinderAdapter.notifyItemChanged(pos);

        for (PhotoWallBean bean : list) {
            if (bean.isChoice()) {
                photoWall.button.setEnabled(true);
                break;
            } else {
                photoWall.button.setEnabled(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cancel) {
            resentResult(RESULT_CANCELED);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 数据返回
     */
    private void resentResult(int resultCode) {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(ALBUM_KEY, selectorImages);
        intent.putExtra("resultCode", resultCode);//RESULT_CANCELED OR RESULT_OK
        intent.setClass(this, BookingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            resentResult(RESULT_CANCELED);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isImage(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith("png");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int resultCode = intent.getIntExtra("resultCode", -1);
        if (resultCode == RESULT_OK) {
            int code = intent.getIntExtra("code", -1);
            if (code == PhotoAlbumActivity.LATEST_IMAGE) {
                if (!isLatest) {
                    updateView(code, null);
                    isLatest = true;
                }
            } else if (code == PhotoAlbumActivity.OTHER) {
                String folderPath = intent.getStringExtra("folder");
                if (isLatest || (folderPath != null && !folderPath.equals(currentPath))) {
                    currentPath = folderPath;
                    updateView(code, folderPath);
                    isLatest = false;
                }
            }
        }
    }

    private void updateView(int code, String folderPath) {
        list.clear();
        selectorImages.clear();
        if (code == PhotoAlbumActivity.OTHER) {
            list.addAll(getAllImagePathsByFolder(folderPath));
        } else if (code == PhotoAlbumActivity.LATEST_IMAGE) {
            list.addAll(getLatestImagePaths(50));
        }

        commonBinderAdapter.notifyDataSetChanged();

    }

    private List<PhotoWallBean> getAllImagePathsByFolder(String folder) {
        File file = new File(folder);
        String[] allFiles = file.list();
        List<PhotoWallBean> list = new ArrayList<>();
        for (int i = allFiles.length - 1; i >= 0; i--) {
            if (isImage(allFiles[i])) {
                list.add(new PhotoWallBean(folder + File.separator + allFiles[i], false));
            }
        }
        return list;
    }
}
