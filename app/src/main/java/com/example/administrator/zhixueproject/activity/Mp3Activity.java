package com.example.administrator.zhixueproject.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.buihha.audiorecorder.Mp3Recorder;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.audio.AudioFileFunc;
import com.example.administrator.zhixueproject.activity.audio.AudioRecordFunc;
import com.example.administrator.zhixueproject.bean.topic.ReleaseContentsBean;
import com.example.administrator.zhixueproject.fragment.topic.PlaybackDialogFragment;

import java.io.File;
import java.io.IOException;

public class Mp3Activity extends BaseActivity implements Mp3Recorder.RecorderListener {

    Mp3Recorder mp3Recorder;
    private String filePath;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp);

        if(mp3Recorder == null){
            filePath = Environment.getExternalStorageDirectory()+"/zhixue.mp3";
            File file=new File(filePath);
            if(file!=null && file.isFile()){
                file.delete();
            }
            mp3Recorder = new Mp3Recorder(filePath);
            mp3Recorder.setListener(Mp3Activity.this);
        }

        /**
         * 开始录音
         */
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    mp3Recorder.startRecording();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(Mp3Activity.this,"开始录音!",Toast.LENGTH_SHORT).show();
            }
        });


        /**
         * 停止录音
         */
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    mp3Recorder.stopRecording();
                    Toast.makeText(Mp3Activity.this,"结束录音!",Toast.LENGTH_SHORT).show();
                    mp3Recorder = null;
                }catch (Exception e){

                }

            }
        });


        /**
         * 播放
         */
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ReleaseContentsBean releaseContentsBean=new ReleaseContentsBean(filePath,2,null,10);
                PlaybackDialogFragment fragmentPlay = PlaybackDialogFragment.newInstance(releaseContentsBean);
                fragmentPlay.show(getSupportFragmentManager(), PlaybackDialogFragment.class.getSimpleName());
            }
        });
    }


    @Override
    public void stop() {
        Toast.makeText(Mp3Activity.this,"结束已结束!",Toast.LENGTH_SHORT).show();
    }
}
