package com.example.administrator.zhixueproject.fragment.topic;

import com.example.administrator.zhixueproject.fragment.BaseFragment;


/**
 * 讨论
 */

public class PostsDetailsTaskFragment extends BaseFragment {
    // private PostsTaskAdapter mAdapter;
    // private PostsDetailsP postsDetailsP;
    private String postId;
    private int PAGE = 1;
    private String LIMIT = "10";
    private String TIMESTAMP = "";
    private int type;//1讨论  2作业


    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
