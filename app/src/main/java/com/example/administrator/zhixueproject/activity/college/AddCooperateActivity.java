package com.example.administrator.zhixueproject.activity.college;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.fragment.college.TopicListFragment;

/**
 * 添加合作
 */
public class AddCooperateActivity extends BaseActivity implements View.OnClickListener{

    private EditText etName,etTimeNum;
    private TextView tvTopic,tvTeacherName;
    private TopicListFragment topicListFragment;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cooperate);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        TextView tvHead = (TextView) findViewById(R.id.tv_title);
        tvHead.setText(getString(R.string.add_cooperation));
        etName=(EditText)findViewById(R.id.et_institution_name);
        tvTopic=(TextView)findViewById(R.id.tv_topic_content);
        tvTeacherName=(TextView)findViewById(R.id.tv_teacher_name);
        etTimeNum=(EditText)findViewById(R.id.et_purchase_period);
        findViewById(R.id.rl_add_topic).setOnClickListener(this);
        findViewById(R.id.rl_choose_teacher).setOnClickListener(this);
        findViewById(R.id.tv_setting_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //所选话题
            case R.id.rl_add_topic:
                 showTopicFragment(true);
                 break;
            //发布人
            case R.id.rl_choose_teacher:
                 break;
            //保存
            case R.id.tv_setting_save:
                 break;
             default:
                 break;
        }
    }


    /**
     * 显示话题弹窗
     *
     * @param show
     */
    private void showTopicFragment(boolean show) {
        if (topicListFragment == null) {
            topicListFragment = new TopicListFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (show) {
            //防止快速点击
            if (topicListFragment.isAdded()) {
                return;
            }
            transaction.add(R.id.fl_topic, topicListFragment).commit();
        } else {
            transaction.remove(topicListFragment).commit();
        }
    }
}
