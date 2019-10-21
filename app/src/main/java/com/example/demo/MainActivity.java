package com.example.demo;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import github.hotstu.lame4droid.LameMp3Manager;
import github.hotstu.lame4droid.RecorderListener;

public class MainActivity extends AppCompatActivity {
    private MediaRecorderButton btRecorder;
    private ImageView btPlayer;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建播放时用的动画
        AnimationDrawable frameDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_voice_left);
        btPlayer = (ImageView)findViewById(R.id.bt_player);
        btPlayer.setImageDrawable(frameDrawable);
        //动画显示在第一帧
        frameDrawable.selectDrawable(0);
        btPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(TextUtils.isEmpty(filePath)){
                    Toast.makeText(v.getContext(),"文件路径错误",Toast.LENGTH_SHORT).show();
                }else {
                    if(v.getTag() != null){
                        String tag =  (String) v.getTag();
                        //语音正在播放，此时单击停止播放
                        if("showing".equals(tag)){
                            //释放资源
                            MediaPlayUtil.release();
                            ((AnimationDrawable)((ImageView)v).getDrawable()).stop();
                            ((AnimationDrawable)((ImageView)v).getDrawable()).selectDrawable(0);
                            v.setTag("showed");
                            return;
                        }
                    }

                    //开始播放语音，并开始动画
                    ((AnimationDrawable)((ImageView)v).getDrawable()).start();
                    v.setTag("showing");
                    MediaPlayUtil.init(getApplicationContext());
                    MediaPlayUtil.playSound(filePath, new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            ((AnimationDrawable)((ImageView)v).getDrawable()).stop();
                            ((AnimationDrawable)((ImageView)v).getDrawable()).selectDrawable(0);
                            v.setTag("showed");
                            MediaPlayUtil.release();
                        }
                    });
                }
            }
        });


        btRecorder = findViewById(R.id.bt_media_recorder);
        LameMp3Manager.INSTANCE.setRecorderListener(new RecorderListener() {
            @Override
            public void onVolume(int volume) {

            }

            @Override
            public void onFinish(String mp3SavePath) {
                filePath = mp3SavePath;
            }
        });


    }

}
