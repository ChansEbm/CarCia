package com.app.CarCia.widget.PopupWindow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.app.CarCia.R;
import com.app.CarCia.databinding.PopupTakePhotoBinding;
import com.app.CarCia.eum.PhotoPopupOpts;
import com.app.CarCia.impl.OnPhotoOptsSelectListener;


/**
 * Created by ChanZeeBm on 2015/11/18.
 */
public class TakePhotoPopupWindow extends BasePopupWindow {
    private PopupTakePhotoBinding popupTakePhotoBinding;
    private OnPhotoOptsSelectListener listener;

    public final static int FROM_CAMERA = 0x001;
    public final static int FROM_ALBUM = 0x002;

    public TakePhotoPopupWindow(@NonNull Context context) {
        super(context);
        popupTakePhotoBinding = (PopupTakePhotoBinding) viewDataBinding;
        popupTakePhotoBinding.btnCancel.setOnClickListener(this);
        popupTakePhotoBinding.btnSelectFromAlbum.setOnClickListener(this);
        popupTakePhotoBinding.btnTakePhoto.setOnClickListener(this);
    }

    @Override
    public int getPopupLayout() {
        return R.layout.popup_take_photo;
    }

    public void setOnPhotoSelectListener(OnPhotoOptsSelectListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            switch (v.getId()) {
                case R.id.btn_select_from_album:
                    listener.onOptsSelect(PhotoPopupOpts.ALBUM);//选择相册
                    break;
                case R.id.btn_take_photo:
                    listener.onOptsSelect(PhotoPopupOpts.TAKE_PHOTO);//选择拍照
                    break;
            }
        }
        dismiss();
    }

}
