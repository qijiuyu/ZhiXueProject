package com.example.administrator.zhixueproject.activity.topic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.topic.CostsListAdapter;
import com.example.administrator.zhixueproject.bean.UploadFile;
import com.example.administrator.zhixueproject.bean.eventBus.TopicEvent;
import com.example.administrator.zhixueproject.bean.topic.CostsListBean;
import com.example.administrator.zhixueproject.bean.topic.TopicListBean;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.HandlerConstant2;
import com.example.administrator.zhixueproject.http.HttpConstant;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.AddImageUtils;
import com.example.administrator.zhixueproject.utils.PopIco;
import com.example.administrator.zhixueproject.utils.StatusBarUtils;
import com.example.administrator.zhixueproject.view.CustomPopWindow;
import com.example.administrator.zhixueproject.view.SwitchButton;
import org.greenrobot.eventbus.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加话题
 *
 * @author petergee
 * @date 2018/10/8
 */
public class AddTopicActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    private int topicIsTop; //是否置顶
    private int topicUseyn; // 是否上架
    private int payType; //收费方式
    private int topicType;// 话题类型
    private String type;   //1 添加 2 编辑
    private TopicListBean bean;
    private String CollegeId = "69";
    private EditText etTitle;
    private TextView tvTollMode;
    private TextView tvTopicType;
    private SwitchButton sbStick;
    private SwitchButton sbPutAway;
    private long topicId;
    private ImageView ivAddPic;
    private CustomPopWindow mTollModePop;
    private LinearLayout llAddTopic;
    private List<CostsListBean> list = new ArrayList<>();
    private String[] costs;
    private EditText etCost;
    private String mCost = "";
    private CostsListAdapter mAdapter;
    private CustomPopWindow mTopicTypePop;
    private PopIco popIco;
    private Uri mOutputUri;
    public static final String FLAG_ADD = "1"; //添加
    public static final String FLAG_EDIT = "2";//编辑
    private String topicImg = "https://pic4.zhimg.com/02685b7a5f2d8cbf74e1fd1ae61d563b_xll.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        initView();
    }

    private void initView() {
        StatusBarUtils.transparencyBar(this);
        TextView tvTitle= (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.add_topic));
        findViewById(R.id.lin_back).setOnClickListener(this);

        llAddTopic = (LinearLayout) findViewById(R.id.ll_add_topic);
        etTitle = (EditText) findViewById(R.id.et_title);

        findViewById(R.id.rl_toll_mode).setOnClickListener(this);
        tvTollMode = (TextView) findViewById(R.id.tv_toll_mode);

        findViewById(R.id.rl_topic_type).setOnClickListener(this);
        tvTopicType = (TextView) findViewById(R.id.tv_topic_type);

        //是否置顶
        sbStick = (SwitchButton) findViewById(R.id.sb_stick);
        sbStick.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                    topicIsTop = 1;
                else
                    topicIsTop = 0;
            }
        });

        //是否上下架
        sbPutAway = (SwitchButton) findViewById(R.id.sb_put_away);
        sbPutAway.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                    topicUseyn = 1;
                else
                    topicUseyn = 0;
            }
        });

        ivAddPic = (ImageView) findViewById(R.id.iv_add_picture);
        ivAddPic.setOnClickListener(this);

        // 获取type
        type = getIntent().getStringExtra(TopicListManageActivity.TYPE);
        bean = (TopicListBean) getIntent().getSerializableExtra(TopicListManageActivity.TOPIC_INFO);
        if (bean != null) {
            initData();
        }

        findViewById(R.id.tv_confirm).setOnClickListener(this);
    }


    private void initData() {

        topicId = bean.getTopicId();
        etTitle.setText(bean.getTopicName());

        //收费方式
        payType = bean.getTopicPayType();
        if (payType == 1) {
            tvTollMode.setText("免费");
        } else if (payType == 2) {
            tvTollMode.setText(bean.getTopicPrice() + "");
        } else if (payType == 3) {
            tvTollMode.setText(bean.getTopicVipName());
        } else if (payType == 4) {
            tvTollMode.setText("权限");
        }

        topicType = bean.getTopicType();
        if (topicType == 1) {
            tvTopicType.setText("课程");
        } else if (topicType == 2) {
            tvTopicType.setText("大家谈");
        } else if (topicType == 3) {
            tvTopicType.setText("全部");
        }

        topicUseyn = bean.getTopicUseyn();
        if (topicUseyn == 0) {
            sbPutAway.setChecked(false);
        } else {
            sbPutAway.setChecked(true);
        }

        topicIsTop = bean.getTopicIsTop();
        if (topicIsTop == 0) {
            sbStick.setChecked(false);
        } else {
            sbStick.setChecked(true);
        }
        topicImg=bean.getTopicImg().toString();
        Glide.with(this).load(bean.getTopicImg().toString()).into(ivAddPic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_toll_mode:
                showTollModePop();
                break;
            case R.id.rl_topic_type:
                showTopicTypePop();
                break;
            case R.id.iv_add_picture:
                addPic();
                break;
            case R.id.tv_confirm:
                topicConfirm();
                break;
            case R.id.lin_back:
                finish();
                break;
        }
    }

    /**
     * 收费方式弹框
     */
    private void showTollModePop() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_cost_menu, null);
        handleTollMode(contentView);
        mTollModePop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWind ow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .enableOutsideTouchableDissmiss(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimUp)
                .create();
        mTollModePop.showAtLocation(llAddTopic, Gravity.BOTTOM, 0, 0);
    }

    private void handleTollMode(View contentView) {
        list.clear();
        costs = getResources().getStringArray(R.array.add_topic);
        for (int i = 0; i < costs.length; i++) {
            CostsListBean bean = new CostsListBean();
            bean.setContent(costs[i]);
            list.add(bean);
        }
        etCost = (EditText) contentView.findViewById(R.id.et_cost);
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
                        mTollModePop.dissmiss();
                        break;
                    case R.id.tv_confirm:
                        if (mCost.equals(costs[0])) {
                            tvTollMode.setText(mCost);
                            payType = 1;
                        } else if (mCost.equals(costs[1])) {
                            tvTollMode.setText(etCost.getText().toString());
                            payType = 2;
                        } else if (mCost.equals(costs[2])) {
                            tvTollMode.setText(etCost.getText().toString());
                            payType = 3;
                        } else if (mCost.equals(costs[3])) {
                            tvTollMode.setText(mCost);
                            payType = 4;
                        }
                        mTollModePop.dissmiss();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(listener);
    }

    /**
     * 话题类型弹框
     */
    private void showTopicTypePop() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_topic_type, null);
        handleTopicType(contentView);
        mTopicTypePop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .enableOutsideTouchableDissmiss(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimUp)
                .create();
        mTopicTypePop.showAtLocation(llAddTopic, Gravity.BOTTOM, 0, 0);
    }

    private void handleTopicType(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopicTypePop != null) {
                    mTopicTypePop.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.tv_all:
                        tvTopicType.setText("全部");
                        topicType = 3;
                        break;
                    case R.id.tv_course:
                        tvTopicType.setText("课程");
                        topicType = 1;
                        break;
                    case R.id.tv_voices:
                        tvTopicType.setText("大家谈");
                        topicType = 2;
                        break;
                }
            }
        };
        contentView.findViewById(R.id.tv_all).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_course).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_voices).setOnClickListener(listener);
    }

    /**
     * 上传图片
     */
    private void addPic() {
        popIco = new PopIco(AddTopicActivity.this);
        popIco.showAsDropDown();
        popIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_pop_ico_camera:
                        AddImageUtils.openCamera(AddTopicActivity.this);
                        break;
                    case R.id.tv_pop_ico_photo:
                        AddImageUtils.selectFromAlbum(AddTopicActivity.this);
                        break;
                }
            }
        });
    }

    /**
     * 相机和相册选择图片的回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AddImageUtils.REQUEST_PICK_IMAGE://从相册选择
                    if (data != null) {
                        if (Build.VERSION.SDK_INT >= 19) {
                            AddImageUtils.handleImageOnKitKat(data, AddTopicActivity.this);
                        } else {
                            AddImageUtils.handleImageBeforeKitKat(data, AddTopicActivity.this);
                        }
                        mOutputUri = Uri.parse(AddImageUtils.cropPhoto(AddTopicActivity.this));
                    }
                    break;
                case AddImageUtils.REQUEST_CAPTURE://拍照
                    mOutputUri = Uri.parse(AddImageUtils.cropPhoto(AddTopicActivity.this));
                    break;
                case AddImageUtils.REQUEST_PICTURE_CUT://裁剪完成
                    if (data != null) {
                        final File file = new File(mOutputUri.getPath());
                        if (!file.isFile()) {
                            return;
                        }
                        List<File> list = new ArrayList<>();
                        list.add(file);
                        showProgress("图片上传中");
                        //上传图片
                        HttpMethod1.uploadFile(HttpConstant.UPDATE_FILES, list, mHandler);
                    }
                    break;
            }

        }
    }

    /**
     * 保存按钮
     */
    private void topicConfirm() {
        String topicName = etTitle.getText().toString().trim();
        String tollMode = tvTollMode.getText().toString().trim();
        String topicTypes = tvTopicType.getText().toString().trim();

        if (TextUtils.isEmpty(topicName)) {
            showMsg("话题名称不能为空");
            return;
        }
        if (TextUtils.isEmpty(tollMode)) {
            showMsg("收费方式不能为空");
            return;
        }
        if (TextUtils.isEmpty(topicTypes)) {
            showMsg("话题类型不能为空");
            return;
        }

        if (type.equals(FLAG_ADD)) {
            if (payType == 1) {
                HttpMethod2.addTopic(CollegeId, topicName, payType + "", topicType + "", topicIsTop + "", topicUseyn + "", topicImg, "", "", "",mHandler);
            } else if (payType == 2) {
                HttpMethod2.addTopic(CollegeId, topicName, payType + "", topicType + "", topicIsTop + "", topicUseyn + "", topicImg, tollMode, "", "",mHandler);
            } else if (payType == 3) {
                HttpMethod2.addTopic(CollegeId, topicName, payType + "", topicType + "", topicIsTop + "", topicUseyn + "", topicImg, "", tollMode, "",mHandler);
            }
        } else if (type.equals(FLAG_EDIT)) {
            if (payType == 1) {
                HttpMethod2.updateTopic( topicId + "", topicName, payType + "", topicType + "", topicIsTop + "", topicUseyn + "", topicImg, "", "", "",mHandler);
            } else if (payType == 2) {
                HttpMethod2.updateTopic( topicId + "", topicName, payType + "", topicType + "", topicIsTop + "", topicUseyn + "", topicImg, tollMode, "", "",mHandler);
            } else if (payType == 3) {
                HttpMethod2.updateTopic( topicId + "", topicName, payType + "", topicType + "", topicIsTop + "", topicUseyn + "", topicImg, "", tollMode, "",mHandler);
            }
        }


    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(false);
        }
        list.get(position).setChecked(true);
        mCost = list.get(position).getContent();
        if (mCost.equals(costs[0])) {
            etCost.setVisibility(View.INVISIBLE);
        } else if (mCost.equals(costs[1])) {
            etCost.setVisibility(View.VISIBLE);
            etCost.setHint("金额");
        } else if (mCost.equals(costs[2])) {
            etCost.setVisibility(View.VISIBLE);
            etCost.setHint("VIP1-7");
        } else if (mCost.equals(costs[3])) {
            setClass(ChargePrivilegeActivity.class);
        }
        mAdapter.notifyDataSetChanged();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearTask();
            switch (msg.what) {
                //上传话题图片
                case HandlerConstant1.UPLOAD_HEAD_SUCCESS:
                    final UploadFile uploadFile = (UploadFile) msg.obj;
                    if (null == uploadFile) {
                        return;
                    }
                    if (uploadFile.isStatus()) {
                        topicImg=uploadFile.getData().getUrl();
                        Glide.with(mContext).load(mOutputUri.toString()).error(R.mipmap.unify_image_ing).into(ivAddPic);
                    } else {
                        showMsg(uploadFile.getErrorMsg());
                    }
                    break;
                    // 添加话题成功
                case HandlerConstant2.ADD_TOPIC_SUCCESS:
                    // 发广播
                    finish();
                    EventBus.getDefault().post(new TopicEvent().setEventType(TopicEvent.UPDATE_TOPIC_LIST));
                    break;
                    // 修改话题成功
                case HandlerConstant2.UPDATE_TOPIC_SUCCESS:
                    EventBus.getDefault().post(new TopicEvent().setEventType(TopicEvent.UPDATE_TOPIC_LIST));
                    finish();
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    showMsg(getString(R.string.net_error));
                    break;
                default:
                    break;
            }
        }
    };

}
