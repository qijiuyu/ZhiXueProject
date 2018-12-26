package com.example.administrator.zhixueproject.activity.topic.test;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.administrator.zhixueproject.R;
import java.io.IOException;

/**
 * @author PeterGee
 * @date 2018/12/26
 */
public class TestActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MediaPlayer myMediaPlayer;
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前音乐音量
        int maxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获取最大声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); // 设置为最大声音，可通过SeekBar更改音量大小

        AssetFileDescriptor fileDescriptor;
        try {
            fileDescriptor = this.getAssets().openFd("adele.mp3");
            myMediaPlayer = new MediaPlayer();
            myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            myMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());

            myMediaPlayer.prepare();
            myMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
