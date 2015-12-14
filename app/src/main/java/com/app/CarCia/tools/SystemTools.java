package com.app.CarCia.tools;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;

import com.app.CarCia.entity.PhotoAlbumBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanZeeBm on 2015/10/21.
 */
public class SystemTools {

    //拨号
    public static Intent CALL(String phoneNum) {
        return new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + phoneNum));
    }

    public static Intent CAMERA() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }


    //打开相册
    public static Intent PICTURE() {
        return new Intent(Intent.ACTION_GET_CONTENT).setType("image/**");
    }

    //打开系统设置
    public static Intent SETTING() {
        return new Intent(Settings.ACTION_APN_SETTINGS);
    }

    //打开wifi设置
    public static Intent WIFI() {
        return new Intent(Settings.ACTION_WIFI_SETTINGS);
    }

    //获得本机所有图片的光标
    public static Cursor getImageCursor(Context context) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String DATA = MediaStore.Images.Media.DATA;
        List<PhotoAlbumBean> latestImages = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(uri, new String[]{DATA}, MIME_TYPE +
                "=? or "
                + MIME_TYPE + "=? or " + MIME_TYPE + "=?", new String[]{"image/jpg", "image/png",
                "image/jpeg"}, MediaStore.Images.Media.DATE_MODIFIED);
        return cursor;
    }
}
