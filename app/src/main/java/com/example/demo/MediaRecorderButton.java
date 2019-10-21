package com.example.demo;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.io.File;

import github.hotstu.lame4droid.LameMp3Manager;

/**
 * Created by clam314 on 2016/7/21
 */
public class MediaRecorderButton extends androidx.appcompat.widget.AppCompatButton {

    //用于标识按钮不同状态，显示不同的样式
    private static final int NORMAL_STATUS = 1;
    private static final int RECORDING_STATUS = 2;
    private static final int CANCEL_STATUS = 3;


    public MediaRecorderButton(Context context) {
        super(context);
        init();
    }

    public MediaRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MediaRecorderButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.button_recordnormal);
        setText("按下开始录音");
        setStatus(NORMAL_STATUS);
    }

    public String getBasePath() {
        String strPath = null;
        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            strPath = getContext().getFilesDir() + "/" + "lameMp3";
        } else {
            strPath = Environment.getExternalStorageDirectory() + "/" + "lameMp3";
        }
        File dir = new File(strPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return strPath;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    private void startRecord() {
        LameMp3Manager.INSTANCE.startRecorder(getBasePath() + File.separator + "lame.mp3");
    }

    private void endRecorder() {
        LameMp3Manager.INSTANCE.stopRecorder();
    }

    private void cancelRecord() {
        LameMp3Manager.INSTANCE.cancelRecorder();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setStatus(RECORDING_STATUS);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isInCancelArea(x, y)) {
                    setStatus(CANCEL_STATUS);
                } else {
                    setStatus(RECORDING_STATUS);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setStatus(NORMAL_STATUS);
                break;

        }
        return super.onTouchEvent(event);
    }

    private boolean isInCancelArea(int x, int y) {
        return x < 0 || x > getWidth() || y < -50 || y > getHeight() + 50;
    }

    private int mStatus = NORMAL_STATUS;

    String TAG = "sw";

    private void actionStartRecord() {
        Log.d(TAG, "actionStartRecord");
        startRecord();

    }

    private void actionCancelRecord() {
        Log.d(TAG, "actionCancelRecord");
        cancelRecord();

    }

    private void actionStopRecord() {
        Log.d(TAG, "actionStopRecord");
        endRecorder();
    }

    private void setStatus(int status) {
        if (mStatus == NORMAL_STATUS) {
            switch (status) {
                case RECORDING_STATUS:
                    setBackgroundResource(R.drawable.button_recording);
                    setText("松开 结束");
                    mStatus = status;
                    actionStartRecord();
                    break;
            }
        } else if (mStatus == CANCEL_STATUS) {
            switch (status) {
                case NORMAL_STATUS:
                    setBackgroundResource(R.drawable.button_recordnormal);
                    setText("按下开始录音");
                    mStatus = status;
                    actionCancelRecord();
                    break;
                case RECORDING_STATUS:
                    setBackgroundResource(R.drawable.button_recording);
                    setText("松开 结束");
                    mStatus = status;
                    break;
            }
        } else if (mStatus == RECORDING_STATUS) {
            switch (status) {
                case NORMAL_STATUS:
                    setBackgroundResource(R.drawable.button_recordnormal);
                    setText("按下开始录音");
                    mStatus = status;
                    actionStopRecord();
                    break;
                case CANCEL_STATUS:
                    setBackgroundResource(R.drawable.button_recording);
                    setText("松开 取消");
                    mStatus = status;
                    break;
            }
        }
    }


}
