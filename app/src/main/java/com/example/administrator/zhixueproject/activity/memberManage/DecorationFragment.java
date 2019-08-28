package com.example.administrator.zhixueproject.activity.memberManage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.adapter.memberManage.DecorationAdapter;
import com.example.administrator.zhixueproject.bean.memberManage.MedalBean;
import com.example.administrator.zhixueproject.bean.memberManage.MedalInfoBean;
import com.example.administrator.zhixueproject.fragment.BaseFragment;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod2;
import com.example.administrator.zhixueproject.utils.DateUtil;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayout;
import com.example.administrator.zhixueproject.view.refreshlayout.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;


/**
 * 勋章弹窗
 */

public class DecorationFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener, MyRefreshLayoutListener {
    private DecorationAdapter mDecorationAdapter;
    private int PAGE = 1;
    private int LIMIT = 10;
    private String TIMESTAMP = "";
    private List<MedalInfoBean> medalList;
    private RecyclerView rvDecorationList;
    private MyRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_metal, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.setClickable(true);
        refreshLayout = (MyRefreshLayout) view.findViewById(R.id.mrl_refresh_layout);
        rvDecorationList = (RecyclerView) view.findViewById(R.id.rv_decoration_list);
        view.findViewById(R.id.bg_decoration).setOnClickListener(this);
        view.findViewById(R.id.tv_confirm).setOnClickListener(this);
        refreshLayout.setMyRefreshLayoutListener(this);
        mDecorationAdapter = new DecorationAdapter(R.layout.decoration_list_item);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvDecorationList.setAdapter(mDecorationAdapter);
        rvDecorationList.setLayoutManager(linearLayoutManager);
        mDecorationAdapter.setOnItemClickListener(this);
        mDecorationAdapter.setEmptyView(R.layout.empty_member_detail_view, (ViewGroup) rvDecorationList
                .getParent());
        requestNet(HandlerConstant1.GET_MEDAL_LIST_SUCCESS);
    }

    public void requestNet(int index) {
        TIMESTAMP= DateUtil.getTime();
        showProgress(getString(R.string.loading));
        HttpMethod2.getMedalList(TIMESTAMP, PAGE, LIMIT, index, mHandler);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bg_decoration://勋章列表左半部分白色透明背景
                mDecorationListener.closeDecorationListener(view);//关闭勋章弹窗
                break;
            case R.id.tv_confirm://确认选中的勋章
                if (!TextUtils.isEmpty(getCheckedMedalID())) {
                    mDecorationListener.medalIconListener(view, getCheckedMedalID(), getCheckedMedalIMG());
                } else {
                    showMsg("请选择勋章");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 勋章条目点击事件
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        boolean checked = medalList.get(position).isChecked();
        medalList.get(position).setChecked(!checked);
        mDecorationAdapter.notifyDataSetChanged();
    }

    /**
     * 获取选中勋章id组拼
     *
     * @return
     */
    public String getCheckedMedalID() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < medalList.size(); i++) {
            if (medalList.get(i).isChecked()) {
                stringBuilder.append(medalList.get(i).getMedalTypeId());
                stringBuilder.append(",");
            }
        }
        String medalIds = "";
        if (!TextUtils.isEmpty(stringBuilder.toString())) {
            medalIds = stringBuilder.substring(0, stringBuilder.length() - 1);
        }
        return medalIds;
    }

    public List<String> getCheckedMedalIMG() {
        List<String> medalImgList = new ArrayList<>();
        for (int i = 0; i < medalList.size(); i++) {
            if (medalList.get(i).isChecked()) {
                medalImgList.add(medalList.get(i).getMedalTypeMig());
            }
        }
        return medalImgList;
    }

    /**
     * 关闭勋章列表弹窗的接口回调
     */
    private OnDecorationListener mDecorationListener;

    @Override
    public void onRefresh(View view) {
        PAGE = 1;
        requestNet(HandlerConstant1.GET_MEDAL_LIST_SUCCESS);
    }

    @Override
    public void onLoadMore(View view) {
        PAGE++;
        requestNet(HandlerConstant1.GET_MEDAL_LIST_SUCCESS2);
    }

    public interface OnDecorationListener {
        void closeDecorationListener(View view);

        void medalIconListener(View view, String ids, List<String> medalImg);
    }

    public OnDecorationListener getDecorationListener() {
        return mDecorationListener;
    }

    public void setDecorationListener(OnDecorationListener decorationListener) {
        mDecorationListener = decorationListener;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MedalBean bean = (MedalBean) msg.obj;
            clearTask();
            switch (msg.what) {
                case HandlerConstant1.GET_MEDAL_LIST_SUCCESS:
                    requestNetSuccess(bean);
                    break;
                case HandlerConstant1.GET_MEDAL_LIST_SUCCESS2:
                    loadMoreSuccess(bean);
                    break;
                case HandlerConstant1.REQUST_ERROR:
                    requestError();
                    break;
                default:
                    break;

            }

        }
    };

    private void requestNetSuccess(MedalBean bean) {
        refreshLayout.refreshComplete();
        if (bean.status) {
            medalList = bean.getData().getMedalTypeList();
            mDecorationAdapter.setNewData(medalList);
            mDecorationAdapter.notifyDataSetChanged();
        } else {
            showMsg(bean.errorMsg);
        }
    }

    private void loadMoreSuccess(MedalBean bean) {
        refreshLayout.loadMoreComplete();
        if (bean.status) {
            if (bean.getData().getMedalTypeList().size() <= 0) {
                return;
            }
            medalList.addAll(bean.getData().getMedalTypeList());
            mDecorationAdapter.setNewData(medalList);
            mDecorationAdapter.notifyDataSetChanged();
        } else {
            // showMsg(bean.getErrorMsg());
        }
    }

    private void requestError() {
        refreshLayout.refreshComplete();
        refreshLayout.loadMoreComplete();
        showMsg(getString(R.string.net_error));
    }
}
