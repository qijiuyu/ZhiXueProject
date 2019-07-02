package com.example.administrator.zhixueproject.activity.topic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.topic.CostsListAdapter;
import com.example.administrator.zhixueproject.application.MyApplication;
import com.example.administrator.zhixueproject.bean.eventBus.PostEvent;
import com.example.administrator.zhixueproject.bean.live.TeacherListBean;
import com.example.administrator.zhixueproject.bean.topic.CostsListBean;
import com.example.administrator.zhixueproject.utils.KeyboardUtils;
import com.example.administrator.zhixueproject.utils.LogUtils;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.view.CustomPopWindow;
import com.example.administrator.zhixueproject.view.SwitchButton;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;

/**
 * 发布帖子
 *
 * @author petergee
 * @date 2018/10/10
 */
public class ReleasePostActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    private CustomPopWindow mCostPopWindow;
    private List<CostsListBean> list = new ArrayList<>();
    private String mCost = "1";
    private CostsListAdapter mAdapter;
    private String[] costs;
    private int postType;//帖子类型
    private int postTopicId;
    private String postIsTop = "0";
    private int postWriterId;
    private String postIsFree = "0"; //1免费  2付费
    private String postId;
    private String postName;
    private String postPrice;
    private EditText et_cost;
    private EditText etTitle;
    private TextView tvCost;
    private SwitchButton sbStick;
    private TextView tvIssuer;
    private LinearLayout llReleasePost;
    private int  type; // 1管理员，2老师
    private RelativeLayout relIssuer;
    private String postContentApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_post);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        StatusBarUtils.transparencyBar(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
        postType = getIntent().getIntExtra("postType", postType);
        postTopicId = getIntent().getIntExtra("postTopicId", postTopicId);
        postId = getIntent().getStringExtra("postId");
        postName = getIntent().getStringExtra("postName");
        postIsFree = TextUtils.isEmpty(getIntent().getStringExtra("postIsFree"))?"1":getIntent().getStringExtra("postIsFree");
        postPrice = getIntent().getStringExtra("postPrice");
        postIsTop = getIntent().getStringExtra("postIsTop");
        postContentApp=getIntent().getStringExtra("postContentApp");
        LogUtils.e("ReleasePostActivity  postContentApp---> "+postContentApp );
        etTitle = (EditText) findViewById(R.id.et_title);
        tvCost = (TextView) findViewById(R.id.tv_cost);
        tvCost.setText("免费");
        sbStick = (SwitchButton) findViewById(R.id.sb_stick);
        llReleasePost = (LinearLayout) findViewById(R.id.ll_release_post);
        relIssuer = (RelativeLayout) findViewById(R.id.rl_issuer);
        relIssuer.setOnClickListener(this);
        tvIssuer = (TextView) findViewById(R.id.tv_issuer);
        ImageView ivRightIssuer= (ImageView) findViewById(R.id.iv_right_issuer);
        type=  MyApplication.homeBean.getAttendType();
        String userName=MyApplication.userInfo.getData().getUser().getUserName()+"";
        // id
        int userId= (int) MyApplication.userInfo.getData().getUser().getUserId();
        // set default value
        tvIssuer.setText(userName);
        postWriterId=userId;
        if (type==2){
            // 老师身份
            // 设置不能选择发布人
            relIssuer.setClickable(false);
            ivRightIssuer.setVisibility(View.INVISIBLE);
        }
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.rl_cost).setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(postId)) {
            tvTitle.setText(getResources().getString(R.string.release_post));
        } else {
            setTitle(getResources().getString(R.string.update_post));
            if (!TextUtils.isEmpty(postName)) {
                etTitle.setText(postName);
                etTitle.setSelection(postName.length());
            }
            if (!TextUtils.isEmpty(postPrice)) {
                tvCost.setText("¥" + postPrice);
                mCost = "2";
            } else {
                mCost = "1";
            }
            sbStick.setChecked("1".equals(postIsTop) ? true : false);
            relIssuer.setVisibility(View.GONE);

        }


        //是否置顶
        sbStick.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                    postIsTop = "1";
                else
                    postIsTop = "0";
            }
        });
    }

    /**
     * 发布贴子界面跳转
     *
     * @param context
     * @param postType
     * @param postTopicId
     */
    public static void start(Context context, int postType, int postTopicId) {
        Intent starter = new Intent(context, ReleasePostActivity.class);
        starter.putExtra("postType", postType);
        starter.putExtra("postTopicId", postTopicId);
        context.startActivity(starter);
    }

    /**
     * 编辑贴子界面跳转
     *
     * @param context
     * @param postId
     * @param postName
     * @param postIsFree
     * @param postPrice
     * @param postIsTop
     * @param postType
     */
    public static void start(
            Context context,
            String postId, String postName,
            String postIsFree, String postPrice,
            String postIsTop, String postType,String postContentApp) {
        Intent starter = new Intent(context, ReleasePostActivity.class);
        starter.putExtra("postType", Integer.parseInt(postType));
        starter.putExtra("postId", postId);
        starter.putExtra("postName", postName);
        starter.putExtra("postIsFree", postIsFree);
        starter.putExtra("postPrice", postPrice);
        starter.putExtra("postIsTop", postIsTop);
        starter.putExtra("postContentApp", postContentApp);
        context.startActivity(starter);
    }

    public void onClick(View view) {
        KeyboardUtils.hideKeyBoard(view.getContext(), view);
        switch (view.getId()) {
            case R.id.tv_confirm:
                nextStep();
                break;
            case R.id.rl_cost:
                showCostPopWindow();
                break;
            case R.id.rl_issuer:
                SelectLecturersActivity.start(view.getContext());
                break;
            case R.id.lin_back:
                finish();
                break;
            default:
                break;
        }
    }


    private void nextStep() {
        String postName = etTitle.getText().toString().trim();
        String mLecturer = tvIssuer.getText().toString().trim();
        if (!"1".equals(postIsFree)) {
            if (et_cost != null) {
                postPrice = et_cost.getText().toString().trim();
                if(TextUtils.isEmpty(postPrice)){
                    showMsg("付费帖子价格不能为空");
                    return;
                }
                double price = Double.parseDouble(postPrice);
                if (price <= 0) {
                    showMsg("付费帖子价格不能为0");
                    return;
                }
            } else {
                postPrice = "0";
            }
        } else {
            postPrice = "0";
        }
        LogUtils.e("帖子价格===" + postPrice);

        if (TextUtils.isEmpty(postName)) {
            showMsg("标题不能为空");
            return;
        }

        if (TextUtils.isEmpty(mCost)) {
            showMsg("收费类型不能为空");
            return;
        }

        if (TextUtils.isEmpty(postId)) {
            if (TextUtils.isEmpty(mLecturer)) {
                showMsg("讲师不能为空");
                return;
            }
            LogUtils.e("发布帖子");
            ReleaseContentsActivity.start(this, String.valueOf(postType), postName, String.valueOf(postTopicId), String.valueOf(postWriterId), postIsFree, postPrice, postIsTop);
        } else {
            LogUtils.e("更新帖子");
            ReleaseContentsActivity.startT(this, postId, postName, String.valueOf(postTopicId), postIsFree, postPrice, postIsTop,postContentApp);
        }
    }

    /**
     * 是否付费的弹框
     */
    private void showCostPopWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_cost_menu, null);
        handleLogic(contentView);
        mCostPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .enableOutsideTouchableDissmiss(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimUp)
                .create();
        mCostPopWindow.showAtLocation(llReleasePost, Gravity.BOTTOM, 0, 0);
    }


    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        list.clear();
        costs = getResources().getStringArray(R.array.release_cost);
        for (int i = 0; i < costs.length; i++) {
            CostsListBean bean = new CostsListBean();
            bean.setContent(costs[i]);
            list.add(bean);
        }
        et_cost = (EditText) contentView.findViewById(R.id.et_cost);
        RecyclerView rv_cost_list = (RecyclerView) contentView.findViewById(R.id.rv_cost_list);
        rv_cost_list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CostsListAdapter(R.layout.cost_list_item, list);
        rv_cost_list.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_cancel:
                        mCostPopWindow.dissmiss();
                        break;
                    case R.id.tv_confirm:
                        if (mCost.equals(costs[0])) {
                            if (postType == 3) {
                                showMsg("不能选择免费");
                                return;
                            }
                            tvCost.setText(mCost);
                            postIsFree = "1";
                        } else if (mCost.equals(costs[1])) {
                            String cost = et_cost.getText().toString();
                            if (cost.isEmpty()) {
                                showMsg("付费帖子金额不能为空");
                                return;
                            }
                            if(Double.parseDouble(cost)==0){
                                showMsg("付费帖子金额不能为0");
                                return;
                            }
                            tvCost.setText("¥" + cost);
                            postIsFree = "2";
                        }
                        mCostPopWindow.dissmiss();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == SelectLecturersActivity.REQUEST_CODE) {
            TeacherListBean bean = (TeacherListBean) data.getSerializableExtra(SelectLecturersActivity.TEACHER_INFO);
            tvIssuer.setText(bean.getUserName());
            postWriterId = bean.getTeacherId();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(false);
        }
        list.get(position).setChecked(true);
        mCost = list.get(position).getContent();
        if (mCost.equals(costs[0])) {
            et_cost.setVisibility(View.INVISIBLE);
        } else if (mCost.equals(costs[1])) {
            et_cost.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void postEvent(PostEvent postEvent) {
        if (PostEvent.RELEASE_SUCCESS == postEvent.getEventType()) {
            EventBus.getDefault().post(new PostEvent().setEventType(PostEvent.MODIFY_POST_SUCCESS));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
