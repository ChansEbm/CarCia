package com.app.CarCia.ui.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.CarCia.BookingLayout;
import com.app.CarCia.R;
import com.app.CarCia.RecorderLayout;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.entity.RecorderBean;
import com.app.CarCia.eum.PhotoPopupOpts;
import com.app.CarCia.impl.OnPhotoOptsSelectListener;
import com.app.CarCia.impl.OnRecordListener;
import com.app.CarCia.model.MarkPictureModel;
import com.app.CarCia.model.NetworkModel;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.CameraTools;
import com.app.CarCia.widget.PopupWindow.TakePhotoPopupWindow;
import com.app.CarCia.widget.RecordButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingActivity extends BaseAty implements OnRecordListener,
        OnPhotoOptsSelectListener {
    private BookingLayout bookingLayout;
    private RecordButton recordButton;
    private NestedScrollView nestedScrollView;
    private boolean isRecording = false;
    private LinearLayout recordLinearLayout;
    private TextView recordTextView;
    private LinearLayout picLinearLayout;
    private List<String> picList = new ArrayList<>();
    private List<String> recordList = new ArrayList<>();
    private List<List<String>> uploadList = new ArrayList<>();
    private TakePhotoPopupWindow takePhotoPopupWindow;
    private EditText titleEdt;
    private EditText contentEdt;

    private String picPath = "";

    private String name = "";
    private String content = "";
    private String cateId = "";
    private String auth = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookingLayout = (BookingLayout) viewDataBinding;
        if (getIntent() == null)
            AppTools.showSnackBar(bookingLayout.getRoot(), "用户标识为空");
        else {
            auth = getIntent().getStringExtra("auth");
        }

    }

    @Override
    protected void initViews() {
        recordButton = bookingLayout.record;
        nestedScrollView = bookingLayout.nestedScrollView;
        recordLinearLayout = bookingLayout.linearLayout;
        recordTextView = bookingLayout.recordText;
        picLinearLayout = bookingLayout.picLinearLayout;
        contentEdt = bookingLayout.edtContent;

        takePhotoPopupWindow = new TakePhotoPopupWindow(this);
    }

    @Override
    protected void initEvents() {
        defaultTitleBar(this).setTitle(R.string.i_need_order_to);
        recordButton.setOnRecordListener(this);
        recordButton.setAppCompatActivity(this);
        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return isRecording;
            }
        });
        takePhotoPopupWindow.setOnPhotoSelectListener(this);

        bookingLayout.simpleDraweeView.setOnClickListener(this);
        bookingLayout.orderCate.setOnClickListener(this);
        bookingLayout.submit.setOnClickListener(this);
        titleEdt = bookingLayout.edtTitle;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_booking;
    }

    @Override
    protected void onClick(int id, View view) {
        switch (id) {
            case R.id.rylt_record:
                RecorderBean recorderBean = (RecorderBean) view.getTag();
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(recorderBean.getFilePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_t1:
                for (int i = 0; i < recordLinearLayout.getChildCount(); i++) {
                    if (view == recordLinearLayout.getChildAt(i).findViewById(R.id.tv_t1)) {
                        RecorderBean bean = (RecorderBean) recordLinearLayout.getChildAt(i)
                                .getTag();
                        File file = new File(bean.getFilePath());
                        file.delete();
                        recordLinearLayout.removeView(recordLinearLayout.getChildAt(i));
                        break;
                    }
                }
                if (recordLinearLayout.getChildCount() == 0) {
                    recordLinearLayout.addView(recordTextView);
                }
                break;
            case R.id.simpleDraweeView:
                takePhotoPopupWindow.showAtLocation(bookingLayout.getRoot(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.order_cate:
                Intent intent = new Intent().setClass(this, OrderCateActivity.class);
                startActivityForResult(intent, 10086);
                break;
            case R.id.nav:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.submit:
                packagingList();
                if (packagingTitleAndContent()) {
                    Map<String, String> maps = new HashMap<>();
                    maps.put("auth", auth);
                    maps.put("catid", cateId);
                    maps.put("name", name);
                    maps.put("content", content);
                    new NetworkModel(this).uploadPicAndRecord(maps,
                            uploadList, "mp3[]", "img[]");
                }
                break;
        }
    }

    private boolean packagingTitleAndContent() {
        name = titleEdt.getText().toString();
        content = contentEdt.getText().toString();
        if (TextUtils.equals(name, "") || name == null) {
            AppTools.showSnackBar(titleEdt, "请输入标题");
            return false;
        }

        if (TextUtils.equals(content, "") || content == null) {
            AppTools.showSnackBar(contentEdt, "请输入摘要");
            return false;
        }
        if (TextUtils.equals(cateId, "") || cateId == null) {
            AppTools.showSnackBar(contentEdt, "请选择分类");
            return false;
        }

        if (TextUtils.equals(auth, "") || auth == null) {
            AppTools.showSnackBar(contentEdt, "无用户标识");
            return false;
        }
        return true;
    }

    private void packagingList() {
        uploadList.clear();
        recordList.clear();
        picList.clear();
        for (int i = 0; i < recordLinearLayout.getChildCount(); i++) {
            if (recordLinearLayout.getChildAt(i) instanceof TextView)
                break;
            RecorderBean recorderBean = (RecorderBean) recordLinearLayout.getChildAt(i).getTag();
            recordList.add(recorderBean.getFilePath());
        }
        for (int i = 0; i < picLinearLayout.getChildCount() - 1; i++) {
            picList.add((String) picLinearLayout.getChildAt(i).findViewById(R.id
                    .simpleDraweeView).getTag());
        }
        uploadList.add(recordList);
        uploadList.add(picList);
    }

    @Override
    public void finishRecord(String recordPath) {
        if (recordPath != null) {
            RecorderLayout recorderLayout = DataBindingUtil.inflate(LayoutInflater.from(this), R
                    .layout.item_recorder, null, false);
            RecorderBean recorderBean = new RecorderBean();
            recorderBean.setFilePath(recordPath);
            if (!(recordLinearLayout.getChildAt(0) instanceof TextView)) {
                recorderBean.setIndex(recordLinearLayout.getChildCount());
                recorderBean.setTitle("录入语音" + (recordLinearLayout.getChildCount() + 1));
                recordLinearLayout.addView(recorderLayout.getRoot());
            } else {
                recordLinearLayout.removeAllViews();
                recorderBean.setIndex(0);
                recorderBean.setTitle("录入语音1");
                recordLinearLayout.addView(recorderLayout.getRoot());
            }
            recorderLayout.setRecorder(recorderBean);
            recorderLayout.getRoot().setOnClickListener(this);
            recorderLayout.getRoot().setTag(recorderBean);
            recorderLayout.tvT1.setOnClickListener(this);
            recorderLayout.tvT1.setTag(recorderBean);
            recordLinearLayout.invalidate();
        }
    }

    @Override
    public void onRecording(boolean isRecording) {
        this.isRecording = isRecording;
    }

    @Override
    public void onOptsSelect(PhotoPopupOpts opts) {
        switch (opts) {
            case ALBUM:
                start(PhotoWallActivity.class);
                break;
            case TAKE_PHOTO:
                picPath = new CameraTools().takePicture(this, TakePhotoPopupWindow.FROM_CAMERA)
                        .getPicturePath();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TakePhotoPopupWindow.FROM_CAMERA) {
            if (resultCode == RESULT_OK) {
                new MarkPictureModel().addSinglePictureInLinearLayout(this, picLinearLayout,
                        picPath);
            }
        } else if (requestCode == 10086) {
            if (resultCode == RESULT_OK) {
                cateId = data.getStringExtra("cateId");
                bookingLayout.tvCate.setText(data.getStringExtra("cate"));
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            ArrayList<String> pics = intent.getStringArrayListExtra(PhotoWallActivity.ALBUM_KEY);
            for (String str :
                    pics) {
                new MarkPictureModel().addSinglePictureInLinearLayout(this, picLinearLayout, str);
            }
        }
    }
}
