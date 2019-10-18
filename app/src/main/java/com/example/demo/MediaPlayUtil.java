package com.example.demo;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by clam314 on 2016/7/20
 *
 * 对系统的MediaPlay进行简单的封装，使其只需要三步就可以播放音频文件
 */
public class MediaPlayUtil {

    private static MediaPlayer mediaPlayer;

    //播放前需要初始化
    public static void init(Context context){
        if(mediaPlayer == null){
            synchronized (MediaPlayUtil.class){
                mediaPlayer = getMediaPlayer(context);
            }
        }
    }

    //播放音频
    public static void playSound(String path, MediaPlayer.OnCompletionListener listener) {
       if(mediaPlayer == null){
          throw new RuntimeException("MediaPlayer no init,please call init() before");
       }
        try {
            mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(listener);
            mediaPlayer.start();
        }catch (Exception e) {
            e.printStackTrace();
            mediaPlayer.reset();
        }
    }

    //播放完毕需要释放MediaPlayer的资源
    public static void release(){
        if(mediaPlayer != null){
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //初始化MediaPlayer
    private static MediaPlayer getMediaPlayer(Context context){
        return new MediaPlayer();
    }
}
