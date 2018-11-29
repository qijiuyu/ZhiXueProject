package com.example.administrator.zhixueproject.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.bean.DownLoad;
import com.example.administrator.zhixueproject.bean.Version;
import com.example.administrator.zhixueproject.http.HandlerConstant1;
import com.example.administrator.zhixueproject.http.method.HttpMethod1;
import com.example.administrator.zhixueproject.view.ArrowDownloadButton;

import java.io.File;

/**
 * 版本更新工具类
 */
public class UpdateVersionUtils {
    private Dialog dialog;
    private ArrowDownloadButton abtn;
    private Version version;
    private Context mContext;
    private final String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhixue.apk";

    /**
     * 查询最新版本
     *
     * @param mContext
     */
    public void searchVersion(Context mContext) {
        this.mContext = mContext;
    }


    private Handler mHandler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //查询最新版本
                case HandlerConstant1.GET_VERSION_SUCCESS:
                    version = (Version) msg.obj;
                    if (null != version) {
//                        if (version.isSussess() && null != version.getData()) {
//                            View view = LayoutInflater.from(mContext).inflate(R.layout.version_pop, null);
//                            TextView tvCalcle = (TextView) view.findViewById(R.id.tv_cancle);
//                            TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
//                            ListView listView = (ListView) view.findViewById(R.id.list_version);
//
//                            dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog.setTitle(null);
//                            dialog.setCancelable(false);
//                            dialog.setContentView(view);
//                            Window window = dialog.getWindow();
//                            window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
//                            dialog.show();
//
//                            //判断是否是强制更新
//                            if (version.getData().isEnforce()) {
//                                tvCalcle.setVisibility(View.GONE);
//                            }
//                            //设置更新内容
//                            if (!TextUtils.isEmpty(version.getData().getChange_log())) {
//                                String[] str = version.getData().getChange_log().split("&");
//                                if (null != str) {
//                                    ListAdapter adapter = new ListAdapter(str);
//                                    listView.setAdapter(adapter);
//                                    listView.setDividerHeight(0);
//                                }
//                            }
//                            tvConfirm.setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    View view = LayoutInflater.from(mContext).inflate(R.layout.update_version, null);
//                                    abtn = (ArrowDownloadButton) view.findViewById(R.id.arrow_download_button);
//                                    dialog.setContentView(view);
//
//                                    //先删除重复安装包文件
//                                    File file = new File(savePath);
//                                    if (file.isFile()) {
//                                        file.delete();
//                                    }
//
//                                    DownLoad d = new DownLoad();
//                                    d.setDownPath(version.getData().getDownload_url());
//                                    d.setSavePath(savePath);
//                                    //下载文件
//                                    HttpMethod1.download(d, mHandler);
//                                    abtn.startAnimating();
//                                }
//                            });
//                            tvCalcle.setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//                                }
//                            });
//
//                        }
                    }
                    break;
                //下载进度
                case HandlerConstant1.DOWNLOAD_PRORESS:
                    String progress = (String) msg.obj;
                    if (!TextUtils.isEmpty(progress)) {
                        progress = progress.replace("%", "");
                        abtn.setProgress(Integer.parseInt(progress));
                    }
                    break;
                //下载完成后自动安装
                case HandlerConstant1.DOWNLOAD_SUCCESS:
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            File file = new File(savePath);
                            if (file.isFile()) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                mContext.startActivity(intent);
                            }
                        }
                    }, 1000);
                    break;

            }
            return false;
        }
    });


    class ListAdapter extends BaseAdapter {
        private String[] str;

        public ListAdapter(String[] str) {
            super();
            this.str = str;
        }

        public int getCount() {
            return str == null ? 0 : str.length;
        }

        public Object getItem(int position) {
            return str[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View view, ViewGroup parent) {
            ListAdapter.ViewHolder holder = null;
            if (view == null) {
                holder = new ListAdapter.ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.version_item, null);
                holder.tvDes = (TextView) view.findViewById(R.id.tv_vi_des);
                view.setTag(holder);
            } else {
                holder = (ListAdapter.ViewHolder) view.getTag();
            }
            holder.tvDes.setText(str[position]);
            return view;
        }

        private class ViewHolder {
            private TextView tvDes;
        }
    }
}
