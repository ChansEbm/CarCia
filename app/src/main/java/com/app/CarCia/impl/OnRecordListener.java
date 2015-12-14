package com.app.CarCia.impl;

/**
 * Created by ChanZeeBm on 2015/12/7.
 */
public interface OnRecordListener {
    void finishRecord(String recordPath);

    void onRecording(boolean isRecording);
}
