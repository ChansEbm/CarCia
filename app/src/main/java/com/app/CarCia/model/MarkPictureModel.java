package com.app.CarCia.model;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.app.CarCia.AppKeyMap;
import com.app.CarCia.PictureItem;
import com.app.CarCia.R;
import com.app.CarCia.impl.OnAddPictureDoneListener;
import com.app.CarCia.impl.WidgetPerform;
import com.app.CarCia.tools.BitmapCompressTool;
import com.app.CarCia.tools.FileSaveTools;
import com.app.CarCia.tools.ObjectAnimatorTools;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 11/18/2015.
 * 添加删除照片动作实现类
 */
public class MarkPictureModel implements View.OnClickListener {
    private LinearLayout linearLayout;
    private OnAddPictureDoneListener onAddPictureDoneListener;
    private ObjectAnimatorTools tools = new ObjectAnimatorTools();

    public void addSinglePictureInLinearLayout(Context context, LinearLayout linearLayout,
                                               @NonNull String
                                                       filePath) {
        if (TextUtils.isEmpty(filePath)) {//如果路径为默认值,则返回不执行任何操作
            return;
        }
        String newPath = getNewFilePath(filePath);//检查图片是否在APP指定图片缓存内
        String displayPath = newPath == null ? filePath : newPath;
        this.linearLayout = linearLayout;//初始化linearLayout
        PictureItem pictureItem = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout
                .item_pics, null, false);//初始化picItem
        SimpleDraweeView simpleDraweeView = pictureItem.simpleDraweeView;
        pictureItem.cancel.setOnClickListener(this);

        BitmapCompressTool.getRadioBitmap(displayPath, 500, 500);
        //压缩图片宽高为300*300
        simpleDraweeView.setTag(filePath);//设置标识为图片路径
        simpleDraweeView.setImageURI(Uri.parse("file://" + displayPath));//加载图片并显示

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);

        int childCount = this.linearLayout.getChildCount();//获得要添加图片的布局里面带有多少个控件
        if (childCount > 0) {//如果有控件,则证明该布局有一个+的引导,我们要把图片加到它前面
            this.linearLayout.addView(pictureItem.getRoot(), childCount - 1, params);
        } else {//否则直接加到布局里头
            this.linearLayout.addView(pictureItem.getRoot(), params);
        }

        if (onAddPictureDoneListener != null) {//添加完成后,回调,在回调处作相应处理
            onAddPictureDoneListener.onAddPictureDone(pictureItem.getRoot(), this.linearLayout
                    .getChildCount());
        }
    }

    public void setOnAddPictureDoneListener(OnAddPictureDoneListener onAddPictureDoneListener) {
        this.onAddPictureDoneListener = onAddPictureDoneListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel) {//删除图片
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                if (linearLayout.getChildAt(i).findViewById(R.id.cancel) == v) {//如果符合点击项
                    View parentView = linearLayout.getChildAt(i);//获得要删除的对象
                    removePicture(parentView);//进行动画删除
                    break;
                }
            }
        }
    }

    /**
     * 动画删除项
     *
     * @param parentView 要删除并且进行动画的项
     */
    private void removePicture(final View parentView) {
        if (tools.isAnimationRunning()) {//如果动画正在进行中,,则直接返回
            return;
        }
        //属性动画,对宽做动画,从原本宽度到0
        tools.performAnimate(parentView, WidgetPerform.WIDTH, AppKeyMap.DEFAULT_DURATION, parentView
                .getWidth(), 0);
        //同时做渐变动画 200ms内从1f变成0.3f
        ViewPropertyAnimator.animate(parentView).alpha(0.3f).setDuration(200).start();
        //延迟x ms后执行删除成功的回调
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayout.removeView(parentView);
                if (onAddPictureDoneListener != null)
                    onAddPictureDoneListener.onDeletePictureDone(linearLayout
                            .getChildCount());
            }
        }, AppKeyMap.DEFAULT_DURATION);
    }

    /**
     * 如果路径不是本App的指定图片缓存 则把图片复制到图片缓存
     *
     * @param oldPath 当前图片所在路径
     * @return 返回App指定图片缓存路径
     */
    private String getNewFilePath(String oldPath) {

        File file = new File(oldPath);
        String parentPath = file.getParent();
        String newPath = null;
        if (!TextUtils.equals(parentPath, FileSaveTools.getInstance().getPictureCacheDir())) {
            String allFilePath = file.getAbsolutePath();
            String fileName = (String) allFilePath.subSequence(allFilePath.lastIndexOf(File
                    .separator) + 1, allFilePath.length());

            Bitmap bitmap = BitmapFactory.decodeFile(allFilePath);
            File newFile = new File(FileSaveTools.getInstance().getPictureCacheDir(), fileName);
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(newFile);
                if (fos != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null)
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            newPath = newFile.getAbsolutePath();
        }
        return newPath;
    }
}
