package com.example.administrator.zhixueproject.activity.topic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.activity.BaseActivity;
import com.example.administrator.zhixueproject.adapter.topic.ChargePrivilegeAdapter;
import com.example.administrator.zhixueproject.bean.topic.ChargePrivilegeBean;
import com.example.administrator.zhixueproject.utils.InputMethodUtils;
import com.example.administrator.zhixueproject.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 特权充值
 *
 * @author petergee
 * @date 2018/10/9
 */
public class ChargePrivilegeActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    private boolean isSelect = false;
    private ChargePrivilegeAdapter mAdapter;
    private List<ChargePrivilegeBean> listData = new ArrayList<>();
    private List<ChargePrivilegeBean> checkedList = new ArrayList<>();
    private RecyclerView rvPrivilegeList;
    private TextView tvSelect;
    private TextView tvRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_priviege);
        initView();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.charge_privilege));
        findViewById(R.id.lin_back).setOnClickListener(this);
        tvRight = (TextView) findViewById(R.id.tv_right);
        tvRight.setText(getString(R.string.confirm));
        tvRight.setOnClickListener(this);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        tvSelect = (TextView) findViewById(R.id.tv_select);
        tvSelect.setOnClickListener(this);
        rvPrivilegeList = (RecyclerView) findViewById(R.id.rv_privilege_list);
        final EditText etSearch= (EditText) findViewById(R.id.et_search);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodUtils.hideInputMethod(v);//隐藏软键盘
                    // 搜索
                    LogUtils.e("搜索");
                    return true;
                }
                return false;
            }
        });


        for (int i = 0; i < 10; i++) {
            ChargePrivilegeBean bean = new ChargePrivilegeBean();
            bean.setName("景小甜" + i);
            listData.add(bean);
        }
        mAdapter = new ChargePrivilegeAdapter(R.layout.charge_privilege_item, listData);
        rvPrivilegeList.setAdapter(mAdapter);
        rvPrivilegeList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(this);

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ChargePrivilegeActivity.class);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.activity_bottom_in, R.anim.alpha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_back:
                finish();
                overridePendingTransition(R.anim.alpha, R.anim.activity_bottom_out);
                break;
            case R.id.tv_right:
                finish();
                overridePendingTransition(R.anim.alpha, R.anim.activity_bottom_out);
                break;
            case R.id.tv_select:
                if (!isSelect) {
                    tvSelect.setText("全部");
                    isSelect = true;
                    for (int i = 0; i < listData.size(); i++) {
                        if (!listData.get(i).isChecked()) {
                            checkedList.add(listData.get(i));
                        }
                    }
                    listData.removeAll(checkedList);
                    mAdapter.notifyDataSetChanged();
                } else {
                    tvSelect.setText("已选");
                    isSelect = false;
                    listData.clear();
                    for (int i = 0; i < 10; i++) {
                        ChargePrivilegeBean bean = new ChargePrivilegeBean();
                        bean.setName("景小甜" + i);
                        listData.add(bean);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_confirm:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        boolean isChecked = listData.get(position).isChecked();
        listData.get(position).setChecked(!isChecked);
        mAdapter.notifyDataSetChanged();
    }
}
