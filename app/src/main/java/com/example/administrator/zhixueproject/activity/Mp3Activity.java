package com.example.administrator.zhixueproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.audio.AudioFileFunc;
import com.example.administrator.zhixueproject.activity.audio.AudioRecordFunc;
import com.example.administrator.zhixueproject.bean.topic.ReleaseContentsBean;
import com.example.administrator.zhixueproject.fragment.topic.PlaybackDialogFragment;

import java.io.File;

public class Mp3Activity extends BaseActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp);

        /**
         * 开始录音
         */
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //先删除之前的录音文件
                final String path=AudioFileFunc.getWavFilePath();
                File file=new File(path);
                if(file.isFile()){
                    file.delete();
                }

                //开始录音
                AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
                mRecord_1.startRecordAndFile();
            }
        });


        /**
         * 停止录音
         */
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
                mRecord_1.stopRecordAndFile();
            }
        });


        /**
         * 播放
         */
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /**
                 * AudioFileFunc.getWavFilePath() 是wav音频存放的路径
                 */

                ReleaseContentsBean releaseContentsBean=new ReleaseContentsBean(AudioFileFunc.getWavFilePath(),2,null,10);
                PlaybackDialogFragment fragmentPlay = PlaybackDialogFragment.newInstance(releaseContentsBean);
                fragmentPlay.show(getSupportFragmentManager(), PlaybackDialogFragment.class.getSimpleName());
            }
        });
    }


}
