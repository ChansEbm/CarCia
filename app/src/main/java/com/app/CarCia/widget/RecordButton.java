package com.app.CarCia.widget;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.app.CarCia.R;
import com.app.CarCia.dialog.RecordDialog;
import com.app.CarCia.impl.OnRecordListener;
import com.app.CarCia.tools.AppTools;
import com.czt.mp3recorder.MP3Recorder;

import java.io.File;
import java.io.IOException;

public class RecordButton extends Button {
    private long downMillions = 0;
    private long currentMillions = 0;
    private AppCompatActivity appCompatActivity;
    private File recordFile = null;
    private MP3Recorder recorder = null;
    private RecordDialog recordDialog;
    private OnRecordListener onRecordListener;

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (appCompatActivity == null)
            return false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downMillions = currentMillions = System.currentTimeMillis();
                recordFile = new File(AppTools.getRecordDir(), downMillions + ".mp3");
                recorder = new MP3Recorder(recordFile);
                int x = (int) getX();
                int y = (int) getY();
                int toolBarHeight = appCompatActivity.findViewById(R.id.toolBar).getHeight();
                recordDialog = new RecordDialog(appCompatActivity, y + toolBarHeight, x);
                recordDialog.show();
                try {
                    recorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_UP:
                currentMillions = System.currentTimeMillis();
                if (currentMillions - downMillions < 800) {
                    recordDialog.dismiss();
                    if (recorder.isRecording()) {
                        recorder.stop();
                    }
                    if (recordFile != null || recordFile.exists()) {
                        recordFile.delete();
                    }
                    return false;
                }
                recorder.stop();
                if (onRecordListener != null && recordFile != null) {
                    onRecordListener.finishRecord(recordFile.getPath());
                }
                recordDialog.dismiss();
                break;
        }
        if (onRecordListener != null)
            onRecordListener.onRecording(recorder.isRecording());
        return true;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public void setOnRecordListener(OnRecordListener onRecordListener) {
        this.onRecordListener = onRecordListener;
    }
}
